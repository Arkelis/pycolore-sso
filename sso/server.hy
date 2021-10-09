(import 
  [starlette.applications [Starlette]]
  [starlette.routing [Route Mount]]
  [starlette.responses [HTMLResponse]]
  [starlette.staticfiles [StaticFiles]]
  [sso.pages [signin]])


(defn respond [request]          
  {:status 200 :body request}) 

(defn login-form-view [request]
  (HTMLResponse
    :content (signin)))

(setv routes                                  
  [(Route "/auth" respond :name "authorization-endpoint" :methods ['POST])
   (Route "/token" respond :name "token-endpoint" :methods ['POST])
   (Route "/userinfo" respond :name "userinfo-endpoint")
   (Route "/revoke" respond :name "revocation-endpoint" :methods ['POST])
   (Route "/signin" login-form-view :name "signin" :methods ['GET 'POST])
   (Route "/signout" respond :name "signout")
   (Mount "" :app (StaticFiles :directory "sso/assets") :name "assets")])


(setv app 
  (Starlette
    :debug True
    :routes routes))

