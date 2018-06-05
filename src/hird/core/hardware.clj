(ns hird.core.hardware
  (:require [hara.reflect :as reflect]
            [hara.io.file :as fs]
            [clojure.string :as string])
  (:import Hack.HardwareSimulator.HardwareSimulator
           Hack.HardwareSimulator.HardwareSimulatorController
           SimulatorsGUI.HardwareSimulatorComponent
           SimulatorsGUI.HardwareSimulatorControllerComponent
           Hack.HardwareSimulator.Pins
           Hack.Controller.HackController))

(def update-program-file
  (reflect/query-class HardwareSimulatorController ["updateProgramFile" :#]))

(def step-task
  (reflect/query-class HackController ["singleStepTask" :#]))

(def load-gate
  (reflect/query-class HardwareSimulator ["loadGate" :#]))

(def input-pins
  (reflect/query-class HardwareSimulator ["inputPins" :#]))

(def output-pins
  (reflect/query-class HardwareSimulator ["outputPins" :#]))

(def internal-pins
  (reflect/query-class HardwareSimulator ["internalPins" :#]))

(def part-pins
  (reflect/query-class HardwareSimulator ["partPins" :#]))

(def pin-info
  (reflect/query-class Pins ["pins" :#]))

(defn list-pins-info [pins type]
  (->> (pin-info pins)
       (map (fn [p] (let [k (keyword (.-name p))]
                      [k {:name  k
                          :width (.-width p)
                          :type type}])))
       (into {})))

(defn list-pins [simulator]
  (merge (list-pins-info (input-pins simulator) :input)
         (list-pins-info (output-pins simulator) :output)
         (list-pins-info (internal-pins simulator) :internal)))
         
(def clock-up?
  (reflect/query-class HardwareSimulator ["clockUp" :#]))

(def clock-time
  (reflect/query-class HardwareSimulator ["time" :#]))

(def clock-tick
  (reflect/query-class HardwareSimulator ["performTick" :#]))

(def clock-tock
  (reflect/query-class HardwareSimulator ["performTock" :#]))

(defn clock-status [{:keys [simulator]}]
  (let [up? (boolean (clock-up? simulator))]
    {:time (clock-time simulator)
     :up?  up?
     :next (if (true? up?) :tock :tick)}))

(defn clock-step [{:keys [simulator]}]
  (let [up? (boolean (clock-up? simulator))]
    (if (true? up?)
      (clock-tock simulator)
      (clock-tick simulator))))

(defn gui-step [{:keys [controller]}]
  (.run (step-task controller)))

(defn simulation []
  (let [simulator  (HardwareSimulator. (HardwareSimulatorComponent.))
        gui        (doto (HardwareSimulatorControllerComponent.)
                     (.setDefaultCloseOperation javax.swing.WindowConstants/HIDE_ON_CLOSE))
        controller (HardwareSimulatorController. gui
                                                 simulator
                                                 "resources/scripts/defaultHW.txt")]
    {:simulator simulator
     :gui gui
     :controller controller}))


(defn load-chip [{:keys [simulator controller]} path]
  (load-gate simulator (str path) true)
  (update-program-file controller (str path)))

(defn select-pin-type [pins type]
  (reduce-kv (fn [out k opts]
               (if (= type (:type opts))
                 (assoc out k opts)
                 out))
             {}
             pins))

(defn chip-template [{:keys [simulator]}]
  (let [pins (list-pins simulator)]
    (->> [:input :output :internal]
         (map (fn [k] [k (select-pin-type pins k)]))
         (into {}))))

(comment
  (simulation))

