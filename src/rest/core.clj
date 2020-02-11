(ns rest.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class))

;Body page
(defn simple-body-page [req]
  {:stauts 200
   :headers {"Content-Type" "text/html"}
    :body "Hello World"}
  )

;Request example
(defn request-example [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->
             (pp/pprint req)
             (str "Request Object: " req))})

; Hello-name handler
(defn hello-name [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->
             (pp/pprint req)
             (str "Hello " (:name (:params req))))})

;Routes
(defroutes app-routes
  (GET "/" [] simple-body-page)
  (GET "/request" [] request-example)
  (GET "/hello" [] hello-name)
  (route/not-found "Error, page not found!"))

;Main functions
(defn -main
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server ( wrap-defaults #'app-routes site-defaults)
                       {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
