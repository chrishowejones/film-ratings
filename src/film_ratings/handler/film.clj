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
          id (boundary.film/create-film db film)]
      (if (int? id)
        [::response/ok (views.film/film-view film)]
        [::response/ok (format "Film named '%s' was not added." (:name film))]))))

(defmethod ig/init-key :film-ratings.handler.film/list [_ {:keys [db]}]
  (fn [_]
    (let [films-list (boundary.film/list-films db)]
      [::response/ok (format "<p>A list of films<p>%s</p>" (string/join "</p><p>" films-list))])))

(defmethod ig/init-key :film-ratings.handler.film/show [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (let [film (boundary.film/fetch-film db id)]
      (if film
        [::response/ok (views.film/film-view film)]
        [::response/ok (format "Film for id %s not found." id)]))))
