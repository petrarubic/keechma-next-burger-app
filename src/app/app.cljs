(ns app.app
  (:require [keechma.next.controllers.router]
            [keechma.next.controllers.dataloader]
            [keechma.next.controllers.subscription]

            [app.controllers.burger-builder]
            [app.controllers.login-form]
            [app.controllers.checkout-form]
            [app.controllers.registration-form]
            [app.controllers.current-user]

            ["react-dom" :as rdom]))

(defn page-eq? [page] (fn [{:keys [router]}] (= page (:page router))))

(defn role-eq? [role] (fn [deps] (= role (:role deps))))

(def homepage? (page-eq? "home"))

(defn slug [{:keys [router]}] (:slug router))

(def app
  {:keechma.subscriptions/batcher rdom/unstable_batchedUpdates
   :keechma/controllers {:router {:keechma.controller/params true
                                  :keechma.controller/type :keechma/router
                                  :keechma/routes [["" {:page "home"}] ":page" ":page/:subpage"]}
                         :dataloader {:keechma.controller/params true
                                      :keechma.controller/type :keechma/dataloader}
                         :entitydb    {:keechma.controller/params true
                                       :keechma.controller/type :keechma/entitydb}
                         :burger-builder        #:keechma.controller {:deps   [:router]
                                                                      :params (page-eq? "home")}
                         :current-user          #:keechma.controller {:deps [:router :entitydb]
                                                                      :params true}
                         :login-form            #:keechma.controller {:type :login-form
                                                                      :deps [:router]
                                                                      :params (page-eq? "auth")}
                         :checkout-form         #:keechma.controller {:type :checkout-form
                                                                      :deps [:router]
                                                                      :params (page-eq? "checkout")}
                         :registration-form     #:keechma.controller {:type :registration-form
                                                                      :deps [:router]
                                                                      :params (page-eq? "auth")}}})