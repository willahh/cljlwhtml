(defproject cljwhtml "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [hiccup "1.0.5"]
                 [compojure "1.1.1"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler cljwlhtml.routes/app})
