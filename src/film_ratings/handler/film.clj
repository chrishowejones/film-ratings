(ns film-ratings.handler.film
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [clojure.string :as string]
            [film-ratings.boundary.film :as boundary.film]
            [film-ratings.views.film :as views.film]
            [integrant.core :as ig]
            [clojure.walk :as walk]))

(defmethod ig/init-key :film-ratings.handler.film/show-create [_ _]
  (fn [_]
    [::response/ok (views.film/create-film-view)]))

(defmethod ig/init-key :film-ratings.handler.film/create [_ {:keys [db]}]
  (fn [{[_ film-form] :ataraxy/result :as request}]
    (let [film (reduce-kv (fn [m k v] (assoc m (keyword k) v))
                          {}
                          (dissoc film-form "__anti-forgery-token"))
          result (boundary.film/create-film db film)
          alerts (if (:id result)
                   {:messages ["Film added"]}
                   result)]

      [::response/ok (views.film/film-view film alerts)])))

(defmethod ig/init-key :film-ratings.handler.film/list [_ {:keys [db]}]
  (fn [_]
    (let [films-list (boundary.film/list-films db)]
      [::response/ok (views.film/list-films-view films-list)])))

(defmethod ig/init-key :film-ratings.handler.film/show-search [_ _]
  (fn [_]
    [::response/ok (views.film/search-film-by-name-view)]))

(defmethod ig/init-key :film-ratings.handler.film/find-by-name [_ {:keys [db]}]
  (fn [{[_ search-form] :ataraxy/result :as request}]
    (let [name (get search-form "name")
          films-list (boundary.film/fetch-films-by-name db name)]
      [::response/ok (views.film/list-films-view films-list)])))
