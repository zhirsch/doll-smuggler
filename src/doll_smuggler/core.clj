(ns doll-smuggler.core
  (:require
    [doll-smuggler.input :refer [parse-file-contents]]
    [doll-smuggler.output :refer [make-output-str]]
    [doll-smuggler.solve :refer [solve]]
  ))

(defn -main
  [path]
  (print (make-output-str (solve (parse-file-contents (slurp path))))))
