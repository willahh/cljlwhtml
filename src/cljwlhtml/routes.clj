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
  (GET "/country/show" [id] (get-country-show-html id))
  ;; (GET "/countrypreview" {{:keys [page limit]}} (get-country-preview page limit))
  ;; (GET "/countrypreview" [page limit id] (get-country-preview page limit id))
  (GET "/countrypreview" params (get-country-preview params)))


(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))

