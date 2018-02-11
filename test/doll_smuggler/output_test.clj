(ns doll-smuggler.output-test
  (:require
   [clojure.test :refer :all]
   [doll-smuggler.types :refer [make-doll]]
   [doll-smuggler.output :refer :all]))

(def DOLL1 (make-doll "sally" "4" "50"))
(def DOLL2 (make-doll "grumpkin" "42" "70"))
(def DOLL3 (make-doll "ben" "153" "200"))
(def DOLL4 (make-doll "henry" "12345" "67890"))
(def DOLL5 (make-doll "steve" "1234567" "890123"))

(deftest test-make-doll-str
  (is (= "sally       4    50\n" (make-doll-str DOLL1)))
  (is (= "grumpkin   42    70\n" (make-doll-str DOLL2)))
  (is (= "ben       153   200\n" (make-doll-str DOLL3)))
  (is (= "henry    12345 67890\n" (make-doll-str DOLL4)))
  (is (= "steve    1234567 890123\n" (make-doll-str DOLL5))))

(deftest test-make-dolls-str
  (is (= (str "sally       4    50\n"
              "grumpkin   42    70\n"
              "ben       153   200\n") (make-dolls-str [DOLL1 DOLL2 DOLL3])))
  (is (= (str "henry    12345 67890\n"
              "steve    1234567 890123\n") (make-dolls-str [DOLL4 DOLL5])))
  (is (empty? (make-dolls-str []))))

(deftest test-make-output-str
  (is (= (str "packed dolls:\n"
              "\n"
              "name    weight value\n"
              "sally       4    50\n"
              "grumpkin   42    70\n"
              "ben       153   200\n") (make-output-str [DOLL1 DOLL2 DOLL3])))
  (is (= (str "packed dolls:\n"
              "\n"
              "name    weight value\n"
              "henry    12345 67890\n"
              "steve    1234567 890123\n") (make-output-str [DOLL4 DOLL5])))
  (is (= (str "packed dolls:\n"
              "\n"
              "name    weight value\n") (make-output-str []))))
