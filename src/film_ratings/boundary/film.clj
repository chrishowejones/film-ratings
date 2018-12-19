(ns film-ratings.boundary.film
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]))

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
    (let [result (jdbc/insert! db :film film)]
      (if-let [id (val (ffirst result))]
        id
        "No id"))))
