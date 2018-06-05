(ns hird.core.format)

(defn set-binary-val [s]
  (cond (= 16 (count s))
        (let [h (first s)
              b (subs s 1)]
          (str (- (Short/parseShort b 2)
                  (if (= h \1) 32768 0))))

        :else (str (Short/parseShort s 2))))

(defn get-binary-val [s len]
  (-> (format "%32s"
              (Integer/toBinaryString (Integer/parseInt s)))
      (subs (- 32 len))
      (.replace \space \0)))

(defn set-decimal-val [s]
  (Short/parseShort s))

(defn get-decimal-val [s]
  (str s))
