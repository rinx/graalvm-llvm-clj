(ns dev
  (:require
    [clojure.java.io :as io]
    [clojure.pprint :refer [pprint]]
    [clojure.tools.namespace.repl :as repl
     :refer [refresh refresh-all]]
    [clojure.spec.alpha :as spec]
    [taoensso.timbre :as timbre]))

(timbre/set-level! :debug)

defn reset []
  (refresh))
