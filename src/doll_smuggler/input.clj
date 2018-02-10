(ns doll-smuggler.input
  (:require [doll-smuggler.types :refer [make-doll make-problem]]
            [clojure.string :refer [split-lines]]))

(def PATTERN #"max\s+weight:\s+(\d+)\s*\n\s*\navailable\s+dolls:\s*\n\s*\nname\s+weight\s+value\s*\n((?s).*)")
(def DOLL_PATTERN #"(\S+)\s+(\d+)\s+(\d+)\s*")

(defn parse-dolls
  "Parses the lines that define dolls in an input file."
  [dolls-str]
  (loop [lines (split-lines dolls-str), dolls []]
    (if (empty? lines)
      dolls
      (let [[matches? name weight-str value-str] (re-matches DOLL_PATTERN (first lines))]
        (if matches?
          (let [doll (make-doll name weight-str value-str)]
            (if (= nil doll)
              (recur (next lines) dolls)
              (recur (next lines) (conj dolls doll))))
          (recur (next lines) dolls))))))

(defn parse-file-contents
  "Parses the contents of an input file."
  [file-contents]
  (let [[matches? max-weight-str dolls-str] (re-matches PATTERN file-contents)]
    (if matches?
      (make-problem max-weight-str (parse-dolls dolls-str))
      nil)))
