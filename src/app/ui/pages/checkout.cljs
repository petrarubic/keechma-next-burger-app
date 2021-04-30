(ns app.ui.pages.checkout
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.core :refer [with-keechma use-sub dispatch]]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.lib :refer [defnc]]
            [helix.hooks :as hooks]
            [keechma.next.helix.classified :refer [defclassified]]
            [app.ui.components.inputs :refer [wrapped-input]]
            [app.ui.components.navbar :refer [Navbar]]))

(defclassified CheckoutWrapper :div "h-screen w-screen font-poppins")
(defclassified SubmitButton :button "focus:outline-none hover:text-lime-500 cursor-pointer w-full flex items-center justify-center mt-4 px-8 py-3 border border-transparent text-2xl font-bold rounded-md text-lime-600 md:py-4 md:px-10")

(defnc CheckoutRenderer [props]
  ($ CheckoutWrapper
     ($ Navbar)
     (d/div {:class "h-full w-full flex justify-center bg-gray-100 overflow-y-scroll "}
            (d/div {:class "flex-col h-4/5 w-2/3 rounded-lg shadow-lg my-20 bg-white"}
                   (d/div {:class "flex-1 p-6 mt-6 items-center mx-40 my-6"}
                          (d/p )
                          (d/form {:on-submit (fn [e] (.preventDefault e) (dispatch props :checkout-form :keechma.form/submit))}
                                  (d/p {:class "font-bold pb-2 pt-2"} "Name")
                                  (wrapped-input {:keechma.form/controller :checkout-form
                                                  :input/type :text
                                                  :input/attr :name})
                                  (d/p {:class "font-bold pb-2 pt-2"} "Street")
                                  (wrapped-input {:keechma.form/controller :checkout-form
                                                  :input/type :text
                                                  :input/attr :street})
                                  (d/p {:class "font-bold pb-2 pt-2"} "Zip Code")
                                  (wrapped-input {:keechma.form/controller :checkout-form
                                                  :input/type :text
                                                  :input/attr :zipcode})
                                  (d/p {:class "font-bold pb-2 pt-2"} "Country")
                                  (wrapped-input {:keechma.form/controller :checkout-form
                                                  :input/type :text
                                                  :input/attr :country})
                                  (d/p {:class "font-bold pb-2 pt-2"} "Email")
                                  (wrapped-input {:keechma.form/controller :checkout-form
                                                  :input/type :text
                                                  :input/attr :email})
                                  (d/p {:class "font-bold pb-2 pt-2"} "Delivery Type")
                                  (wrapped-input {:keechma.form/controller :checkout-form
                                                  :input/type :select
                                                  :input/attr :delivery-type
                                                  :options [{:value "default" :label "---Select delivery type---"}
                                                            {:value "fastest" :label "Fastest"}
                                                            {:value "cheapest" :label "Cheapest"}]})
                                  ($ SubmitButton "ORDER")))))))

(def Checkout (with-keechma CheckoutRenderer))