(ns cljwlhtml.process.admin.htmlhelper
  (:use [hiccup.core]))

(defn get-html-nav []
  (html "<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow\">"
        "<h5 class=\"my-0 mr-md-auto font-weight-normal\">Administration</h5>"
        "<nav class=\"my-2 my-md-0 mr-md-3\">"
        "<a class=\"p-2 text-dark\" href=\"/\"/>Front</a>"
        "<a class=\"p-2 text-dark\" href=\"/admin/module/user\">User</a>"
        "<a class=\"p-2 text-dark\" href=\"/admin/module/language\">Translation</a>"
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

(defn get-html [html]
  (get-html-template-a :html-head (get-html-header) :html-body html))





