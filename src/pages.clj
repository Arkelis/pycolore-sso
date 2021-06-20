(ns pages
  (:use [hiccup.core]
        [hiccup.page]
        [hiccup.form]))

(defn html-head [title css-urls js-urls & others]
  [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title title]
    (for [css-url css-urls]
      (include-css css-url))
    (for [js-url js-urls]
      (include-css js-url))
    others])


(defn signin
  ([] (signin false))
  ([retry?]
    (html5 {:lang "fr"}
      (html-head
        "Se connecter | Pycolore"
        ["/style.css"
         "https://fonts.googleapis.com/css?family=Fira+Sans:300,400,700%7CPT+Serif:400,700&amp;display=swap"] 
        [])
      [:body
        [:h1 "Service d'auhentification Pycolore"]
        (form-to [:post "/signin"]
          [:h2 (if retry?
            "Veuillez réessayer"
            "Connectez-vous pour continuer")]
          [:div.form-group
            (label :username "Nom d'utilisateur")
            (text-field :username)]
          [:div.form-group
            (label :password "Mot de passe")
            (password-field :password)]
          (if retry?
            [:p.error "Utilisateur ou mot de passe incorrect."])
          (submit-button "Se connecter"))])))
