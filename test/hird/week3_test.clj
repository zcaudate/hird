(ns hara.week3-test
  (:use hara.test)
  (:require [hird.core :refer :all]))

(def ^:dynamic *dir* "exercise/03/a")

(def ^:dynamic *opts* {:update :clock
                       :format :decimal})

(fact "List All Gates"

  (list-chips *dir*)
  => ["Bit" "PC" "RAM64" "RAM8" "Register"])


(fact "Bit Gate"

  (test-chip "Bit" *opts*)
  => true

  (take 10 (eval-chip "Bit" *opts*))
  => [[{:in "0", :load "0"} {:out "0"}]
      [{:in "0", :load "0"} {:out "0"}]
      [{:in "0", :load "1"} {:out "0"}]
      [{:in "0", :load "1"} {:out "0"}]
      [{:in "1", :load "0"} {:out "0"}]
      [{:in "1", :load "0"} {:out "0"}]
      [{:in "1", :load "1"} {:out "0"}]
      [{:in "1", :load "1"} {:out "1"}]
      [{:in "0", :load "0"} {:out "1"}]
      [{:in "0", :load "0"} {:out "1"}]])


(fact "Register Gate"

  (test-chip "Register" *opts*)
  => true

  (take 10 (eval-chip "Register" *opts*))
  => [[{:in "0", :load "0"} {:out "0"}]
      [{:in "0", :load "0"} {:out "0"}]
      [{:in "0", :load "1"} {:out "0"}]
      [{:in "0", :load "1"} {:out "0"}]
      [{:in "-32123", :load "0"} {:out "0"}]
      [{:in "-32123", :load "0"} {:out "0"}]
      [{:in "11111", :load "0"} {:out "0"}]
      [{:in "11111", :load "0"} {:out "0"}]
      [{:in "-32123", :load "1"} {:out "0"}]
      [{:in "-32123", :load "1"} {:out "-32123"}]])


(fact "PC Gate"

  (test-chip "PC" *opts*)
  => true

  (eval-chip "PC" *opts*)
  => [[{:load "0", :reset "0", :inc "0", :in "0"} {:out "0"}]
      [{:load "0", :reset "0", :inc "0", :in "0"} {:out "0"}]
      [{:load "0", :reset "0", :inc "1", :in "0"} {:out "0"}]
      [{:load "0", :reset "0", :inc "1", :in "0"} {:out "1"}]
      [{:load "0", :reset "0", :inc "1", :in "-32123"} {:out "1"}]
      [{:load "0", :reset "0", :inc "1", :in "-32123"} {:out "2"}]
      [{:load "1", :reset "0", :inc "1", :in "-32123"} {:out "2"}]
      [{:load "1", :reset "0", :inc "1", :in "-32123"} {:out "-32123"}]
      [{:load "0", :reset "0", :inc "1", :in "-32123"} {:out "-32123"}]
      [{:load "0", :reset "0", :inc "1", :in "-32123"} {:out "-32122"}]
      [{:load "0", :reset "0", :inc "1", :in "-32123"} {:out "-32122"}]
      [{:load "0", :reset "0", :inc "1", :in "-32123"} {:out "-32121"}]
      [{:load "1", :reset "0", :inc "0", :in "12345"} {:out "-32121"}]
      [{:load "1", :reset "0", :inc "0", :in "12345"} {:out "12345"}]
      [{:load "1", :reset "1", :inc "0", :in "12345"} {:out "12345"}]
      [{:load "1", :reset "1", :inc "0", :in "12345"} {:out "0"}]
      [{:load "1", :reset "0", :inc "1", :in "12345"} {:out "0"}]
      [{:load "1", :reset "0", :inc "1", :in "12345"} {:out "12345"}]
      [{:load "1", :reset "1", :inc "1", :in "12345"} {:out "12345"}]
      [{:load "1", :reset "1", :inc "1", :in "12345"} {:out "0"}]
      [{:load "0", :reset "0", :inc "1", :in "12345"} {:out "0"}]
      [{:load "0", :reset "0", :inc "1", :in "12345"} {:out "1"}]
      [{:load "0", :reset "1", :inc "1", :in "12345"} {:out "1"}]
      [{:load "0", :reset "1", :inc "1", :in "12345"} {:out "0"}]
      [{:load "1", :reset "0", :inc "1", :in "0"} {:out "0"}]
      [{:load "1", :reset "0", :inc "1", :in "0"} {:out "0"}]
      [{:load "0", :reset "0", :inc "1", :in "0"} {:out "0"}]
      [{:load "0", :reset "0", :inc "1", :in "0"} {:out "1"}]
      [{:load "0", :reset "1", :inc "0", :in "22222"} {:out "1"}]
      [{:load "0", :reset "1", :inc "0", :in "22222"} {:out "0"}]])
      
(fact "RAM8 Gate"

  (test-chip "RAM8" *opts*)
  (21 61 85 87 111 113 137 139 163)

  (eval-chip "RAM8" *opts*))

(fact "RAM64 Gate"

  (test-chip "RAM64" *opts*)
  
  (eval-chip "RAM64" *opts*))
