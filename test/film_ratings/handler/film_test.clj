(ns film-ratings.handler.film-test
  (:require [ataraxy.coerce :refer [->int]]
            [ataraxy.core :as ataraxy]
            [clojure.test :refer [deftest is testing]]
            [film-ratings.boundary.film
             :refer
             [create-film FilmDatabase]]
            film-ratings.handler.film
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [shrubbery.core :as shrubbery]))

(def films [{:id 1 :name "Star Wars IV: A New Hope" :rating 5}
            {:id 2 :name "Star Wars I: The Phantom Menace" :rating 1}
            {:id 3 :name "Home Alone" :rating 3}])

(def film-database-stub
  (shrubbery/stub FilmDatabase
        {:list-films films}))

(deftest check-list-handler
  (testing "Check that calling list handler returns a list of films"
    (let [handler (ig/init-key :film-ratings.handler.film/list {:db film-database-stub})
          response (handler (mock/request :get "/list-films"))]
      (is (= :ataraxy.response/ok (first response)))
      (is (= "Star Wars IV: A New Hope"
             (re-find #"Star Wars IV: A New Hope" (second response))))
      (is (= "Star Wars I: The Phantom Menace"
             (re-find #"Star Wars I: The Phantom Menace" (second response))))
      (is (= "Home Alone"
             (re-find #"Home Alone" (second response)))))))

(deftest check-create-handler
  (testing "Check that calling the create handler returns a view of the new film inserted"
    (let [new-film {:name "My film" :description "Film description" :rating "3"}
          film-database-mock (shrubbery/mock FilmDatabase {:create-film new-film})
          handler (ig/init-key :film-ratings.handler.film/create {:db film-database-mock})
          ataraxy-handler (ataraxy.core/handler {:routes '{[:post "/add-film" {film-form :form-params}] [:film/create film-form]}
                                                 :handlers {:film/create handler}})
          response (ataraxy-handler (assoc (mock/request :post "/add-film") :form-params new-film))]
      (is (= 200 (:status response)))
      (is (= "My film"
             (re-find #"My film" (:body response))))))

  (testing "Check that calling the create handler passes correct argument to database"
    (let [new-film {:name "My film" :description "Film description" :rating "3"}
          film-database-mock (shrubbery/mock FilmDatabase)
          handler (ig/init-key :film-ratings.handler.film/create {:db film-database-mock})
          _ (handler (assoc (mock/request :post "/add-film") :ataraxy/result [:form-params new-film]))]
      (is (shrubbery/received? film-database-mock create-film [new-film])))))
