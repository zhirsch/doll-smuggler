(ns doll-smuggler.core
  (:gen-class))

(use 'clojure.pprint)

(load "input")

(defn -main
  [path]
  (pprint (parse-file (slurp path))))
