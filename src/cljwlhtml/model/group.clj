(ns cljwlhtml.model.group
  (:require [clojure.java.jdbc :refer :all :as jdbc]
            [clojure.string :refer [lower-case]]))

(def default-page 1)
(def default-limit 10)
(def columns ["id" "user-id" "name"])

(def db-name "resources/databases/wlhtml.db")
(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname db-name})

(defn get-row [id]
  (jdbc/query db-spec (clojure.string/join ["SELECT * FROM `group` WHERE `id` = " id])))

(defn get-paginate [& {:keys [page limit] :or {page default-page limit default-limit}}]
  (jdbc/query db-spec (clojure.string/join ["SELECT * FROM `group` LIMIT " limit " OFFSET " page])))
