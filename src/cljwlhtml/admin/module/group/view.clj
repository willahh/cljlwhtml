(ns cljwlhtml.admin.module.group.view
  (:use [hiccup.core])
  (:require [cljwlhtml.process.admin.htmlhelper :as htmlhelper]
            [cljwlhtml.model.group :as groupModel]
            [cljwlhtml.locales.frfr :as frfr]))


(defn get-show-html [record]
  (def columns (htmlhelper/get-column-name-from-record record))
  (html [:table {:class "table showTable" :style "border: 1px solid #000; width: auto;"}
         [:tbody
          (for [column columns]
            [:tr
             [:td {:width 50} column]
             [:td ((keyword column) record)]])]]))

(defn get-field-html [mode record column]
  (let [value ((keyword column) record)]
    (cond (= mode "show")
          (html value)
          
          (= mode "update")
          (html clojure.string/join ["<input value=\"" value "\""]>)

          (= mode "insert")
          (html "<input>"))))

(defn get-show-or-insert-or-update-html [page-param]
  (let [mode (:mode page-param)
        id (:id (:params page-param))
        columns (groupModel/columns)
        record (groupModel/get-row id)
        submit-line [:tr
                     [:td {:colspan "2" :style "text-align: center;"}
                      [:div
                       [:input {:type "submit" :class "btn btn-primary"}]
                       [:input {:type "reset" :value "Annuler" :class "btn" :onclick "window.history.back();"}]]]]
        body [:table {:class "table showTable" :style "border: 1px solid #000; width: auto;"}
              [:tbody
               (for [column columns]
                 [:tr
                  [:td {:width 50} column]
                  [:td (get-field-html mode record column)]])
               (when (or (= mode "insert") (= mode "update")) submit-line)
               ]]]
    (html (if (or (= mode "insert") (= mode "update"))
            [:form body]
            body))))

(defn get-page-list-html []
  (def records (groupModel/get-paginate :page 0 :limit 10))
  (def columns groupModel/columns)
  
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
  (def records (groupModel/get-paginate :page 0 :limit 10))
  (def record (first records))
  (def columns groupModel/columns)
  
  (html (htmlhelper/get-page-header-html)
        (get-page-list-html records columns)
        (get-show-html record)))

(defn get-view [route-param]
  (def page-param (htmlhelper/get-page-param-from-route-param route-param))
  
  (let [mode (:mode page-param)
        id (:id (:params page-param))
        columns groupModel/columns
        record (first frfr/locales)]
    (cond (= (:mode page-param) "list")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html)
                                           (get-page-list-html))))

          (= (:mode page-param) "update")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html id)
                                           (get-show-or-insert-or-update-html page-param))))

          (= (:mode page-param) "insert")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html id)
                                           (get-show-or-insert-or-update-html page-param))))

          (= (:mode page-param) "show")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html id)
                                           (get-show-or-insert-or-update-html page-param)))))))