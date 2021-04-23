(ns app.controllers.current-user
  (:require [keechma.next.controller :as ctrl]
            [keechma.next.controllers.pipelines :as pipelines]
            [keechma.next.controllers.router :as router]
            [keechma.next.controllers.entitydb :as edb]
            [keechma.next.controllers.dataloader :as dl]
            [clojure.string :as string]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]))

(derive :current-user ::pipelines/controller)

(def load-current-user
  (pipeline!  [value {:keys [deps-state* state*] :as ctrl}]
              (let [username (first (string/split (:email value) #"@"))
                    id (gensym username)
                    email (:email value)
                    password (:password value)]
               (edb/insert-named! ctrl :entitydb :user :user/current {:id id :username username :email email :password password}))))

(def pipelines
  {:login load-current-user
   :logout (pipeline! [value ctrl]
                      (.reload js/window.location true))})

(defmethod ctrl/prep :current-user [ctrl] 
  (pipelines/register ctrl pipelines))

(defmethod ctrl/derive-state :current-user
  [_ state {:keys [entitydb]}]
  (edb/get-named entitydb :user/current))