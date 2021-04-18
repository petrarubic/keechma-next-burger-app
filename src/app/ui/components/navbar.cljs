(ns app.ui.components.navbar
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [<> $]]
            [helix.hooks :as hooks]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.core :refer
             [with-keechma dispatch call use-sub]]
            [keechma.next.helix.lib :refer [defnc]]
            [oops.core :refer [ocall oget]]))

(defnc NavbarLink [props]
  (d/a {:className "border-b-4 border-gray-500 hover:border-gray-700 pt-1 transition ease-in-out duration-200"
        :href (:url props)}
       (:text props)))

(defnc NavbarRenderer [props]
  (d/div {:className "bg-gray-500 w-full text-white h-16 py-0 fixed md:relative top-0 z-10 flex"}
         (d/img {:src "/images/burger-logo.png" :className "w-18 h-12 p-2 ml-5 mt-2"})
         (d/div {:className "md:w-1/2 mr-5 mx-auto flex justify-around items-center font-open-sans text-lg"}          
                ($ NavbarLink {:url (router/get-url props :router {:page "home"}) :text "Burger Builder"})
                ($ NavbarLink {:url (router/get-url props :router {:page "auth"}) :text "Authentication"}))))

(def Navbar (with-keechma NavbarRenderer))