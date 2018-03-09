(ns cljwlhtml.routes
  (:use compojure.core
        cljwlhtml.core
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))


(use 'compojure.core)

(defroutes main-routes
  (GET "/" [] (generate-show-html-from-database-result (take 10 (get-all-country))))
  (GET "/country" [page limit] (get-country-html page limit))
  (GET "/country2" [page limit] (get-country-html page limit)))


(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))

