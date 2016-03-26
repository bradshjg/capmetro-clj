(ns capmetro-clj.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
	    [clojure.data.json :as json]
	    [clj-http.client :as client]))

(defn get-next-bus [stopid]
  (:body (client/get "http://www.capmetro.org/planner/s_nextbus2.asp" {:accept :json ,:query-params {"stopid" stopid, "opt" 1}})))
  
(defroutes app-routes
  (GET "/" [] (get-next-bus 1042))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
