(ns cljwlhtml.routes
  (:use compojure.core
        compojure.example.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))


(use 'compojure.core)

(defroutes main-routes
  (GET "/" [] "ok"))


(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))

