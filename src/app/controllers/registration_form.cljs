(ns app.controllers.registration-form
  (:require [keechma.next.controller :as ctrl]
            [keechma.next.controllers.pipelines :as pipelines]
            [keechma.next.controllers.router :as router]
            [keechma.next.controllers.dataloader :as dl]
            [keechma.next.controllers.form :as form]
            [clojure.string :as string]
            [app.validators :as v]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]))

(derive :registration-form ::pipelines/controller)

(def pipelines
  {:keechma.form/submit-data
   (pipeline! [value {:keys [meta-state*] :as ctrl}]
              (let [email (:email value)
                    password (:password value)]
                (ctrl/broadcast ctrl :login {:email email :password password}))
              (router/redirect! ctrl :router {:page "home"}))})

(defmethod ctrl/prep :registration-form [ctrl]
  (pipelines/register ctrl
                      (form/wrap pipelines (v/to-validator {:email [:email :not-empty]
                                                            :password [:not-empty :ok-password]}))))