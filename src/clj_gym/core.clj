(ns clj-gym.core
  "Interact with OpenAI's gym-http-api server"
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(def ^:dynamic *url* "http://localhost:5000")

(defn- req [method rel-url body]
  (let [req (merge {:url (str *url* "/v1/envs/" rel-url)
                    :method method
                    :content-type :json :accept :json}
                   (when body {:body (json/generate-string body)}))]
    (-> (http/request req) :body (json/parse-string true))))

(defn create
  "Create instance of environment."
  [env-id]
  (:instance_id (req :post nil {:env_id env-id})))

(defn envs
  "All running environments."
  []
  (:all_envs (req :get nil nil)))

(defn reset
  "Reset environment and return initial observation."
  [instance-id]
  (req :post (str (name instance-id) "/reset/") nil))

(defn step
  "Step though environment using action.
  Return map of {:observation _ :reward _ :done _ :info _}"
  [instance-id action]
  (req :post (str (name instance-id) "/step/") {:action action}))

(defn action-space
  "Name and dimensions/bounds of action_space."
  [instance-id]
  (:info (req :get (str (name instance-id) "/action_space/") nil)))

(defn observation-space
  "Name and dimensions/bounds of observation_space."
  [instance-id]
  (:info (req :get (str (name instance-id) "/observation_space/") nil)))

(defn start-monitor [instance-id directory & {:keys [force? resume?]}]
  (req :post (str (name instance-id) "/monitor/start/")
       {:directory directory
        :force (if force? true false)
        :resume (if resume? true false)}))

(defn close-monitor
  "Flush monitor data to disk."
  [instance-id]
  (req :post (str (name instance-id) "/monitor/close/") nil))
