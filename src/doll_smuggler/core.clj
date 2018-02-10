(ns doll-smuggler.core
  (:require [clojure.pprint :refer [pprint]]
            [doll-smuggler.input :refer [parse-file-contents]]
            [doll-smuggler.solve :refer [solve]]))

(defn -main
  [path]
  (pprint (solve (parse-file-contents (slurp path)))))
