(ns cljwlhtml.admin.module.group.view
  (:use [hiccup.core])
  (:require [cljwlhtml.process.admin.htmlhelper :as htmlhelper]
            [cljwlhtml.model.group :as groupModel]
            [cljwlhtml.locales.frfr :as frfr]))

;; TODO - Definir un vrai objet avec description etc
(def module-configuration {:name "group" :label "User group"})


(defn get-show-or-insert-or-update-html [page-param]
  (let [mode (:mode page-param)
        id (:id (:params page-param))
        columns groupModel/columns
        record (groupModel/get-row id)
        submit-line (htmlhelper/get-submit-line-html record)
        body [:table {:class "table showTable" :style "border: 1px solid #000; width: auto;"}
              [:tbody
               (for [column columns]
                 [:tr
                  [:td {:width 50} column]
                  [:td (htmlhelper/get-field-html mode record column)]])
               (when (or (= mode "insert") (= mode "update")) submit-line)
               ]]]
    (html (if (or (= mode "insert") (= mode "update"))
            [:form {:method "post" :action "save"} body]
            (html body
                  (htmlhelper/get-show-navigation record))))))

(defn get-page-list-html [records columns]
  (html [:table {:class "table listTable" :style "border: 1px solid #000"}
         [:thead
          [:tr
           (for [column columns]          
             [:th column])
           [:th "actions"]]]
         [:tbody
          (for [record records]          
            [:tr
             [:td "<input type=\"checkbox\">"]
             (for [column columns]
               [:td ((keyword column) record)]
               [:td (htmlhelper/get-field-html record column)])
             [:td (htmlhelper/get-action-html record)]])]]))

(defn get-view [route-param]
  (def page-param (htmlhelper/get-page-param-from-route-param route-param))
  
  (let [mode (:mode page-param)
        id (:id (:params page-param))
        columns groupModel/columns
        record (first frfr/locales)]
    (cond (= (:mode page-param) "list")
          (html (htmlhelper/get-html (let [records (groupModel/get-paginate :page 0 :limit 10)
                                           columns groupModel/columns]
                                       (html (htmlhelper/get-page-header-html module-configuration)
                                             (get-page-list-html records columns)
                                             (htmlhelper/get-page-list-options-html)))))

          (= (:mode page-param) "update")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html module-configuration record)
                                           (get-show-or-insert-or-update-html page-param))))

          (= (:mode page-param) "insert")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html module-configuration record)
                                           (get-show-or-insert-or-update-html page-param))))

          (= (:mode page-param) "show")
          (html (htmlhelper/get-html (html (htmlhelper/get-page-header-html module-configuration record)
                                           (get-show-or-insert-or-update-html page-param)))))))


;; ;; test
;; (def column "id")
;; (def record (first (groupModel/get-paginate :page 0 :limit 10)))

;; (htmlhelper/get-action-link (first (groupModel/get-paginate :page 0 :limit 10)) "update")
;; (htmlhelper/get-page-header-html (first (groupModel/get-paginate :page 0 :limit 10)))
;; (htmlhelper/get-page-header-html {:id 1, :user-id 1, :name "Groupe a"})
;; (get-show-or-insert-or-update-html {:mode "show", :params {:id 1}})

;; (htmlhelper/get-field-html "update" record column)




;; (let [records (groupModel/get-paginate :page 0 :limit 10)
;;       columns groupModel/columns]
;;   (htmlhelper/get-page-header-html)
;;   (get-page-list-html records columns)
;;   (htmlhelper/get-page-list-options-html))