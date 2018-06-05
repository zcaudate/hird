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

