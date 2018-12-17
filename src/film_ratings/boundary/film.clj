(ns film-ratings.boundary.film
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]))

(defprotocol FilmDatabase
  (list-films [db])
  (fetch-film [db name])
  (create-film [db film]))

(extend-protocol FilmDatabase
  duct.database.sql.Boundary
  (list-films [{db :spec}]
    (jdbc/query db ["SELECT * FROM film"]))
  (fetch-film [{db :spec} name]
    (jdbc/query db ["SELECT * FROM film WHERE name = ?" name]))
  (create-film [{db :spec} film]
    (jdbc/insert! db :film film)))
