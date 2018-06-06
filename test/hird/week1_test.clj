(ns hara.week1-test
  (:use hara.test)
  (:require [hird.core :refer :all]))

(def ^:dynamic *dir* "exercise/01")

(def ^:dynamic *opts* {:update :instant
                       :format :binary})

(fact "List All Gates"

  (list-chips *dir*)
  => ["And" "And16" "DMux" "DMux4Way" "DMux8Way"
      "Mux" "Mux16" "Mux4Way16" "Mux8Way16"
      "Not" "Not16" "Or" "Or16" "Or8Way" "Xor"])


(fact "And Gate"
  
  (test-chip "And" *opts*)
  => true

  (eval-chip "And" *opts*)
  => [[{:a "0", :b "0"} {:out "0"}]
      [{:a "0", :b "1"} {:out "0"}]
      [{:a "1", :b "0"} {:out "0"}]
      [{:a "1", :b "1"} {:out "1"}]])

(fact "And16 Gate"
  
  (test-chip "And16" *opts*)
  => true

  (eval-chip "And16" *opts*)
  => [[{:a "0000000000000000", :b "0000000000000000"} {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "1111111111111111"} {:out "0000000000000000"}]
      [{:a "1111111111111111", :b "1111111111111111"} {:out "1111111111111111"}]
      [{:a "1010101010101010", :b "0101010101010101"} {:out "0000000000000000"}]
      [{:a "0011110011000011", :b "0000111111110000"} {:out "0000110011000000"}]
      [{:a "0001001000110100", :b "1001100001110110"} {:out "0001000000110100"}]])

(fact "DMux Gate"

  (test-chip "DMux" *opts*)
  => true

  (eval-chip "DMux" *opts*)
  => [[{:in "0", :sel "0"} {:a "0", :b "0"}]
      [{:in "0", :sel "1"} {:a "0", :b "0"}]
      [{:in "1", :sel "0"} {:a "1", :b "0"}]
      [{:in "1", :sel "1"} {:a "0", :b "1"}]])


(fact "DMux4Way Gate"
  (test-chip "DMux4Way" *opts*)
  => true

  (eval-chip "DMux4Way" *opts*)
  => [[{:in "0", :sel "00"} {:a "0", :b "0", :c "0", :d "0"}]
      [{:in "0", :sel "01"} {:a "0", :b "0", :c "0", :d "0"}]
      [{:in "0", :sel "10"} {:a "0", :b "0", :c "0", :d "0"}]
      [{:in "0", :sel "11"} {:a "0", :b "0", :c "0", :d "0"}]
      [{:in "1", :sel "00"} {:a "1", :b "0", :c "0", :d "0"}]
      [{:in "1", :sel "01"} {:a "0", :b "1", :c "0", :d "0"}]
      [{:in "1", :sel "10"} {:a "0", :b "0", :c "1", :d "0"}]
      [{:in "1", :sel "11"} {:a "0", :b "0", :c "0", :d "1"}]])

 
(fact "DMux8Way Gate"

  (test-chip "DMux8Way" *opts*)
  => true

  (eval-chip "DMux8Way" *opts*)
  => [[{:sel "000", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "001", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "010", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "011", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "100", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "101", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "110", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "111", :in "0"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "000", :in "1"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "1"}]
      [{:sel "001", :in "1"} {:e "0", :g "0", :c "0", :h "0", :b "1", :d "0", :f "0", :a "0"}]
      [{:sel "010", :in "1"} {:e "0", :g "0", :c "1", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "011", :in "1"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "1", :f "0", :a "0"}]
      [{:sel "100", :in "1"} {:e "1", :g "0", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "101", :in "1"} {:e "0", :g "0", :c "0", :h "0", :b "0", :d "0", :f "1", :a "0"}]
      [{:sel "110", :in "1"} {:e "0", :g "1", :c "0", :h "0", :b "0", :d "0", :f "0", :a "0"}]
      [{:sel "111", :in "1"} {:e "0", :g "0", :c "0", :h "1", :b "0", :d "0", :f "0", :a "0"}]])


(fact "Mux Gate"

  (test-chip "Mux" *opts*)
  => true

  (eval-chip "Mux" *opts*)
  => [[{:a "0", :b "0", :sel "0"} {:out "0"}]
      [{:a "0", :b "0", :sel "1"} {:out "0"}]
      [{:a "0", :b "1", :sel "0"} {:out "0"}]
      [{:a "0", :b "1", :sel "1"} {:out "1"}]
      [{:a "1", :b "0", :sel "0"} {:out "1"}]
      [{:a "1", :b "0", :sel "1"} {:out "0"}]
      [{:a "1", :b "1", :sel "0"} {:out "1"}]
      [{:a "1", :b "1", :sel "1"} {:out "1"}]])

(fact "Mux16 Gate"

  (test-chip "Mux16" *opts*)
  => true

  (eval-chip "Mux16" *opts*)
  => [[{:a "0000000000000000", :b "0000000000000000", :sel "0"} {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "0000000000000000", :sel "1"} {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "0001001000110100", :sel "0"} {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "0001001000110100", :sel "1"} {:out "0001001000110100"}]
      [{:a "1001100001110110", :b "0000000000000000", :sel "0"} {:out "1001100001110110"}]
      [{:a "1001100001110110", :b "0000000000000000", :sel "1"} {:out "0000000000000000"}]
      [{:a "1010101010101010", :b "0101010101010101", :sel "0"} {:out "1010101010101010"}]
      [{:a "1010101010101010", :b "0101010101010101", :sel "1"} {:out "0101010101010101"}]])

