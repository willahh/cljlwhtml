(ns cljwlhtml.admin.module.user.view
  (:use [hiccup.core])
  (:require [cljwlhtml.process.admin.htmlhelper]))

;; (def routes
;;   '(GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params)))



(defn get-html []
  (html [:div {:class "col col-sm-6"}
         [:div "ok"]]
        [:div {:class "col col-sm-6"}
         [:div "ok"
          [:button {:class "btn btn-light"} "Validate"]
          [:button {:class "btn btn-light"} "Cancel"]]]))


(defn get-view [params]
  (html (cljwlhtml.process.admin.htmlhelper/get-html (get-html))))


