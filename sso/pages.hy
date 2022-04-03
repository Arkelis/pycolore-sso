(import 
  hyccup.page [html5 include-css include-js]
  hyccup.form [form-to label text-field password-field submit-button])

(defn html-head [title css-urls js-urls #* others]
  ['head
    ['meta {'charset "utf-8"}]
    ['meta {'name "viewport" 'content "width=device-width, initial-scale=1.0"}]
    ['title title]
    #* (include-css #* css-urls)
    #* (include-css #* js-urls)
    #* others])


(defn signin [[retry? False]]
  (html5 {'lang "fr"}
    (html-head
      "Se connecter | Pycolore"
      ["/style.css"
        "https://fonts.googleapis.com/css?family=Fira+Sans:300,400,700%7CPT+Serif:400,700&amp;display=swap"] 
      [])
    ['body
      ['h1 "Service d'auhentification Pycolore"]
      (form-to ['post "/signin"]
        ['h2 (if retry?
          "Veuillez réessayer"
          "Connectez-vous pour continuer")]
        ['div.form-group
          (label 'username "Nom d'utilisateur")
          (text-field 'username)]
        ['div.form-group
          (label 'password "Mot de passe")
          (password-field 'password)]
        (if retry?
          ['p.error "Utilisateur ou mot de passe incorrect."])
        (submit-button "Se connecter"))]))
