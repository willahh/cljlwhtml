(ns cljwlhtml.admin.module.user.view
  (:use [hiccup.core])
  (:require [cljwlhtml.process.admin.htmlhelper :as htmlhelper]
            [cljwlhtml.model.user :as userModel]))

(defn get-column-name-from-record [row]
  (map (fn [a]
         (name (first a))) row))

(defn get-show-html [row]
  (def columns (get-column-name-from-record row))
  (html [:table {:class "table showTable" :style "border: 1px solid #000; width: auto;"}
         [:tbody
          (for [column columns]
            [:tr
             [:td {:width 50} column]
             [:td ((keyword column) row)]])]]))

(defn get-list-html []
  (def records (userModel/get-paginate :page 0 :limit 10))
  (def columns (get-column-name-from-record (first records)))
  
  (html [:table {:class "table listTable" :style "border: 1px solid #000"}
         [:thead
          [:tr
           (for [column columns]          
             [:th column])]]
         [:tbody
          (for [record records]          
            [:tr
             (for [column columns]
               [:td ((keyword column) record)])])]]))

(defn get-html []
  (def row (first (userModel/get-row 1)))
  (html (get-list-html)
        (get-show-html row)))


(defn get-view [params]
  (html (htmlhelper/get-html (get-html))))



