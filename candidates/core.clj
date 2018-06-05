
(comment
  (list-chips "exercise/01")
  (current-dir)
  (load-chip "exercise/01" "And")

  (load-tests "And")
  [[{:a "0", :b "0"} {:out "0"}] [{:a "0", :b "1"} {:out "0"}] [{:a "1", :b "0"} {:out "0"}] [{:a "1", :b "1"} {:out "1"}]]
  
  (verify/read-tests (hardware/chip-template sim)
                     ["|   a   |   b   |  out  |"
                      "|   0   |   0   |   0   |"
                      "|   0   |   1   |   0   |"
                      "|   1   |   0   |   0   |"
                      "|   1   |   1   |   1   |"])

  
  )

(comment
  (verify/verify-all sim
                     (hardware/chip-template sim)
                     (read-tests (hardware/chip-template sim)
                                 ["|   a   |   b   |  out  |"
                                  "|   0   |   0   |   0   |"
                                  "|   0   |   1   |   0   |"
                                  "|   1   |   0   |   1   |"
                                  "|   1   |   1   |   1   |"])
                     {:update :instant
                      :format :binary})
  
  (verify/verify sim
                 (hardware/chip-template sim)
                 [{:in "1010101010101010"} {:out "0101010101010101"}]
                 {:update :instant
                  :format :binary})
  
  
  (evaluate sim
            (hardware/chip-template sim)
            {:in "1010101010101010"}
            {:update :instant
             :format :binary})
  
  (hardware/load-chip sim "exercise/01/And.hdl")
  (hardware/load-chip sim "exercise/01/DMux.hdl")
  (hardware/load-chip sim "exercise/01/Not16.hdl")
  

  (def template
    (let [pins (hardware/list-pins (:simulator sim))]
      (->> [:input :output :internal]
           (map (fn [k] [k (select-pins pins k)]))
           (into {}))))
  
  
  (select-pins )
  
  (def sim (hardware/simulation))

  (hardware/list-pins (:simulator sim))
  {:y {:name :y, :width 16, :type :input}, :zx {:name :zx, :width 1, :type :input}, :zy {:name :zy, :width 1, :type :input}, :nx {:name :nx, :width 1, :type :input}, :out {:name :out, :width 16, :type :output}, :f {:name :f, :width 1, :type :input}, :x {:name :x, :width 16, :type :input}, :ny {:name :ny, :width 1, :type :input}, :zr {:name :zr, :width 1, :type :output}, :ng {:name :ng, :width 1, :type :output}, :no {:name :no, :width 1, :type :input}}
  
  (load-gate )
  (pin-info (hardware/input-pins (:simulator sim)) :input)
  
  (->> (hardware/input-pins (:simulator sim))
       (hardware/pin-info)
       (map hara.reflect/delegate))
  
  
  )