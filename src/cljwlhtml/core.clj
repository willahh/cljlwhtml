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

;; (defn get-country [& [page limit]]
;;   (let [page (if page page default-page)
;;         limit (if limit limit default-limit)]
;;     (jdbc/query db-spec (clojure.string/join ["select * from `country` limit " limit " offset " page]))))

(defn get-country [& {:keys [page limit] :or {page default-page limit default-limit}}]
  (jdbc/query db-spec (clojure.string/join ["select * from `country` limit " limit " offset " page])))

(defn paginate []
  (html [:div {:style "margin: 10px;"}
         [:a {:class "btn btn-light" :href "?page=2&limit=10"} "Next"]
         ]))

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

(defn get-country-html [& page limit]
  (printl "yo")
  (def rows (get-country :page (read-string page) :limit (read-string limit)))
  
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head
     (header)]
    [:body
     [:h1 "Country"]
     (navigation)
     [:table {:class "showTable" :border "1"} 
      [:thead
       [:tr
        [:th "id"]
        [:th "payscode"]
        [:th "payslibelle"]]]
      [:tbody
       (for [row rows]
         (html
          [:tr
           [:td (get row :id)]
           [:td (get row :payscode)]
           [:td (get row :payslibelle)]]))]]]]   
   (paginate)))

