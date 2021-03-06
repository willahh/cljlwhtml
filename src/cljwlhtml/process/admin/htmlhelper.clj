(ns cljwlhtml.process.admin.htmlhelper
  (:use [hiccup.core]))

(defn get-html-nav []
  (html "<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow\">"
        "<h5 class=\"my-0 mr-md-auto font-weight-normal\">Administration</h5>"
        "<nav class=\"my-2 my-md-0 mr-md-3\">"
        "<a class=\"p-2 text-dark\" href=\"/\"/>Front</a>"
        "<a class=\"p-2 text-dark\" href=\"/admin/module/user\">User</a>"
        "<a class=\"p-2 text-dark\" href=\"/admin/module/language\">Translation</a>"
        "<a class=\"p-2 text-dark\" href=\"/admin/module/group\">Group</a>"
        "<a class=\"p-2 text-dark\" href=\"#\">Pricing</a>"
        "</nav>"
        "<a class=\"btn btn-outline-primary\" href=\"#\">Sign up</a>"
        "</div>"
        ))

(defn get-html-template-a [& {:keys [html-head html-body]}]
  (html 
   "<!DOCTYPE html>"
   [:html
    [:head (if html-head html-head)]
    [:body
     (get-html-nav)
     (if html-body html-body)]]))

(defn get-html-header []
  (html "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">"
        "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"></script>"
        "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"></script>"
        "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"></script>"))


(defn get-action-link [record action]
  (let [id (:id record)]    
    (cond (= action "show")
          (clojure.string/join ["?mode=show&id=" id])

          (= action "insert")
          (clojure.string/join ["?mode=insert"])

          (= action "update")
          (clojure.string/join ["?mode=update&id=" id])

          (= action "delete")
          (clojure.string/join ["?mode=delete&id=" id]))))

(defn get-page-header-html 
  ([module-configuration]
   (html [:div
          [:h3 (:label module-configuration)]]
         [:div 
          [:a {:class "btn btn-light" :href "./language"} "List"]
          [:a {:class "btn btn-light" :href (get-action-link nil "insert")} "Add new"]]))

  ([module-configuration record]
   (html [:div
          [:h3 (:label module-configuration)]]
         [:div 
          [:a {:class "btn btn-light" :href "./language"} "List"]
          (when (some? record)
            (html [:a {:class "btn btn-light" :href (get-action-link record "insert")} "Add new"]
                  [:a {:class "btn btn-light" :href (get-action-link record "update")} "Update"]
                  [:a {:class "btn btn-light" :href (get-action-link record "show")} "Show"]))])))

(defn get-field-html [mode record column]
  (let [field-name column
        field-value ((keyword column) (first record))]
    (cond (= mode "show")
          (html field-value)
          
          (= mode "update")
          (html (clojure.string/join ["<input name=\"" field-name "\" value=\"" field-value "\">"]))
          

          (= mode "insert")
          (html "<input>"))))

(defn get-html [html]
  (get-html-template-a :html-head (get-html-header) :html-body html))

(defn get-column-name-from-record [record]
  (map (fn [a]
         (name (first a))) record))

(defn get-page-param-from-route-param [params]
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

(defn get-action-html [record]
  (html [:div {:class "action"}
         [:a {:href (get-action-link record "show") :class "btn"} "Show"]
         [:a {:href (get-action-link record "update") :class "btn"} "Edit"]
         [:a {:href (get-action-link record "delete") :class "btn"} "Delete"]]))


(defn get-page-list-options-html []
  (html [:div {:class "option"}
         ;; "Pagination - Select all - Selection action - Import - Export - Show as json"
         ]))

(defn get-submit-line-html [record]
  (html [:tr
         [:td {:colspan "2" :style "text-align: center;"}
          [:div
           [:input {:type "submit" :class "btn btn-primary"}]
           [:input {:type "reset" :value "Annuler" :class "btn" :onclick "window.history.back();"}]]]]))


(defn get-show-navigation [record]
  (html [:div "show nav"]))