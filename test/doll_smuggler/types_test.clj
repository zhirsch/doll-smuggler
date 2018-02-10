(ns doll-smuggler.types-test
  (:require [clojure.test :refer :all]
            [clojure.algo.generic.math-functions :refer [approx=]]
            [doll-smuggler.types :refer [make-doll make-problem]]))

(deftest test-make-doll
  (testing "valid"
    (let [doll (make-doll "felix" "1" "2")]
      (is (= "felix" (:name doll)))
      (is (approx= 1.0 (:weight doll) 1e-6))
      (is (approx= 2.0 (:value doll) 1e-6)))
    (let [doll (make-doll "felix" "1.6" "2.9")]
      (is (= "felix" (:name doll)))
      (is (approx= 1.6 (:weight doll) 1e-6))
      (is (approx= 2.9 (:value doll) 1e-6))))
  (testing "invalid"
    (is (= nil (make-doll "felix" "-1" "2")))
    (is (= nil (make-doll "felix" "1" "-2")))
    (is (= nil (make-doll "felix" "a" "2")))
    (is (= nil (make-doll "felix" "1" "b")))))

(deftest test-make-problem
  (testing "valid"
    (let [problem (make-problem "1.0" '())]
      (is (approx= 1.0 (:max-weight problem) 1e-6))
      (is (empty? (:dolls problem))))
    (let [problem (make-problem "9.4" (list (make-doll "felix" "1" "2")))]
      (is (approx= 9.4 (:max-weight problem) 1e-6))
      (is (= (list (make-doll "felix" "1" "2")) (:dolls problem)))))
  (testing "invalid"
    (is (= nil (make-problem "-1" '())))
    (is (= nil (make-problem "a" '())))))
