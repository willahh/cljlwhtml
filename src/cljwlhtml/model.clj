(ns cljwlhtml.model
  (:require [clojure.java.jdbc :refer :all :as jdbc]
            [clojure.string :refer [lower-case]]))

(def default-page 1)
(def default-limit 10)

(def db-name "resources/databases/wlhtml.db")
(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname db-name})

;; (defn get-all-country []
;;   (jdbc/query db-spec "select * from country"))

(defn get-country-one [id]
  (jdbc/query db-spec (clojure.string/join ["select * from country where id = " id])))

(defn get-country [& {:keys [page limit] :or {page default-page limit default-limit}}]
  (jdbc/query db-spec (clojure.string/join ["select * from `country` limit " limit " offset " page])))