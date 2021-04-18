(ns app.ui.pages.auth
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.core :refer [with-keechma use-sub dispatch]]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.lib :refer [defnc]]
            [helix.hooks :as hooks]
            [keechma.next.helix.classified :refer [defclassified]]
            [app.ui.components.inputs :refer [wrapped-input]]
            [app.ui.components.navbar :refer [Navbar]]))

(defclassified AuthWrapper :div "h-screen w-screen")
(defclassified SubmitButton :button "focus:outline-none cursor-pointer w-full flex items-center justify-center mt-4 px-8 py-3 border border-transparent text-base font-medium rounded-md text-green-600 md:py-4 md:text-lg md:px-10")

(defnc AuthRenderer [props]
  ($ AuthWrapper
       ($ Navbar)
       (d/div {:class "h-full w-full flex justify-center"}
              (d/div {:class "flex-col h-2/4 w-2/3 rounded-lg shadow-lg overflow-hidden my-20"}
                     (d/div {:class "flex-1 p-6 mt-6 items-center mx-40 my-6"}
                            (d/form {:on-submit (fn [e] (.preventDefault e) (dispatch props :login-form :keechma.form/submit))}
                                    (wrapped-input {:keechma.form/controller :login-form
                                                    :input/type :text
                                                    :input/attr :email
                                                    :placeholder "Email"})
                                    (wrapped-input {:keechma.form/controller :login-form
                                                    :input/type :text
                                                    :input/attr [:password]
                                                    :type "password"
                                                    :placeholder "Password"})
                                    ($ SubmitButton "Submit")))))))

(def Auth (with-keechma AuthRenderer))