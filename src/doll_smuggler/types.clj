(in-ns 'doll-smuggler.core)

;; Defines the problem that needs to be solved: pick the highest valued
;; set of dolls that have a total weight less than the max allowed.
(defrecord Problem [max-weight dolls])

(defn make-problem
  "Makes a new Problem with a max weight and an empty list of dolls."
  [max-weight]
  (Problem. max-weight '()))

(defn add-doll
  "Returns a new Problem with the doll added to the list of dolls."
  [problem doll]
  (let [dolls (:dolls problem)]
    (assoc problem :dolls (conj dolls doll))))

;; Defines a doll that can be packed.
(defrecord Doll [name weight value])

(defn make-doll
  "Makes a new Doll with a name, weight, and value."
  [name weight value]
  (Doll. name weight value))
