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

(defclassified AuthWrapper :div "h-screen w-screen font-poppins")
(defclassified SubmitButton :button "focus:outline-none hover:text-lime-500 cursor-pointer w-full flex items-center justify-center mt-4 px-8 border border-transparent text-2xl font-bold rounded-md text-lime-600 md:px-10")

(defnc AuthRenderer [props]
  ($ AuthWrapper
       ($ Navbar)
       (d/div {:class "h-full w-full flex justify-center bg-gray-100 overflow-y-scroll"}
              (d/div {:class "flex-col h-1/3 w-2/3 rounded-lg shadow-lg my-20 bg-white"}
                     (d/div {:class "flex-1 p-6 mt-6 items-center mx-40 my-6"}
                            (d/form {:on-submit (fn [e] (.preventDefault e) (dispatch props :login-form :keechma.form/submit))}
                                    (d/p {:class "font-bold pb-2 pt-2"} "Email")
                                    (wrapped-input {:keechma.form/controller :login-form
                                                    :input/type :text
                                                    :input/attr :email})
                                    (d/p {:class "font-bold pb-2 pt-2"} "Password")
                                    (wrapped-input {:keechma.form/controller :login-form
                                                    :input/type :text
                                                    :input/attr [:password]
                                                    :type "password"})
                                    ($ SubmitButton "SUBMIT")))))))

(def Auth (with-keechma AuthRenderer))