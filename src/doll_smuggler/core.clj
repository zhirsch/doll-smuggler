(ns doll-smuggler.core
  (:require [clojure.pprint :refer [pprint]]
            [doll-smuggler.input :refer [parse-file-contents]]))

(defn -main
  [path]
  (pprint (parse-file-contents (slurp path))))
