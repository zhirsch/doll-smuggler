(ns doll-smuggler.types)

;; Defines the problem that needs to be solved: pick the highest valued
;; set of dolls that have a total weight less than the max allowed.
(defrecord Problem [max-weight dolls])

(defn make-problem
  "Makes a new Problem with a max weight and a list of dolls."
  [max-weight-str dolls]
  (try
    (let [max-weight (Long/parseLong max-weight-str)]
      (if (< max-weight 0)
        nil
        (Problem. max-weight dolls)))
    (catch NumberFormatException _ nil)))

;; Defines a doll that can be packed.
(defrecord Doll [name weight value])

(defn make-doll
  "Makes a new Doll with a name, weight, and value."
  [name weight-str value-str]
  (try
    (let [weight (Long/parseLong weight-str), value (Long/parseLong value-str)]
      (cond
        (< weight 0) nil
        (< value 0) nil
        :else (Doll. name weight value)))
    (catch NumberFormatException _ nil)))
