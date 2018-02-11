(ns doll-smuggler.output)

(def HEADER "name    weight value\n")
(def FORMAT "%-8s %4d %5d\n")

(defn make-doll-str
  "Formats a doll line."
  [doll]
  (format FORMAT (:name doll) (:weight doll) (:value doll)))

(defn make-dolls-str
  "Formats all the doll lines."
  [dolls]
  (clojure.string/join (map make-doll-str dolls)))

(defn make-output-str
  "Formats a solution."
  [dolls]
  (str "packed dolls:\n\n" HEADER (make-dolls-str dolls)))
