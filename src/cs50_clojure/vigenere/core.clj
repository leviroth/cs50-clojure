(ns cs50-clojure.vigenere.core)

(require 'cs50-clojure.encryption.core)

(defn char-to-key
  [c]
  (-
   (int (Character/toLowerCase c))
   (int \a)))

(defn -main
  [& args]
  (let [k (first args)]
    (print "plaintext: ")
    (flush)
    (println
     (str
      "ciphertext: "
      (cs50-clojure.encryption.core/encrypt-string
       (map char-to-key (cycle k))
       (read-line))))))
