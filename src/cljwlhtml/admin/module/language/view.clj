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

(defn get-field-html [mode record column]
  (cond (= mode "show")
        (html ((keyword column) record))
        
        (= mode "update")
        (html "<input value\"ok\">")

        (= mode "insert")
        (html "<input>")))

(defn get-show-or-insert-or-update-html [page-param]
  (let [mode (:mode page-param)
        id (:id (:params page-param))
        columns ["locale" "group" "key" "value"]
        record (first frfr/locales)
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
            ;; [:h3 (print-str mode)]
            [:form body]
            body))))

(defn get-page-list-html []
  (def records frfr/locales)
  (def columns ["locale" "group" "key" "value"])
  
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
        (get-page-list-html records columns)
        (get-show-html row)))

(defn get-page-param-from-route-param [params]
  "Get an Arraymap of mode and paramters from a route params
  object.

  e.g.
  'module/language' -> {:mode 'list'}
  'module/language?mode=insert' -> {:mode 'insert'}
  'module/language?mode=update&id=1' -> {:mode 'insert' :params {:id 1}}
  'module/language?mode=show&id=1' -> {:mode 'show' :params {:id 1}}"
  
  (let [{:keys [mode page limit id]} (:params params)]
    (cond 
      ;; Insert
      (and (some? mode) (= mode "insert"))
      {:mode "insert"}

      ;; Update
      (and (and (some? mode) (= mode "update")) (some? id))
      {:mode "update"
       :params {:id id}}

      ;; Show
      (and (and (some? mode) (= mode "show")) (some? id))
      {:mode "show"
       :params {:id id}}

      ;; List
      :else
      {:mode "list"})))


(defn get-view [params]
  (def page-param (get-page-param-from-route-param params))
  (cond (= (:mode page-param) "list")
        (html (htmlhelper/get-html (get-page-list-html)))

        (= (:mode page-param) "update")
        (html (htmlhelper/get-html (html (get-page-header-html)
                                         (get-show-or-insert-or-update-html page-param))))

        (= (:mode page-param) "insert")
        (html (htmlhelper/get-html (html (get-page-header-html)
                                         (get-show-or-insert-or-update-html page-param))))

        (= (:mode page-param) "show")
        (html (htmlhelper/get-html (html (get-page-header-html)
                                         (get-show-or-insert-or-update-html page-param))))))




(get-show-or-insert-or-update-html {:mode "list"})
(get-show-or-insert-or-update-html {:mode "show"
                                    :params {:id 1}})
