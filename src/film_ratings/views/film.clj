(ns film-ratings.views.film
  (:require [film-ratings.views.template :refer [page labeled-radio]]
            [hiccup.form :refer [form-to label text-field text-area submit-button]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

(defn create-film-view
  []
  (page
   [:div.container.jumbotron.bg-light
    [:row
     [:h2 "Add a film"]]
    [:row
     [:col-md
      (form-to [:post "/add-film"]
               (anti-forgery-field)
               [:div.form-group.col-md
                (label :name "Name:")
                (text-field {:class "mb-3 form-control" :placeholder "Enter film name"} :name)]
               [:div.form-group.col-md
                (label :description "Description:")
                (text-area {:class "mb-3 form-control" :placeholder "Enter film description"} :description)]
               [:div.form-group.col-md
                (label :ratings "Rating (1-5):")]
               [:div.form-group.btn-group.col-md
                (map (labeled-radio "rating") (repeat 5 false) (range 1 6))]
               [:div.form-group.col-md.text-center
                (submit-button {:class "btn btn-primary text-center"} "Add")])]]]))
