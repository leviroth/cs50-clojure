(ns cs50-clojure.mario.core)

(defn get-int
  [prompt]
  (print prompt)
  (flush)
  (let [int-string (read-line)]
    (or
     (try (Integer/parseInt int-string)
          (catch NumberFormatException _ nil))
     (recur "Retry: "))))

(defn get-bounded-int
  [prompt lower upper]
  (let [n (get-int prompt)]
    (if (and (>= n lower)
             (<= n upper))
      n
      (recur prompt lower upper))))

(defn repeat-char
  [c n]
  (apply str (take n (repeat c))))

(defn boring-row
  [row-number height]
  (def total-width (+ height 1))
  (def hashes (+ 2 row-number))
  (def padding (- total-width hashes))
  (str
   (repeat-char \space padding)
   (repeat-char \# hashes)))

(defn fancy-row
  [row-number height]
  (let [left-side (boring-row row-number height)]
    (str left-side
         "  "
         (clojure.string/trimr (apply str (reverse left-side))))))

(defn pyramid
  [height row-builder]
  (clojure.string/join "\n"
                       (map #(row-builder % height) (range height))))

(defn prefixify
  [& names]
  (symbol
   (clojure.string/join "-" names)))

(defn x-pyramid
  [prefix]
  (let [fn-name (prefixify prefix "pyramid")
        builder-name (prefixify prefix "row")]
    `(def ~fn-name #(pyramid % ~builder-name))))

(defmacro make-pyramid-functions
  [prefixes]
  `(do ~@(map x-pyramid prefixes)
       (def pyramid-function-map
         (into {}
               (for [prefix# ~prefixes]
                 [prefix# (resolve (prefixify prefix# "pyramid"))])))))

(make-pyramid-functions ["boring" "fancy"])

(defn -main
  [& args]
  (println
   (if-let [command (pyramid-function-map (first args))]
     (command (get-bounded-int "Height: " 0 23))
     (str "Usage: lein run-mario [variety]\n\nVarieties:\n"
          (clojure.string/join "\n" (keys pyramid-function-map))))))
