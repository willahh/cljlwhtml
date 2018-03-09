(ns cljwlhtml.core
  (:require [clojure.java.jdbc :refer :all :as jdbc]
            [clojure.string :refer [lower-case]]))


(use 'hiccup.core)

(def default-page 1)
(def default-limit 10)

(def db-name "resources/databases/wlhtml.db")
(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname db-name})

(defn get-all-country []
  (jdbc/query db-spec "select * from country"))

(defn get-country-one [id]
  (jdbc/query db-spec (clojure.string/join ["select * from country where id = " id])))

(defn get-country [& {:keys [page limit] :or {page default-page limit default-limit}}]
  (jdbc/query db-spec (clojure.string/join ["select * from `country` limit " limit " offset " page])))

(defn paginate [page limit]
  (def prev-params (clojure.string/join ["?page=" (- (read-string page) (read-string limit)) "&limit=" 10]))
  (def next-params (clojure.string/join ["?page=" (+ (read-string page) (read-string limit)) "&limit=" 10]))
  
  (html [:div {:style "margin: 10px;"}
         [:a {:class "btn btn-light" :href prev-params} "Prev"]
         [:a {:class "btn btn-light" :href next-params} "Next"]]))

(defn header []
  (html "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">"
        "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"></script>"
        "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"></script>"
        "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"></script>"))

(defn navigation []
  (html [:div "Nav"
         [:ul
          [:li [:a {:href "/"} "/"]]
          [:li [:a {:href "/country?page=1&limit=5"} "/country"]]
          [:li [:a {:href "/country2"} "/country2"]]]]))

(defn generate-show-html-from-database-result [database-result]
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head
     (header)]
    [:body
     [:h1 "Home"]
     (navigation)]]))

;; country

(defn get-country-html [page limit]
  (def rows (get-country :page (read-string page) :limit (read-string limit)))
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head
     (header)]
    [:body
     [:h1 "Country"]
     (navigation)
     [:table {:class "listTable" :border "1"} 
      [:thead
       [:tr
        [:th "id"]
        [:th "payscode"]
        [:th "payslibelle"]
        [:th "action"]]]
      [:tbody
       (for [row rows]
         (html
          [:tr
           [:td (get row :id)]
           [:td (get row :payscode)]
           [:td (get row :payslibelle)]
           [:td [:div
                 [:a  {:class "btn btn-light" :href (clojure.string/join ["country/show?id=" (get row :id)])} "s"]
                 [:button {:class "btn btn-light" :onclick "ok"} "e"]
                 [:button {:class "btn btn-light" :onclick "ok"} "d"]]]]))]]]]   
   (paginate page limit)))











(defn get-html-template-a [& {:keys [html-head html-body-head html-body]}]
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head (if html-head html-head)]
    [:body
     (if html-body-head html-body-head)
     (if html-body html-body)]]))

(defn get-country-show-html [id & {:keys [head]}]
  (def row (first (get-country-one (read-string id))))
  (def html-body-head (html [:h1 "Country detail"]
                            (navigation)))
  (def html-body (html 
                  [:table {:class "showTable" :border "1"} 
                   [:tbody
                    [:tr
                     [:td "id"]
                     [:td (get row :id)]]
                    [:tr
                     [:td "payscode"]
                     [:td (get row :payscode)]]
                    [:tr
                     [:td "payslibelle"]
                     [:td (get row :payslibelle)]]]]))
  (if head
    (get-html-template-a :html-body html-body :html-body-head html-body-head)
    (get-html-template-a :html-body html-body)))

(defn get-country-list-html [page limit & {:keys [head]}]
  (def rows (get-country :page (read-string page) :limit (read-string limit)))
  (def html-body-head (html [:h1 "Country list"]
                            (navigation)))
  (def html-body (html
                  [:table {:class "listTable" :border "1"} 
                   [:thead
                    [:tr
                     [:th "id"]
                     [:th "payscode"]
                     [:th "payslibelle"]
                     [:th "action"]]]
                   [:tbody
                    (for [row rows]
                      (html
                       [:tr
                        [:td (get row :id)]
                        [:td (get row :payscode)]
                        [:td (get row :payslibelle)]
                        [:td [:div
                              [:a  {:class "btn btn-light" :href (clojure.string/join ["country/show?id=" (get row :id)])} "s"]
                              [:button {:class "btn btn-light" :onclick "ok"} "e"]
                              [:button {:class "btn btn-light" :onclick "ok"} "d"]]]]))]]                  
                  ))
  (if head
    (get-html-template-a :html-body html-body :html-body-head html-body-head)
    (get-html-template-a :html-body html-body)))



(defn get-country-preview [params]
  (let [page (:page (:params params))
        limit (:limit (:params params))
        id "1"]
    (html (get-country-list-html page limit :head true)
          (get-country-show-html id :head false))))
