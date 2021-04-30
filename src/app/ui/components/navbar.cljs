(ns app.ui.components.navbar
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [<> $]]
            [helix.hooks :as hooks]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.core :refer
             [with-keechma dispatch call use-sub]]
            [keechma.next.helix.classified :refer [defclassified]]
            [keechma.next.helix.lib :refer [defnc]]
            [oops.core :refer [ocall oget]]))

(defnc NavbarLink [props]
  (d/a {:className "border-b-4 border-gray-500 mr-5 hover:border-gray-700 pt-1 transition ease-in-out duration-200"
        :href (:url props)}
       (:text props)))

(defclassified NavbarButton :button "focus:outline-none border-b-4 border-gray-500 mr-5 hover:border-gray-700 pt-1 transition ease-in-out duration-200")

(defnc NavbarRenderer [props]
  (let [current-user-data (use-sub props :current-user)]
    (d/div {:className "font-poppins bg-gray-500 w-full text-white h-16 py-0 fixed md:relative top-0 z-10 flex"}
           (d/img {:src "/images/burger-logo.png" :className "w-18 h-12 p-2 ml-5 mt-2"})
           (d/div {:className "md:w-1/2 mx-auto flex flex-row-reverse items-center font-open-sans text-lg"}
                  (if current-user-data
                    (d/div
                     ($ NavbarButton {:on-click #(router/redirect! props :router {:page "orders"})} "Orders")
                     ($ NavbarButton {:on-click #(dispatch props :current-user :logout)} "Log out"))
                    ($ NavbarLink {:url (router/get-url props :router {:page "auth"}) :text "Authentication"}))
                  ($ NavbarLink {:url (router/get-url props :router {:page "home"}) :text "Burger Builder"})))))

(def Navbar (with-keechma NavbarRenderer))