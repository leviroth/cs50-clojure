(ns cs50-clojure.caesar.core)
(require 'cs50-clojure.encryption.core)

(defn -main
  [& args]
  (let [k (Integer/parseInt (first args))]
    (print "plaintext: ")
    (flush)
    (println
     (str
      "ciphertext: "
     (cs50-clojure.encryption.core/encrypt-string (repeat k) (read-line))))))
