(ns film-ratings.handler.film
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [integrant.core :as ig]))

(defmethod ig/init-key :film-ratings.handler.film/show-create [_ _]
  (fn [_]
    [::response/ok "Should show a form to enter a film."]))

(defmethod ig/init-key :film-ratings.handler.film/create [_ {:keys [db]}]
  (fn [{[_ {:strs [name description rating] :as result}] :ataraxy/result}]
    [::response/ok (format "Film: name=%s, desc=%s, rating=%s" name, description, rating)]))

(defmethod ig/init-key :film-ratings.handler.film/list [_ {:keys [db]}]
  (fn [_]
    [::response/ok (format "A list of films - using %s" db)]))