(fact "Mux4Way16 Gate"

  (test-chip "Mux4Way16" *opts*)
  => true

  (eval-chip "Mux4Way16" *opts*)
  => [[{:a "0000000000000000", :b "0000000000000000", :c "0000000000000000", :d "0000000000000000", :sel "00"}
       {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "0000000000000000", :c "0000000000000000", :d "0000000000000000", :sel "01"}
       {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "0000000000000000", :c "0000000000000000", :d "0000000000000000", :sel "10"}
       {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "0000000000000000", :c "0000000000000000", :d "0000000000000000", :sel "11"}
       {:out "0000000000000000"}]
      [{:a "0001001000110100", :b "1001100001110110", :c "1010101010101010", :d "0101010101010101", :sel "00"}
       {:out "0001001000110100"}]
      [{:a "0001001000110100", :b "1001100001110110", :c "1010101010101010", :d "0101010101010101", :sel "01"}
       {:out "1001100001110110"}]
      [{:a "0001001000110100", :b "1001100001110110", :c "1010101010101010", :d "0101010101010101", :sel "10"}
       {:out "1010101010101010"}]
      [{:a "0001001000110100", :b "1001100001110110", :c "1010101010101010", :d "0101010101010101", :sel "11"}
       {:out "0101010101010101"}]])

(fact "Mux8Way16 Gate"

  (test-chip "Mux8Way16" *opts*)
  => true

  (eval-chip "Mux8Way16" *opts*)
  => [[{:sel "000",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "001",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "010",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "011",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "100",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "101",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "110",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "111",
        :e "0000000000000000", :g "0000000000000000",
        :c "0000000000000000", :h "0000000000000000", :b "0000000000000000",
        :d "0000000000000000", :f "0000000000000000", :a "0000000000000000"}
       {:out "0000000000000000"}]
      [{:sel "000",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0001001000110100"}]
      [{:sel "001",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0010001101000101"}]
      [{:sel "010",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0011010001010110"}]
      [{:sel "011",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0100010101100111"}]
      [{:sel "100",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0101011001111000"}]
      [{:sel "101",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0110011110001001"}]
      [{:sel "110",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "0111100010011010"}]
      [{:sel "111",
        :e "0101011001111000", :g "0111100010011010",
        :c "0011010001010110", :h "1000100110101011", :b "0010001101000101",
        :d "0100010101100111", :f "0110011110001001", :a "0001001000110100"}
       {:out "1000100110101011"}]])

(fact "Not Gate"

  (test-chip "Not" *opts*)
  => true

  (eval-chip "Not" *opts*)
  => [[{:in "0"} {:out "1"}]
      [{:in "1"} {:out "0"}]])

(fact "Not16 Gate"

  (test-chip "Not16" *opts*)
  => true

  (eval-chip "Not16" *opts*)
  => [[{:in "0000000000000000"} {:out "1111111111111111"}]
      [{:in "1111111111111111"} {:out "0000000000000000"}]
      [{:in "1010101010101010"} {:out "0101010101010101"}]
      [{:in "0011110011000011"} {:out "1100001100111100"}]
      [{:in "0001001000110100"} {:out "1110110111001011"}]])

(fact "Or Gate"

  (test-chip "Or" *opts*)
  => true

  (eval-chip "Or" *opts*)
  => [[{:a "0", :b "0"} {:out "0"}]
      [{:a "0", :b "1"} {:out "1"}]
      [{:a "1", :b "0"} {:out "1"}]
      [{:a "1", :b "1"} {:out "1"}]])

(fact "Or16 Gate"

  (test-chip "Or16" *opts*)
  => true

  (eval-chip "Or16" *opts*)
  => [[{:a "0000000000000000", :b "0000000000000000"} {:out "0000000000000000"}]
      [{:a "0000000000000000", :b "1111111111111111"} {:out "1111111111111111"}]
      [{:a "1111111111111111", :b "1111111111111111"} {:out "1111111111111111"}]
      [{:a "1010101010101010", :b "0101010101010101"} {:out "1111111111111111"}]
      [{:a "0011110011000011", :b "0000111111110000"} {:out "0011111111110011"}]
      [{:a "0001001000110100", :b "1001100001110110"} {:out "1001101001110110"}]])

(fact "Or8Way Gate"
  
  (test-chip "Or8Way" *opts*)
  => true

  (eval-chip "Or8Way" *opts*)
  => [[{:in "00000000"} {:out "0"}]
      [{:in "11111111"} {:out "1"}]
      [{:in "00010000"} {:out "1"}]
      [{:in "00000001"} {:out "1"}]
      [{:in "00100110"} {:out "1"}]])

(fact "Xor Gate"

  (test-chip "Xor" *opts*)
  => true

  (eval-chip "Xor" *opts*)
  => [[{:a "0", :b "0"} {:out "0"}]
      [{:a "0", :b "1"} {:out "1"}]
      [{:a "1", :b "0"} {:out "1"}]
      [{:a "1", :b "1"} {:out "0"}]])

(comment

  (for [i (list-chips *dir*)]
    (list 'fact (str i " Gate")
          (list 'test-chip i '*opts*)
          '=> true
          
          (list 'eval-chip i '*opts*)
          '=> (eval-chip i *opts*)))
  
  
  )
