(defproject cs50-clojure "0.1.0-SNAPSHOT"
  :description "Clojure implementation of some CS50 problem sets"
  :url "http://example.com/FIXME"
  :license {:name "CC BY-NC-SA 3.0"
            :url "https://creativecommons.org/licenses/by-nc-sa/3.0/"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot cs50-clojure.core
  :target-path "target/%s"
  :profiles {:greedy {:main cs50-clojure.greedy.core}}
  :aliases {"run-greedy" ["with-profile" "greedy" "run"]})
