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

(defn update [record]
  (jdbc/query db-spec "UPDATE \"group\" SET \"id\" = '2', \"user-id\" = '1', \"name\" = 'Groupe bbcd' WHERE \"id\" = '2';"))









;; Some test
;;
;;
;;
;; https://github.com/clojure/java.jdbc
;; https://github.com/jkk/honeysql
;;
;;
;;
;; http://pesterhazy.karmafish.net/presumably/2015-05-25-getting-started-with-clojure-jdbc-and-sqlite.html
;; http://grishaev.me/en/clj-sqlite
;; -----------------------> https://en.wikibooks.org/wiki/Clojure_Programming/Examples/JDBC_Examples
;; (jdbc/update-or-insert! db-spec :group
;;                         {:name "Cactus" :appearance "Spiky" :cost 2000}
;;                         ["name = ?" "Cactus"])

;; (jdbc/execute! db-spec ["UPDATE `group` SET `name` = `test` WHERE `id` = `1`"])

;; (jdbc/query db-spec ["SELECT * FROM `group`"])
;; (jdbc/query db-spec ["SELECT * FROM `group` WHERE `id` = 2"])
;; (jdbc/query db-spec ["UPDATE `group` SET `name` = test WHERE `id` = 2"])
;; (jdbc/query db-spec ["UPDATE `group` SET `name` = \"test\" WHERE `id` = \"2\""])
;; (jdbc/query db-spec ["UPDATE `group` SET `id` = 2  `user-id` = 1 `name` = test WHERE `id` = 2"])



;; (jdbc/execute! db-spec ["insert into group (id, user-id, name) values (?, ?, ?)"
;;                         "a"
;;                         "b"
;;                         "c"])

;; (jdbc/execute! db-spec ["INSERT INTO group (id, user-id, name) values (?, ?, ?)"
;;                         "a"
;;                         "b"
;;                         "c"])

;; (jdbc/insert! db-spec :group {:id "France",
;;                               :user-name "Paris"
;;                               :name "a"})

;; (jdbc/query db-spec "select id, name, capital from group") ; ko
;; (jdbc/query db-spec "SELECT id, name, capital from GROUP") ; ko

;; (jdbc/query db-spec (clojure.string/join ["SELECT * FROM `group` WHERE `id` = " 1])) ; ok
;; (jdbc/query db-spec "SELECT * FROM `group` WHERE `id` = 1") ; ok
;; (jdbc/query db-spec "SELECT id, name FROM `group` WHERE `id` = 1") ; ok
;; (jdbc/query db-spec "UPDATE `group` SET `name` = 1 WHERE `id` = 1") ; ko

;; UPDATE "group" SET
;; "id" = '3',
;; "user-id" = '1',
;; "name" = 'Groupe cc'
;; WHERE "id" = '3';

