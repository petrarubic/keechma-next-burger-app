(ns app.controllers.orders
  (:require [keechma.next.controller :as ctrl]
            [keechma.next.controllers.pipelines :as pipelines]
            [keechma.next.controllers.entitydb :as edb]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]))

(derive :orders ::pipelines/controller)

(defn prepend-order!
  [{:keys [deps-state*], :as ctrl} order]
  (let [order-list (edb/get-collection (:entitydb @deps-state*) :order/list)]
    (edb/insert-collection! ctrl :entitydb :order :order/list (conj order-list order))))

(def pipelines
  {:order-created (pipeline! [value ctrl] (prepend-order! ctrl value))})

(defmethod ctrl/prep :orders [ctrl] (pipelines/register ctrl pipelines))

(defmethod ctrl/derive-state :orders
  [_ state {:keys [entitydb]}]
  (edb/get-collection entitydb :order/list))