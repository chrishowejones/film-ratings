(defproject film-ratings "0.1.2"
  :description "Film rating demo app using Duct."
  :url "http://github.com/chrishowejones/film-ratings"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [duct/core "0.6.2"]
                 [duct/module.logging "0.3.1"]
                 [duct/module.web "0.6.4"]
                 [duct/module.ataraxy "0.2.0"]
                 [duct/module.sql "0.4.2"]
                 [duct/database.sql.hikaricp "0.3.3"]
                 [hiccup "1.0.5"]
                 [org.postgresql/postgresql "42.1.4"]
                 [org.xerial/sqlite-jdbc "3.21.0.1"]
                 [org.clojure/tools.logging "0.4.1"]]
  :plugins [[duct/lein-duct "0.10.6"]
            [test2junit "1.4.2"]
            [lein-cloverage "1.0.13"]]
  :test2junit-output-dir "test2junit"
  :test2junit-run-ant true
  :main ^:skip-aot film-ratings.main
  :uberjar-name "film-ratings.jar"
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[ring-mock "0.1.5"] ;; add for testing handlers
                                   [com.gearswithingears/shrubbery "0.4.1"] ;; add for testing protocols
                                   [integrant/repl "0.2.0"]
                                   [eftest "0.4.1"]
                                   [kerodon "0.9.0"]]}})
