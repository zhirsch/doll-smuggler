(ns doll-smuggler.input-test
  (:require [clojure.test :refer :all]
            [clojure.algo.generic.math-functions :refer [approx=]]
            [doll-smuggler.input :refer [parse-file-contents]]
            [doll-smuggler.types :refer [make-doll make-problem]]))

(def CONTENTS_1
"max weight: 1

available dolls:

name weight value
")

(def CONTENTS_2
"max weight: 1

available dolls:

name weight value
luke      1     2
bob       3     4
")

(def CONTENTS_3
"max\t weight: \t17.2\t \t
 \t
available    dolls: \t
\t
name\tweight\tvalue
ben 0.4 17.2
  \t
felix 4\t 6
")

(def CONTENTS_4
"max weight: 1
available dolls:
")

(def CONTENTS_5
"max weight: 1

available dolls:
name weight value
")

(def CONTENTS_6
"max weight: 1

available foos:

name weight value
luke      1     2
")

(deftest test-parse-file-contents
  (testing "matches"
    (is (= (make-problem "1" '()) (parse-file-contents CONTENTS_1)))
    (is (= (make-problem "1" (list (make-doll "bob" "3" "4") (make-doll "luke" "1" "2")))
           (parse-file-contents CONTENTS_2)))
    (is (= (make-problem "17.2" (list (make-doll "felix" "4" "6") (make-doll "ben" "0.4" "17.2")))
           (parse-file-contents CONTENTS_3))))
  (testing "does not match"
    (is (= nil (parse-file-contents "")))
    (is (= nil (parse-file-contents "max weight: 1")))
    (is (= nil (parse-file-contents CONTENTS_4)))
    (is (= nil (parse-file-contents CONTENTS_5)))
    (is (= nil (parse-file-contents CONTENTS_6)))))
