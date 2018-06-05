(ns hird.core
  (:require [clojure.string :as string]
            [hird.core.hardware :as hardware]
            [hird.core.format :as format]))

(defn load-chip [{:keys [simulator controller]} path]
  (hardware/load-gate simulator (str path) true)
  (hardware/update-program-file controller (str path)))

(defn select-pin-type [pins type]
  (reduce-kv (fn [out k opts]
               (if (= type (:type opts))
                 (assoc out k opts)
                 out))
             {}
             pins))

(defn pin-template [{:keys [simulator]}]
  (let [pins (hardware/list-pins simulator)]
    (->> [:input :output :internal]
         (map (fn [k] [k (select-pin-type pins k)]))
         (into {}))))

(def formatters
  {:decimal {:get format/get-decimal-val
             :set format/set-decimal-val}
   :binary  {:get format/get-binary-val
             :set format/set-binary-val}})

(def updaters
  {:instant hardware/gui-step
   :clock   hardware/clock-step})

(defn evaluate
  ([sim template inputs]
   (evaluate sim template inputs {}))
  ([{:keys [simulator controller] :as sim} template inputs {:keys [update format] :as opts
                                                            :or {update :clock
                                                                 format :decimal}}]
   (let [{get-formatter :get set-fomatter :set} (formatters format)
         update-fn (updaters update)
         _   (doseq [in (keys (:input template))]
               (.setValue simulator (name in) (set-fomatter (get inputs in))))
         _   (update-fn sim)]
     (->> (:output template)
          (map (fn [[k p]] [k (get-formatter (.getValue simulator (name k))
                                             (:width p))]))
          (into {})))))


(evaluate sim
          (pin-template sim)
          {:in "1010101010101010"}
          {:update :instant
           :format :binary})

{:out "0101010101010101"}


(comment
  

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
