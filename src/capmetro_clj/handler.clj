(ns capmetro-clj.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :refer :all]
	          [ring.middleware.defaults :refer :all]
            [clojure.data.json :as json]
            [clj-http.client :as client]))

(defn extract-body [response]
  (json/read-str (:body response)))

(defn make-response [body]
  {:body body,
   :status 200})

(defn capmetro-api [endpoint params]
  (let [endpoint-map {:stop "http://www.capmetro.org/planner/s_locate.asp",
                      :next   "http://www.capmetro.org/planner/s_nextbus2.asp"}]
  (-> (client/get ((keyword endpoint) endpoint-map) {:query-params params})
      (extract-body))))

(defn get-stop [id]
  (-> (capmetro-api "stop" {"loc" id, "opt" 1})
      (make-response)))

(defn get-next-bus [id]
  (-> (capmetro-api "next" {"stopid" id, "opt" 3})
      (make-response)))

(defroutes app-routes
  (GET "/stop" [] "Still need to get all stops")
  (GET "/stop/:id" [id] (get-stop id))
  (GET "/stop/:id/next" [id] (get-next-bus id))
  (route/not-found "Not Found"))

(defn ignore-trailing-slash
  "Modifies the request uri before calling the handler.
  Removes a single trailing slash from the end of the uri if present.

  Useful for handling optional trailing slashes until Compojure's route matching syntax supports regex.
  Adapted from http://stackoverflow.com/questions/8380468/compojure-regex-for-matching-a-trailing-slash"
  [handler]
  (fn [request]
    (let [uri (:uri request)]
      (handler (assoc request :uri (if (and (not (= "/" uri))
                                            (.endsWith uri "/"))
                                     (subs uri 0 (dec (count uri)))
                                     uri))))))

(def app
  (-> (handler/api app-routes)
      (ignore-trailing-slash)
      (wrap-json-response)))
