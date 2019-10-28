(ns graalvm-llvm-clj.core
  (:require
    [clojure.spec.alpha :as spec]
    [clojure.core.async :as async :refer [<! >! <!! >!!]]
    [clojure.java.io :as io]
    [taoensso.timbre :as timbre])
  (:import
    [org.graalvm.polyglot Source Context Value])
  (:gen-class))

(defn- execute
  [^Value execable & args]
  (.execute execable (object-array args)))

(defn -main []
  (timbre/set-level! :info)
  (let [bc (io/as-file "hello.bc")
        source (-> (Source/newBuilder "llvm" bc)
                   (.build))
        polyglot (-> (Context/newBuilder (into-array ["llvm"]))
                    (.allowAllAccess true)
                    (.build))]
    (-> polyglot
        (.eval source)
        (execute))))
