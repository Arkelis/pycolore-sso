(ns server                                        
    (:require [io.pedestal.http :as http]          
              [io.pedestal.http.route :as route]
              [io.pedestal.http.body-params :as body-params]
              [pages]))


(defn respond [request]          
  {:status 200 :body request}) 

(def form-params
  (body-params/body-params))


(defn login-form-view [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (pages/signin)})

(def routes
  (route/expand-routes                                   
   #{["/auth" :post [form-params respond] :route-name :authorization-endpoint]
     ["/token" :post respond :route-name :token-endpoint]
     ["/userinfo" :get respond :route-name :userinfo-endpoint]
     ["/revoke" :get respond :route-name :revocation-endpoint]
     ["/signin" :get login-form-view :route-name :signin]
     ["/signout" :get respond :route-name :signout]})) 




(def service-map
  {::http/routes routes
   ::http/type :jetty
   ::http/port 8000
   ::http/host "0.0.0.0"
   ::http/resource-path "/assets"})

(defn start []
  (http/start (http/create-server service-map)))

(defonce server (atom nil))                                                             

(defn start-dev []
  (reset! server                                                                        
          (http/start (http/create-server
                       (assoc service-map
                              ::http/join? false)))))                                   

(defn stop-dev []
  (http/stop @server))

(defn restart []                                                                        
  (stop-dev)
  (start-dev))


