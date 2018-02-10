(ns doll-smuggler.types-test
  (:require [clojure.test :refer :all]
            [doll-smuggler.types :refer [make-doll make-problem]]))

(deftest test-make-doll
  (testing "valid"
    (let [doll (make-doll "felix" "1" "2")]
      (is (= "felix" (:name doll)))
      (is (= 1 (:weight doll)))
      (is (= 2 (:value doll)))))
  (testing "invalid"
    (is (= nil (make-doll "felix" "-1" "2")))
    (is (= nil (make-doll "felix" "1" "-2")))
    (is (= nil (make-doll "felix" "a" "2")))
    (is (= nil (make-doll "felix" "1" "b")))))

(deftest test-make-problem
  (testing "valid"
    (let [problem (make-problem "1" '())]
      (is (= 1 (:max-weight problem)))
      (is (empty? (:dolls problem))))
    (let [problem (make-problem "9" (list (make-doll "felix" "1" "2")))]
      (is (= 9 (:max-weight problem)))
      (is (= (list (make-doll "felix" "1" "2")) (:dolls problem)))))
  (testing "invalid"
    (is (= nil (make-problem "-1" '())))
    (is (= nil (make-problem "a" '())))))
