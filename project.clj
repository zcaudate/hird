(defproject zcaudate/hird "0.1.0-SNAPSHOT"
  :description "simulations for nand2tetris"
  :url "http://www.github.com/zcaudate/hird"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [hack/core       "2.5.7"]
                 [hack/compilers  "2.5.7"]
                 [hack/simulators "2.5.7"]
                 [hack/gui-assembler "2.5.7"]
                 [hack/gui-core "2.5.7"]
                 [hack/gui-simulators "2.5.7"]
                 [hack/gui-translators "2.5.7"]]
  :aliases {"test" ["run" "-m" "hara.test" "exit"]}
  :profiles {:dev {:dependencies [[zcaudate/lucid.distribute "1.4.5"]
                                  [zcaudate/lucid.mind       "1.4.5"]
                                  [zcaudate/lucid.publish    "1.4.5"]
                                  [zcaudate/lucid.unit       "1.4.5"]]
                   :plugins [[lein-ancient "0.6.15"]
                             [lein-localrepo "0.5.4"]
                             [cider/cider-nrepl "0.17.0"]]}}
  :repl-options {:host "0.0.0.0"})
