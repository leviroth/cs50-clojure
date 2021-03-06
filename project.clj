(defproject cs50-clojure "0.1.0-SNAPSHOT"
  :description "Clojure implementation of some CS50 problem sets"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot cs50-clojure.core
  :target-path "target/%s"
  :profiles {:greedy {:main cs50-clojure.greedy.core}
             :mario {:main cs50-clojure.mario.core}
             :caesar {:main cs50-clojure.caesar.core}
             :vigenere {:main cs50-clojure.vigenere.core}
             :fifteen {:main cs50-clojure.fifteen.core}}
  :aliases {"run-greedy" ["with-profile" "greedy" "run"]
            "run-mario" ["with-profile" "mario" "run"]
            "run-caesar" ["with-profile" "caesar" "run"]
            "run-vigenere" ["with-profile" "vigenere" "run"]
            "run-fifteen" ["with-profile" "fifteen" "run"]})
