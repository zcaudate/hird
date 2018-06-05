(ns hird.core
  (:require [hird.core.evaluate :as evaluate]
            [hird.core.hardware :as hardware]
            [hird.core.verify :as verify]
            [hara.io.file :as fs]))

(defonce ^:dynamic *sim* nil)

(defonce ^:dynamic *curr-dir* nil)

(defonce ^:dynamic *curr-opts* nil)

(defn simulation-instance []
  (or *sim*
      (let [sim (hardware/simulation)]
        (alter-var-root #'*sim*
                        (fn [_] sim)))))

(defn change-dir [dir]
  (alter-var-root #'*curr-dir*
                  (fn [_] dir)))

(defn current-dir []
  (or *curr-dir*
      (throw (Exception. (str "No directory selected")))))

(defn change-opts [opts]
  (alter-var-root #'*curr-opts*
                  (fn [_] opts)))

(defn current-opts []
  *curr-opts*)

(defn list-chips
  ([]
   (list-chips (current-dir)))
  ([dir]
   (change-dir dir)
   (->> (fs/select dir {:include [".hdl"]})
        (map (fn [f] (let [s (str (.getFileName f))]
                       (subs s 0 (- (count s) 4)))))
        (sort))))

(defn load-chip
  ([chip]
   (load-chip (simulation-instance) (current-dir) chip))
  ([sim dir chip]
   (if-let [file (first (fs/select dir {:include [(str chip ".hdl")]}))]
     (hardware/load-chip sim (str file))
     (throw (Exception. (str "Cannot find " dir "/" chip ".hdl"))))))

(defn load-tests
  ([chip]
   (load-tests (simulation-instance) (current-dir) chip))
  ([sim dir chip]
   (let [[chip test] (if (vector? chip)
                       chip
                       [chip chip])]
     (load-chip sim dir chip)
     (if-let [file (first (fs/select dir {:include [(str chip ".cmp")]}))]
       (verify/read-tests (hardware/chip-template sim) file)
       (throw (Exception. (str "Cannot find " dir "/" chip ".cmp")))))))

(defn test-chip
  ([chip opts]
   (test-chip (simulation-instance)
              (current-dir)
              chip
              opts))
  ([sim dir chip opts]
   (let [tests (load-tests sim dir chip)
         template (hardware/chip-template sim)]
     (verify/verify-all sim template tests opts))))

(defn eval-chip
  ([chip opts]
   (eval-chip (simulation-instance)
              (current-dir)
              chip
              opts))
  ([sim dir chip opts]
   (let [tests (load-tests sim dir chip)
         template (hardware/chip-template sim)
         inputs (map first tests)]
     (reduce (fn [out input]
               (conj out [input (evaluate/evaluate sim template input opts)]))
             []
             inputs))))
