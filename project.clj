(defproject capmetro-clj "0.1.0-SNAPSHOT"
  :description "REST API for undocumented CapMetro API"
  :url "http://bradshaw-rpi.ddns.net/capmetro"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.5"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
		 [ring/ring-json "0.4.0"]
		 [clj-http "2.1.0"]
		 [cheshire "5.0.2"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler capmetro-clj.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
