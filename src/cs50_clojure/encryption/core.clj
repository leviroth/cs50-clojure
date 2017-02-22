(ns cs50-clojure.encryption.core)

(defn encrypt-char
  [k plaintext]
  (let [conversion-base (int (if (Character/isLowerCase plaintext)
                               \a
                               \A))
        shifted (- (int plaintext) conversion-base)
        encrypted (rem (+ shifted k) 26)
        ciphertext (char (+ encrypted conversion-base))]
    ciphertext))

(defn encrypt-string
  ([key-seq plaintext] (encrypt-string key-seq plaintext '()))
  ([key-seq plaintext acc]
    (cond (empty? plaintext)
          (apply str (reverse acc))

          (Character/isAlphabetic (int (first plaintext)))
          (recur (rest key-seq)
                 (rest plaintext)
                 (cons (encrypt-char
                        (first key-seq)
                        (first plaintext)) acc))
          :else
          (recur key-seq
                 (rest plaintext)
                 (cons (first plaintext) acc)))))
