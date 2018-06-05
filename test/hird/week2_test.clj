(ns hara.week2-test
  (:use hara.test)
  (:require [hird.core :refer :all]))

(def ^:dynamic *dir* "exercise/02")

(def ^:dynamic *opts* {:update :instant
                       :format :binary})

(fact "ALU Gate"

  (test-chip "ALU" *opts*)
  => true

  (test-chip ["ALU" "ALU-nostat"] *opts*)
  => true)

(fact "Add16 Gate"

  (test-chip "Add16" *opts*)
  => true

  (eval-chip "Add16" *opts*)
  => [[{:b "0000000000000000", :a "0000000000000000"} {:out "0000000000000000"}]
      [{:b "1111111111111111", :a "0000000000000000"} {:out "1111111111111111"}]
      [{:b "1111111111111111", :a "1111111111111111"} {:out "1111111111111110"}]
      [{:b "0101010101010101", :a "1010101010101010"} {:out "1111111111111111"}]
      [{:b "0000111111110000", :a "0011110011000011"} {:out "0100110010110011"}]
      [{:b "1001100001110110", :a "0001001000110100"} {:out "1010101010101010"}]])

(fact "FullAdder Gate"

  (test-chip "FullAdder" *opts*)
  => true

  (eval-chip "FullAdder" *opts*)
  => [[{:a "0", :b "0", :c "0"} {:sum "0", :carry "0"}]
      [{:a "0", :b "0", :c "1"} {:sum "1", :carry "0"}]
      [{:a "0", :b "1", :c "0"} {:sum "1", :carry "0"}]
      [{:a "0", :b "1", :c "1"} {:sum "0", :carry "1"}]
      [{:a "1", :b "0", :c "0"} {:sum "1", :carry "0"}]
      [{:a "1", :b "0", :c "1"} {:sum "0", :carry "1"}]
      [{:a "1", :b "1", :c "0"} {:sum "0", :carry "1"}]
      [{:a "1", :b "1", :c "1"} {:sum "1", :carry "1"}]])

(fact "HalfAdder Gate"

  (test-chip "HalfAdder" *opts*)
  => true

  (eval-chip "HalfAdder" *opts*)
  => [[{:a "0", :b "0"} {:sum "0", :carry "0"}]
      [{:a "0", :b "1"} {:sum "1", :carry "0"}]
      [{:a "1", :b "0"} {:sum "1", :carry "0"}]
      [{:a "1", :b "1"} {:sum "0", :carry "1"}]])

(fact "Inc16 Gate"

  (test-chip "Inc16" *opts*)
  => true

  (eval-chip "Inc16" *opts*)
  => [[{:in "0000000000000000"} {:out "0000000000000001"}]
      [{:in "1111111111111111"} {:out "0000000000000000"}]
      [{:in "0000000000000101"} {:out "0000000000000110"}]
      [{:in "1111111111111011"} {:out "1111111111111100"}]])

(comment
  (for [i (list-chips *dir*)]
    (list 'fact (str i " Gate")
          (list 'test-chip i '*opts*)
          '=> true
          
          (list 'eval-chip i '*opts*)
          '=> (eval-chip i *opts*))))
