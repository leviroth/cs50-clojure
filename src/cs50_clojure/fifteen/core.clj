(ns cs50-clojure.fifteen.core)

(defn fill-squares-to-tiles
  "Associative vector mapping squares to tiles."
  [d]
  (let [highest-tile (dec (* d d))
        left-column-nums (range highest-tile 0 (- d))]
    (vec (map
          #(vec (range % (- % d) -1))
          left-column-nums))))

(defn fill-tiles-to-squares
  "Associative vector mapping tiles to squares."
  [d]
  (let [highest-tile (dec (* d d))]
    (vec
     (apply concat
            (for [row (reverse (range d))]
              (for [col (reverse (range d))]
                [row col]))))))

(defn swap-unsafe
  "Swap two tiles. Does not check if move is legal, or even if tiles exist."
  [{:keys [tiles-to-squares squares-to-tiles] :as board} tile-1 tile-2]
  (let [squares (map #(tiles-to-squares %) (list tile-1 tile-2))]
    (assoc board
           :tiles-to-squares (assoc tiles-to-squares
                                    tile-2 (tiles-to-squares tile-1)
                                    tile-1 (tiles-to-squares tile-2))
           :squares-to-tiles (reduce (fn [new-map [coord tile]]
                                       (assoc-in new-map coord tile))
                                     squares-to-tiles
                                     [[(first squares) tile-2]
                                      [(second squares) tile-1]]))))

(defn make-board
  "Constructs a board in starting position."
  [d]
  (let [board {:tiles-to-squares (fill-tiles-to-squares d)
               :squares-to-tiles (fill-squares-to-tiles d)
               :d d}]
    (if (even? d)
      ;; If side length is even, 1 and 2 have to be swapped, or else the board
      ;; is unsolvable.
      (swap-unsafe board 1 2)
      board)))

(defn row-to-string
  "Convert row to string for printing."
  [row]
  (clojure.string/join " "
                       (map #(if (zero? %)
                               " _"
                               (format "%2d" %))
                            row)))

(defn board-to-string
  "Convert board to string for printing."
  [board]
  (clojure.string/join "\n"
                       (map row-to-string (:squares-to-tiles board))))

(defn move
  "Moves tile and returns new board if legal. Otherwise, returns nil."
  [board tile]
  (let [d (:d board)
        max-tile (dec (* d d))]
    ;; Check that tile exists on this board.
    (if (and (<= tile max-tile) (> tile 0))
      (let [tile-pos ((:tiles-to-squares board) tile)
            blank-pos ((:tiles-to-squares board) 0)]
        ;; Move is legal if one coordinate is the same, and the other is
        ;; different by 1.
        (if (= 1
               (apply + (map (comp #(Math/abs %) -)
                             tile-pos
                             blank-pos)))
          (swap-unsafe board tile 0))))))

(defn won?
  "Returns true if the board has been won."
  [board]
  (let [tiles (apply concat (:squares-to-tiles board))
        d (:d board)]
    (every? identity (map = tiles (range 1 (* d d))))))

(defn -main
  [& args]
  (loop [board (make-board (Integer/parseInt (first args)))]
    (println)
    (println (board-to-string board))
    (println "Which tile to move?")
    (let [tile (Integer/parseInt (read-line))]
      (if-let [new-board (move board tile)]
        (if (won? new-board)
          (println "You've won!")
          (recur new-board))
        (do
          (println "Illegal move!")
          (recur board))))))
