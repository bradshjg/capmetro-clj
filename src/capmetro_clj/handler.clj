(ns capmetro-clj.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :refer :all]
	          [ring.middleware.defaults :refer :all]
            [clojure.data.json :as json]
            [clj-http.client :as client]))

(defn get-next-bus [stopid]
  (client/get "http://www.capmetro.org/planner/s_nextbus2.asp" {:accept :json ,:query-params {"stopid" stopid, "opt" 1}}))

(defn extract-body [response]
  (json/read-str (:body response)))

(defn make-response [body]
  {:body body,
   :status 200})

(defroutes app-routes
  (GET "/" [] (-> (get-next-bus 1042) (extract-body) (make-response)))
  (GET "/test" [] (:one {:one "one", :two "two"}))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (wrap-json-response)))
