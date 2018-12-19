(ns film-ratings.boundary.film
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            duct.database.sql)
  (:import java.sql.SQLException))

(defprotocol FilmDatabase
  (list-films [db])
  (fetch-film [db id])
  (create-film [db film]))

(extend-protocol FilmDatabase
  duct.database.sql.Boundary
  (list-films [{db :spec}]
    (jdbc/query db ["SELECT * FROM film"]))
  (fetch-film [{db :spec} id]
    (first
     (jdbc/query db ["SELECT * FROM film WHERE id = ?" id])))
  (create-film [{db :spec} film]
    (try
     (let [result (jdbc/insert! db :film film)]
       (if-let [id (val (ffirst result))]
         {:id id}
         {:errors ["Failed to add film."]}))
     (catch SQLException ex
       (log/errorf "Failed to insert film. %s\n" (.getMessage ex))
       {:errors [(format "Film not added due to %s" (.getMessage ex))]}))))
