(ns cljwlhtml.routes
  (:use compojure.core
        cljwlhtml.core
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [cljwlhtml.admin.module.user.view]
            [cljwlhtml.core :as core]))

(def main-routes
  (compojure.core/routes
   (GET "/" [] (core/get-home-html))
   (GET "/country" params (core/get-country-preview params))
   (GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))

