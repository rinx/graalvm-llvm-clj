(defproject graalvm-llvm-clj "0.1.0-SNAPSHOT"
  :description "call llvm from clojure"
  :url "https://github.com/rinx/graalvm-llvm-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [org.clojure/core.async "0.4.474"]
                 [com.taoensso/timbre "4.10.0"]]
  :target-path "target/%s"
  :java-source-paths []
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all
                       :main graalvm-llvm-clj.core}})
