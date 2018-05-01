(ns kundel.spacing-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [kundel.component :as s]))

(deftest buttons-4
  (testing "4 buttons"
    (is (= (s/get-button-size 4) 25))))

(deftest buttons-8
  (testing "8 buttons"
    (is (= (s/get-button-size 8) 12.5))))

(deftest buttons-12
  (testing "12 buttons"
    (is (> (s/get-button-size 12) 8.3))
    (is (< (s/get-button-size 12) 8.4))))

(deftest buttons-20
  (testing
    (is (= (s/get-button-size 20) 5))))