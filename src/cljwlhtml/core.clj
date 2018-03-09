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


(defn paginate []
  (html [:div {:style "margin: 10px;"}
         [:a {:class "btn btn-light" :href "?page=2&limit=10"} "Next"]
         ]))

(defn header []
  (html "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">"
        "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"></script>"
        "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"></script>"
        "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"></script>"))

(defn generate-show-html-from-database-result [database-result]
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head
     (header)]
    [:body
     [:table {:class "showTable" :border "1"} 
      [:thead
       [:tr
        [:th "id"]
        [:th "payscode"]
        [:th "payslibelle"]]]
      [:tbody
       (for [row database-result]
         (html
          [:tr
           [:td (get row :id)]
           [:td (get row :payscode)]
           [:td (get row :payslibelle)]]))]]]]   
   (paginate)))

(generate-show-html-from-database-result (take 10 (get-all-country)))








(defn get-country-html []
  (def test2 (take 10 (get-all-country)))
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head
     (header)]
    [:body
     [:table {:class "showTable" :border "1"} 
      [:thead
       [:tr
        [:th "id"]
        [:th "payscode"]
        [:th "payslibelle"]]]
      [:tbody
       ;; (for [row (generate-show-html-from-database-result (take 5 (get-all-country)))]
       (for [row test2]
         (html
          [:tr
           [:td (get row :id)]
           [:td (get row :payscode)]
           [:td (get row :payslibelle)]]))]]]]   
   (paginate)))

