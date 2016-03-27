(ns capmetro-clj.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [capmetro-clj.handler :refer :all]))

(deftest test-app
  (testing "get stop"
    (let [response (app (mock/request :get "/stop/1042"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "get stop with slash"
    (let [response (app (mock/request :get "/stop/1042/"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "get next"
    (let [response (app (mock/request :get "/stop/1042/next"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "get next with slash"
    (let [response (app (mock/request :get "/stop/1042/next/"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
