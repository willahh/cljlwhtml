(ns cljwlhtml.admin.module.language.view
  (:use [hiccup.core])
  (:require [cljwlhtml.process.admin.htmlhelper :as htmlhelper]
            [cljwlhtml.model.user :as userModel]
            [cljwlhtml.locales.frfr :as frfr]))


(defn get-column-name-from-record [row]
  (map (fn [a]
         (name (first a))) row))

(defn get-page-header-html []
  (html [:div
         [:h3 "Language"]]
        [:div 
         [:a {:class "btn btn-light" :href "?mode=insert"} "Add new"]]))

(defn get-show-html [row]
  (def columns (get-column-name-from-record row))
  (html [:table {:class "table showTable" :style "border: 1px solid #000; width: auto;"}
         [:tbody
          (for [column columns]
            [:tr
             [:td {:width 50} column]
             [:td ((keyword column) row)]])]]))

(defn get-field-html [record columns]
  (if (some? record)
    (html "<input>")
    (html ((keyword columns) record))))

(defn get-insertupdate-html [columns & record]
  (html [:table {:class "table showTable" :style "border: 1px solid #000; width: auto;"}
         [:tbody
          (for [column columns]
            [:tr
             [:td {:width 50} column]
             [:td (get-field-html record columns)]])
          [:tr
           ]]]))

(defn get-list-html [records column]
  (html [:table {:class "table listTable" :style "border: 1px solid #000"}
         [:thead
          [:tr
           (for [column columns]          
             [:th column])]]
         [:tbody
          (for [record records]          
            [:tr
             (for [column columns]
               [:td ((keyword column) record)]
               [:td (get-field-html record column)])])]]))

(defn get-html []
  (def records frfr/locales)
  (def row (first frfr/locales))
  (def columns ["locale" "group" "key" "value"])
  
  (html (get-page-header-html)
        (get-list-html records columns)
        (get-show-html row)))

(defn get-insert-or-update-html [& id]
  (def records frfr/locales)
  (def row (first frfr/locales))
  (def columns ["locale" "group" "key" "value"])
  
  (html (get-page-header-html)
        (get-insertupdate-html row columns)))


(defn get-view [params]
  (let [{:keys [mode page limit]} (:params params)]
    (html (print-str mode))
    (cond (some? mode)
          (html (htmlhelper/get-html (get-insert-or-update-html)))

          :else
          (html (htmlhelper/get-html (get-html))))))
