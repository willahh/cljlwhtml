(ns cljwlhtml.core
  (:require [clojure.java.jdbc :refer :all :as jdbc]
            [clojure.string :refer [lower-case]]))

(use 'hiccup.core)


(def db-name "resources/databases/wlhtml.db")
(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname db-name})

(defn get-all-country []
  (jdbc/query db-spec "select * from country"))

(defn generate-show-html-from-database-result [database-result]
  (html [:table {:class "showTable"}
         [:thead
          [:tr
           [:th "id"]
           [:th "payscode"]
           [:td "payslibelle"]]]
         [:tbody
          [:tr
           (for [row database-result]
             (html
              [:td (get row :id)]
              [:td (get row :payscode)]
              [:td (get row :payslibelle)]))]]]))


(generate-show-html-from-database-result (take 10(get-all-country)))
