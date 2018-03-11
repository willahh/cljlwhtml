(ns cljwlhtml.routes
  (:use compojure.core
        cljwlhtml.core
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [cljwlhtml.admin.module.user.view]
            [cljwlhtml.admin.module.group.view]
            [cljwlhtml.admin.module.language.view]
            [cljwlhtml.core :as core]))

(def main-routes
  (compojure.core/routes
   (GET "/" [] (core/get-home-html))
   (GET "/country" params (core/get-country-preview params))
   (GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))
   (GET "/admin/module/language" params (cljwlhtml.admin.module.language.view/get-view params))
   (GET "/admin/module/group" params (cljwlhtml.admin.module.group.view/get-view params))))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))

