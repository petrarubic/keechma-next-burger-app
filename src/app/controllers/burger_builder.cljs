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
   :burgers []
   :total-price 0.00})

(defn add-ingredient [{:keys [ingredients] :as state} id]
  (let [ingredients' (mapv (fn [ingredient]
                       (if (= id (:id ingredient))
                         (assoc ingredient :count (inc (:count ingredient)))
                         ingredient))
                     ingredients)]
    (assoc state :ingredients ingredients')))

(defn calculate-total-price [{:keys [ingredients total-price] :as state}]
  (let [salad-count (get-in ingredients [0 :count])
        bacon-count (get-in ingredients [1 :count])
        cheese-count (get-in ingredients [2 :count])
        meat-count (get-in ingredients [3 :count])
        salad-price (* (get-in ingredients [0 :price]) salad-count)
        bacon-price (* (get-in ingredients [1 :price]) bacon-count)
        cheese-price (* (get-in ingredients [2 :price]) cheese-count)
        meat-price (* (get-in ingredients [3 :price]) meat-count)
        total-price' (+ 4 salad-price bacon-price cheese-price meat-price)]
    (assoc state :total-price total-price')))

(defn remove-ingredient [{:keys [ingredients] :as state} id]
  (let [ingredients' (mapv (fn [ingredient]
                             (if (= id (:id ingredient))
                               (assoc ingredient :count (dec (:count ingredient)))
                               ingredient))
                           ingredients)]
    (assoc state :ingredients ingredients')))

(defn prepend-burger [{:keys [ingredients burgers total-price] :as state}]
  (let [burgers' (conj burgers {:id (gensym "burger")
                                :ingredients ingredients})]
    (assoc state :burgers burgers')))

(defmethod ctrl/handle :burger-builder [{:keys [state*] :as ctrl} ev payload]
  (case ev
    :add-ingredient (swap! state* add-ingredient payload)
    :remove-ingredient (swap! state* remove-ingredient payload)
    :calculate-total-price (swap! state* calculate-total-price payload)
    :burger-created (swap! state* prepend-burger payload)
    nil))