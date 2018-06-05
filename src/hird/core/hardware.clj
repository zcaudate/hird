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

(def pin-info
  (reflect/query-class Pins ["pins" :#]))

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