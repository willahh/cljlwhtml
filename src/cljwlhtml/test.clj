;; (ns cljwlhtml.test
;;   (:require  [clojure.test :as t]
;;              [clojure.spec.alpha :as s]))


;; ;; (def main-routes
;; ;;   (compojure.core/routes
;; ;;    (GET "/" [] (core/get-home-html))
;; ;;    (GET "/country" params (core/get-country-preview params))))


;; (defmacro test2 [& body]
;;   `(def main-routes
;;      (compojure.core/routes
;;       ~(for [row body]
;;          row))))
;; ;; (defmacro test2 [& body]
;; ;;   `(def main-routes
;; ;;      (compojure.core/routes
;; ;;       ~@body)))

;; ;; (macroexpand-1 '(test2 
;; ;;                  `(GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))
;; ;;                  `(GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))))


;; (def testvar ['(GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))
;;               '(GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))])

;; (defmacro macrotest [& body]
;;   `(def main-routes
;;      (compojure.core/route
;;       ~(for [row body]
;;          row))))



;; (for [row testvar]
;;   row)


;; (macroexpand '(macrotest ['(GET "/admin/module/user" params (cljwlhtml.admin.module.user.view/get-view params))]))
;; (macrotest testvar)



;; (macroexpand-1 '(test2 
;;                  '(GET "/" [] "ok")
;;                  '(GET "/" [] "ok")))





;; (+ (/ 20 1000) (/ 1 3))


;; (defstruct desilu :fred :ricky)

;; (def x (map (fn [n]
;;               (struct-map desilu
;;                           :fred n
;;                           :ricky 2
;;                           :lucy 3
;;                           :ethel 4))
;;             (range 1000)))

;; (def fred (accessor desilu :fred))

;; (reduce (fn [n y] (+ n (:fred y))) 0 x)
;; (reduce (fn [n y] (+ n (fred y))) 0 x)


;; (type #{:a :b :c :d})
;; (type {:a :b :c :d})

;; (hash-set :a :b :c :d)
;; (sorted-set :a :b :c :d)

;; (def s #{:a :b :c :clcd})


