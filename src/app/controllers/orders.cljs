(ns app.controllers.orders
  (:require [keechma.next.controller :as ctrl]
            [keechma.next.controllers.pipelines :as pipelines]
            [keechma.next.controllers.entitydb :as edb]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]))

(derive :orders ::pipelines/controller)

(def load-orders
  (pipeline!  [value {:keys [deps-state* state*] :as ctrl}]))

(def pipelines
  {:keechma.on/start load-orders
   :keechma.on/stop  (pipeline! [_ ctrl])})

(defmethod ctrl/prep :orders [ctrl] (pipelines/register ctrl pipelines))

(defmethod ctrl/derive-state :orders
  [_ state {:keys [entitydb]}]
  (edb/get-collection entitydb :order/orders-list))