(ns app.controllers.checkout-form
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
  {:keechma.form/submit-data
   (pipeline! [value {:keys [meta-state*] :as ctrl}]
              (router/redirect! ctrl :router {:page "home"}))})

(defmethod ctrl/prep :checkout-form [ctrl]
  (pipelines/register ctrl
                      (form/wrap pipelines (v/to-validator {:name [:not-empty]
                                                            :street [:not-empty]
                                                            :zipcode [:not-empty]
                                                            :country [:not-empty]
                                                            :email [:email :not-empty]}))))