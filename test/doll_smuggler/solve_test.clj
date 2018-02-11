(ns doll-smuggler.solve-test
  (:require [clojure.test :refer :all]
            [doll-smuggler.types :refer :all]
            [doll-smuggler.solve :refer :all]))

(deftest test-init-V
  (let [problem (make-problem "2" [])]
    (is (= '{(0 0) 0, (0 1) 0, (0 2) 0} (init-V problem)))))

(deftest test-compute-include-value
  (let [problem (make-problem "2" [(make-doll "a" "1" "2") (make-doll "b" "3" "4")])]
    (let [V '{(0 9) 0}, i 1, w 10]
      (is (= 2 (compute-include-value problem V i w))))
    (let [V '{(1 9) 7}, i 2, w 12]
      (is (= 11 (compute-include-value problem V i w))))))

(deftest test-compute-exclude-value
  (let [V '{(4 12) 32}, i 5, w 12]
    (is (= 32 (compute-exclude-value V i w)))))

(deftest test-maybe-include-doll
  (let [problem (make-problem "2" [(make-doll "a" "1" "2") (make-doll "b" "3" "4")])]
    (testing "doll's weight is more than remaining weight"
      (let [V '{(1 2) 4}, K {}, i 2, w 2]
        (is (= '({(1 2) 4, (2 2) 4}, {(2 2) 0}) (maybe-include-doll problem V K i w)))))
    (testing "including has higher value than excluding"
      (let [V '{(1 10) 4, (1 7) 1}, K {}, i 2, w 10]
        (is (= '({(1 10) 4, (1 7) 1, (2 10) 5}, {(2 10) 1}) (maybe-include-doll problem V K i w)))))
    (testing "excuding has higher value than including"
      (let [V '{(1 10) 6, (1 7) 1}, K {}, i 2, w 10]
        (is (= '({(1 10) 6, (1 7) 1, (2 10) 6}, {(2 10) 0}) (maybe-include-doll problem V K i w)))))))

(deftest test-compute-values-for-doll
  (let [problem (make-problem "2" [(make-doll "a" "1" "2") (make-doll "b" "3" "4")])]
    (let [V (init-V problem), K {}, i 1]
      (is (= '({(0 0) 0, (0 1) 0, (0 2) 0, (1 0) 0, (1 1) 2, (1 2) 2} {(1 0) 0, (1 1) 1, (1 2) 1}) (compute-values-for-doll problem V K i))))))

(deftest test-compute-output
  (let [problem (make-problem "4" [(make-doll "a" "1" "2") (make-doll "b" "3" "4")])
        K '{(1 0) 0, (1 1) 1, (1 2) 1, (1 3) 1, (1 4) 1, (2 0) 0, (2 1) 0, (2 2) 0, (2 3) 1, (2 4) 1}]
    (is (= [(get (:dolls problem) 1) (get (:dolls problem) 0)] (compute-output problem K))))
  (let [problem (make-problem "3" [(make-doll "a" "1" "2") (make-doll "b" "3" "4")])
        K '{(1 0) 0, (1 1) 1, (1 2) 1, (1 3) 1, (2 0) 0, (2 1) 0, (2 2) 0, (2 3) 1}]
    (is (= [(get (:dolls problem) 1)] (compute-output problem K))))
  (let [problem (make-problem "2" [(make-doll "a" "1" "2") (make-doll "b" "3" "4")])
        K '{(1 0) 0, (1 1) 1, (1 2) 1, (2 0) 0, (2 1) 0, (2 2) 0}]
    (is (= [(get (:dolls problem) 0)] (compute-output problem K))))
  (let [problem (make-problem "2" [(make-doll "a" "3" "4") (make-doll "b" "5" "6")])
        K '{(1 0) 0, (1 1) 0, (1 2) 0, (2 0) 0, (2 1) 0, (2 2) 0}]
    (is (empty? (compute-output problem K)))))

(deftest test-solve
  ;; See test.sh
  )
