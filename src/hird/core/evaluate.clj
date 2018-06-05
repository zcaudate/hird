(ns hird.core.evaluate
  (:require [hird.core.hardware :as hardware]
            [hird.core.format :as format]))


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

