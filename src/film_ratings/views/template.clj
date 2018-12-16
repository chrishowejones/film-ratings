(ns film-ratings.views.template
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn page
  [content]
  (html5
    [:head
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
     [:title "Example page"]
     (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css")
     (include-js
       "https://code.jquery.com/jquery-3.3.1.slim.min.js"
       "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
       "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js")
     [:body
      [:div.container-fluid
       [:div.navbar.navbar-dark.bg-dark.shadow-sm
        [:div.container.d-flex.justify-content-between
         [:h1.navbar-brand.align-items-center.text-light "Film Ratings"]]]
       [:section.text-center
        content]]]]))
