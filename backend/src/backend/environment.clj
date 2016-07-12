(ns backend.environment
  (:require [environ.core :refer [env]]))

(defn get-env-port
  "detect PORT environment variable"
  []
  (if-let [port (env :port)]
    (do (prn "Environment variable PORT detected: " port)
        (Integer. port))
    (do (prn "No-Environment variable PORT, setting default port as 8080")
        8080)))

(defn get-env-db-url
  "detect DATABASE_URL environment variable"
  []
  (if-let [url (or (env :database-url) (env :cleardb-database-url)) ]
    (do (prn "Environment variable DATABASE_URL detected: " url)
        url)
    (let [default-db "jdbc:mysql://localhost:3306/cfm_restaurants?user=root"]
      (prn "No-Environment variable DATABASE_URL, setting default url as " default-db)
      default-db)))

(defn in-prod?
  "verifies if it's in production mode (environment variable PRODUCTION)"
  []
  (if-let [production (env :production)]
    (do (prn "Production Mode ON, environment variable PRODUCTION=" production)
        true)
    (do (prn "No-Environment variable PRODUCTION, production mode false")
        false)))
