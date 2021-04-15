(ns app.controllers.burger-builder
  (:require [keechma.next.controller :as ctrl]))

(derive :burger-builder :keechma/controller)

;; define a map of burger ingredients
(def burger-ingredients
  [{:id 1 :name "Salad" :count 0 :price 0.50}
   {:id 2 :name "Bacon" :count 0 :price 0.70}
   {:id 3 :name "Cheese" :count 0 :price 0.40}
   {:id 4 :name "Meat" :count 0 :price 1.30}])

(defmethod ctrl/start :burger-builder [ctrl params prev]
  {:ingredients burger-ingredients
   :total-price 4.00})

(defn add-ingredient [{:keys [ingredients] :as state} id]
  (let [ingredients' (mapv (fn [ingredient]
                       (if (= id (:id ingredient))
                         (assoc ingredient :count (inc (:count ingredient)))
                         ingredient))
                     ingredients)]
    (assoc state :ingredients ingredients')))

(defn remove-ingredient [{:keys [ingredients] :as state} id]
  (let [ingredients' (mapv (fn [ingredient]
                             (if (= id (:id ingredient))
                               (assoc ingredient :count (dec (:count ingredient)))
                               ingredient))
                           ingredients)]
    (assoc state :ingredients ingredients')))

(defmethod ctrl/handle :burger-builder [{:keys [state*] :as ctrl} ev payload]
  (case ev
    :add-ingredient (swap! state* add-ingredient payload)
    :remove-ingredient (swap! state* remove-ingredient payload)
    nil))