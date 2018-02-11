(ns doll-smuggler.solve)

;; Binary knapsack solver based on
;; http://www.es.ele.tue.nl/education/5MC10/Solutions/knapsack.pdf
;;
;; The algorithm uses a "divide and conquer" approach to solve the problem.
;; Essentially, given a max weight W, the optimal value is determined by
;; considering, for each doll, whether a higher value is obtained by including
;; that doll or not including that doll.
;;
;; That is (in pseudo-code):
;;
;;   def maximize_value(dolls, remaining_weight):
;;     if len(dolls) == 0:
;;       return 0
;;     doll = dolls[-1]
;;     if weight(doll) > remaining_weight:
;;       return maximize_value(dolls[:-1], remaining_weight)
;;     return max(maximize_value(dolls[:-1], remaining_weight),
;;                value(doll) + maximize_value(dolls[:-1], remaining_weight - weight(doll)))
;;
;; Calling maximize_value(dolls, max_weight) will return the optimal value for
;; packing a subset of the dolls.
;;
;; The actual implemented algorithm has two extensions:
;;   1. It uses dynamic programming to avoid computing the same result of
;;      maximize_value more than once.
;;   2. It keeps track of specifically which dolls are in the subset of packed
;;      dolls.
;;
;; In the implementation below, variables have the following meanings:
;;   * V -- a map from ith doll and remaining weight to the maximum value, if
;;          it's already been computed.  This is the table used for dynamic
;;          programming.
;;   * K -- a map from ith doll and remaining weight to whether that doll is
;;          included in the packed subset of dolls.  The entries with weight
;;          equal to max weight represent the solution to the problem.
;;   * i -- the 1-based index of a doll.
;;   * w -- the remaining weight available.

;; TODO(zhirsch): This could probably be simplified by using the "memoize" function.

(defn init-V
  "Initializes V to map (0, w) -> 0 for 0 <= w <= max-weight."
  [problem]
  ;; Loop over w = 0 -> max-weight (inclusive).
  (loop [V {}, w 0]
    (if (> w (:max-weight problem))
      V
      (recur (assoc V (list 0 w) 0) (inc w)))))

(defn compute-include-value
  "Computes the value of packing the ith doll when the remaining weight is w."
  [problem V i w]
  (let [doll (get (:dolls problem) (- i 1))]
    ;; value(doll) + V[i - 1, w - weight(doll)]
    (+ (:value doll) (get V (list (- i 1) (- w (:weight doll)))))))

(defn compute-exclude-value
  "Computes the value of not packing the ith doll when the remaining weight is w."
  [V i w]
  ;; V[i - 1, w]
  (get V (list (- i 1) w)))

(defn maybe-include-doll
  "Computes whether it's better to include or excude this doll. Returns new values for V and K."
  [problem V K i w]
  (let [doll (get (:dolls problem) (- i 1)), key (list i w),
        exclude-value (compute-exclude-value V i w)]
    (if (> (:weight doll) w)
      ;; The weight of this doll is more than the remaining weight, so it must
      ;; be excluded.
      [(assoc V key exclude-value) (assoc K key 0)]
      ;; Include this doll if the value of including it is more than the value
      ;; of excluding it.
      (let [include-value (compute-include-value problem V i w)]
        (if (> include-value exclude-value)
          [(assoc V key include-value) (assoc K key 1)]
          [(assoc V key exclude-value) (assoc K key 0)])))))

(defn compute-values-for-doll
  "Computes all the entries in V for all weights for the ith doll. Returns new values for V and K."
  [problem V K i]
  ;; Loop over w = 0 -> max-weight (inclusive).
  (loop [V V, K K, w 0]
    (if (> w (:max-weight problem))
      [V K]
      (let [[V K] (maybe-include-doll problem V K i w)]
        (recur V K (inc w))))))

(defn compute-output
  "Computes the subset of dolls that are packed."
  [problem K]
  ;; Loop over i = count(dolls) -> 1 (inclusive).
  ;; Each time a doll is kept, the remaining weight is reduced.
  (loop [i (count (:dolls problem)), w (:max-weight problem), kept []]
    (if (= i 0)
      kept
      (if (= (get K (list i w)) 1)
        (let [doll (get (:dolls problem) (- i 1))]
          (recur (dec i) (- w (:weight doll)) (conj kept doll)))
        (recur (dec i) w kept)))))

(defn solve
  "Solves the given problem. Returns the subset of dolls that are packed."
  [problem]
  ;; Loop over i = 1 -> count(dolls) (inclusive).
  (loop [V (init-V problem), K {}, i 1]
    (if (> i (count (:dolls problem)))
      (compute-output problem K)
      (let [[V K] (compute-values-for-doll problem V K i)]
        (recur V K (inc i))))))
