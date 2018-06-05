(ns hird.core.verify
  (:require [clojure.string :as string]
            [hara.io.file :as fs]
            [hird.core.hardware :as hardware]
            [hird.core.evaluate :as evaluate]))

(defn read-tests
  [template lines]
  (let [lines (cond (vector? lines)
                    lines
                    
                    :else
                    (fs/read-all-lines lines))
        [labels & data] (map (fn [line]
                               (->> (string/split line #"\|")
                                    (remove empty?)
                                    (map string/trim)))
                             lines)
        labels  (map keyword labels)]
    (reduce (fn [out item]
              (let [m (zipmap labels item)]
                (conj out [(select-keys m (-> template :input keys))
                           (select-keys m (-> template :output keys))])))
            []
            data)))

(defn verify [sim template [input output] opts]
  (let [result (evaluate/evaluate sim template input opts)]
    (= output (select-keys result (keys output)))))

(defn verify-all [sim template tests opts]
  (let [res (map-indexed (fn [i test]
                           (if (verify sim template test opts)
                             true
                             (inc i)))
                         tests)]
    (if (every? true? res)
      true
      (filter number? res))))


(comment
  
  (read-tests (hardware/chip-template sim)
              ["|   a   |   b   |  out  |"
               "|   0   |   0   |   0   |"
               "|   0   |   1   |   0   |"
               "|   1   |   0   |   0   |"
               "|   1   |   1   |   1   |"])
  => [[{:a "0", :b "0"} {:out "0"}]
      [{:a "0", :b "1"} {:out "0"}]
      [{:a "1", :b "0"} {:out "0"}]
      [{:a "1", :b "1"} {:out "1"}]])


