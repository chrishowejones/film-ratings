(ns film-ratings.handler.index
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [clojure.java.io :as io]
            [integrant.core :as ig]))

(defmethod ig/init-key :film-ratings.handler/index [_ options]
  (fn [{[_] :ataraxy/result}]
    [::response/ok "Hello"]))
