(ns kundel.smoke-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [kundel.component :as s]))

(deftest buttons-4
  (testing "4 buttons"
    (is (= (s/get-button-size 4) 20))))

(deftest buttons-8
  (testing "8 buttons"
    (is (= (s/get-button-size 8) 10))))

(deftest buttons-12
  (testing "12 buttons"
    (is (> (s/get-button-size 12) 6.6))
    (is (< (s/get-button-size 12) 6.7))))

(deftest buttons-20
  (testing "20 buttons"
    (is (= (s/get-button-size 20) 4))))

(deftest space-4
  (testing "4 buttons"
    (is (= (s/get-space-size 4) 5))))

(deftest space-8
  (testing "8 buttons"
    (is (= (s/get-space-size 8) 2.5))))

(deftest space-12
  (testing "12 buttons"
    (is (> (s/get-space-size 12) 1.6))
    (is (< (s/get-space-size 12) 1.7))))

(deftest space-20
  (testing "20 buttons"
    (is (= (s/get-space-size 20) 1))))
