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

(defn get-html-nav []
  (html "<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow\">"
        "<h5 class=\"my-0 mr-md-auto font-weight-normal\">Cljlwhtml</h5>"
        "<nav class=\"my-2 my-md-0 mr-md-3\">"
        "<a class=\"p-2 text-dark\" href=\"/admin/module/user\"/>Administration</a>"
        "<a class=\"p-2 text-dark\" href=\"/\">Home</a>"
        "<a class=\"p-2 text-dark\" href=\"/country?page=1&limit=5\">Country</a>"
        "</nav>"
        "<a class=\"btn btn-outline-primary\" href=\"#\">Sign up</a>"
        "</div>"
        ))

(defn get-html-header []
  (html "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">"
        "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"></script>"
        "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"></script>"
        "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"></script>"))


(defn get-html-template-a [& {:keys [html-head html-body-head html-body]}]
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head (if html-head html-head)]
    [:body
     (get-html-nav)
     [:div {:class "row"}
      [:div {:class "col-sm-6"}
       (if html-body-head html-body-head)]
      [:div {:class "col-sm-6"}
       (if html-body html-body)]]]]))

(defn get-home-html []
  (get-html-template-a :html-head (get-html-header) :html-body "Home page"))



(defn get-country-list-html [page limit & {:keys [header head]}]
  (def rows (get-country :page (read-string page) :limit (read-string limit)))
  (def html-body-head (html [:h1 "Country list"]))
  (def html-head (if header (get-html-header)))
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
                              [:a  {:class "btn btn-light" :href (clojure.string/join ["/country?id=" (get row :id) "&page=" page "&limit=" limit])} "s"]
                              " "
                              [:a {:class "btn btn-light" :onclick "ok"} "e"]
                              " "
                              [:a {:class "btn btn-light" :onclick "ok"} "d"]]]]))]]                  
                  ))
  
  (get-html-template-a :html-head html-head :html-body html-body :html-body-head html-body-head))


(defn get-country-html [page limit]
  (html (get-country-list-html page limit :head true :header true)
        (paginate page limit)))

(defn get-country-show-html [id & {:keys [head header]}]
  (def row (first (get-country-one (read-string id))))
  (def html-body-head (html [:h1 "Country detail"]))
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

(defn get-country-preview [params]
  (let [{:keys [page limit id]} (:params params)]
    (cond (and (some? page) (some? id))
          (html (get-country-list-html page limit :head true :header true)
                (paginate page limit)
                (get-country-show-html id :head true))
          (some? id) (html (get-country-show-html id :head true :header true))
          (some? page) (html (get-country-list-html page limit :head true :header true)
                             (paginate page limit)))))

