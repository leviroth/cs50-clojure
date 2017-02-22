(ns cs50-clojure.greedy.core)

(defn get-float
  [prompt]
  (println prompt)
  (let [f-string (read-line)]
    (or
     (try (Float/parseFloat f-string)
          (catch NumberFormatException _ nil))
     (recur "Retry:"))))

(defn get-positive-float
  [prompt]
  (let [f (get-float prompt)]
    (if (>= f 0)
      f
      (recur prompt))))

(defn make-change
  "Compute the number of coins needed to make change for cents. Denominations
  in American currency by default. Uses a greedy algorithm, which is not valid
  for all denomination schemes."
  ([cents] (make-change cents [25 10 5 1]))
  ([cents denominations]
   (:coins
    (reduce (fn [{:keys [cents coins]} denomination]
              {:cents (rem cents denomination)
               :coins (+ coins (quot cents denomination))})
            {:cents cents :coins 0}
            (sort > denominations)))))

(defn -main
  "Get a dollar amount of change and print how many coins are needed."
  [& args]
  (let [change-float (get-positive-float "How much change is owed?")
        change-cents (int (Math/round (* 100 change-float)))]
    (println (make-change change-cents))))
