(ns restaurant-inspections-api.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.reload :as reload]
            [compojure.handler :refer [site]]
            [taoensso.timbre :refer [info]]
            ;; internal
            [restaurant-inspections-api.constants :as const]
            [restaurant-inspections-api.cors :refer [all-cors]]
            [restaurant-inspections-api.routes :refer [all-routes]]
            [restaurant-inspections-api.cron.core :refer [load-api-data]])
  (:gen-class))

(defn -main
  "Starts server and schedules load-api-data process."
  [& args]
  (let [handler (if const/production?
                  (all-cors (site all-routes))
                  (reload/wrap-reload (all-cors (site #'all-routes))))
        port const/port]
    (info (str "Running server on port " port))
    (load-api-data)
    (run-server handler {:port port})))
