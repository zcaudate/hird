(ns hird.core.combinatorial
  (:require [hird.core.hardware :as hardware]))

(defn short-val [s]
  (cond (= 16 (count s))
        (let [h (first s)
              b (subs s 1)]
          (str (- (Short/parseShort b 2)
                  (if (= h \1) 32768 0))))

        :else (str (Short/parseShort s 2))))

(defn binary-val [s len]
  (-> (format "%32s"
              (Integer/toBinaryString (Integer/parseInt s)))
      (subs (- 32 len))
      (.replace \space \0)))

(defn evaluate [sim template inputs opts]
  (let [_ (doseq [in (:inputs template)]
            (.setValue (:simulator sim) (name in) (short-val (get inputs in))))
        _ (.run (hardware/step-task (:controller sim)))
        outputs (-> template :outputs keys)]
    (zipmap outputs
            (map (fn [p] (binary-val (.getValue (:simulator sim)
                                                (name p))
                                     (-> template :outputs p :length)))
                 outputs))))
