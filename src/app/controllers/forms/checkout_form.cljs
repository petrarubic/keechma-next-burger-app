(ns app.controllers.forms.checkout-form
  (:require [keechma.next.controller :as ctrl]
            [keechma.next.controllers.pipelines :as pipelines]
            [keechma.next.controllers.router :as router]
            [keechma.next.controllers.dataloader :as dl]
            [keechma.next.controllers.form :as form]
            [clojure.string :as string]
            [app.validators :as v]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]))

(derive :checkout-form ::pipelines/controller)

(def pipelines
  {:keechma.form/get-data (pipeline! [_ _]
                                     {:name     "Petra Rubic"
                                      :street   "Hamilton Street 34"
                                      :email    "petra.rubic@test.com"
                                      :zipcode  "55678"
                                      :country  "United States"})
   :keechma.form/submit-data
   (pipeline! [value {:keys [meta-state*] :as ctrl}]
              (let [street (:street value)
                    zipcode (:zipcode value)
                    country (:country value)
                    delivery-type (:delivery-type value)]
                (ctrl/broadcast ctrl :order-created {:id (gensym "order") :street street :zipcode zipcode :country country :delivery-type delivery-type}))
              (router/redirect! ctrl :router {:page "orders"}))})

(defmethod ctrl/prep :checkout-form [ctrl]
  (pipelines/register ctrl
                      (form/wrap pipelines (v/to-validator {:name [:not-empty]
                                                            :street [:not-empty]
                                                            :zipcode [:not-empty :valid-zipcode]
                                                            :country [:not-empty :valid-country]
                                                            :email [:email :not-empty]
                                                            :delivery-type [:not-empty]}))))