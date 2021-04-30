(ns app.ui.pages.orders
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.core :refer [with-keechma use-sub dispatch]]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.lib :refer [defnc]]
            [helix.hooks :as hooks]
            [keechma.next.helix.classified :refer [defclassified]]
            [app.ui.components.navbar :refer [Navbar]]))

(defclassified OrdersWrapper :div "h-screen w-screen font-poppins")

(defnc OrdersRenderer [props]
  (let [orders (use-sub props :orders)
        burger-builder (use-sub props :burger-builder)
        burgers (:burgers burger-builder)]
  ($ OrdersWrapper
     ($ Navbar)
     (d/div {:class "h-full w-full bg-gray-100 overflow-y-scroll"}
            (d/h1 {:class "text-2xl mt-8 ml-12 mb-8 font-bold"} "Order History")
            (d/div {:class "flex flex-row bg-white rounded-b shadow-lg mx-20"}      
                   (d/div
                    (map
                     (fn [order]
                       (d/div {:key (:id order)
                               :class "flex"}
                              (d/div {:class "p-4 ml-20 pr-52 mt-10 mb-24"}
                                     (d/div
                                            (d/p {:class "text-yellow-600 font-bold text-xl mb-4"} (str (gensym "Order")))
                                            (d/div
                                             (d/div
                                              (d/p {:class "underline text-gray-900 text-base mb-3"} "Contact")
                                              (d/p {:class "text-sm mb-2"}
                                                   (d/span {:class "font-bold"} "Street: ")
                                                   (d/span {:class "text-gray-500"} (:street order)))
                                              (d/p {:class "text-sm mb-2"}
                                                   (d/span {:class "font-bold"} "Zipcode: ")
                                                   (d/span {:class "text-gray-500"} (:zipcode order)))
                                              (d/p {:class "text-sm mb-2"}
                                                   (d/span {:class "font-bold"} "Country: ")
                                                   (d/span {:class "text-gray-500"} (:country order))))))))) orders))
                   (d/div
                    (map
                     (fn [burger]
                       (d/div {:class "p-4 ml-10 pr-52 mt-10 mb-18"}
                              (d/p {:class "text-yellow-600 font-bold text-xl mb-4"} (str (gensym "Burger")))
                              (d/p {:class "underline text-gray-900 text-base mb-2"} "Ingredients")
                              (map
                               (fn [ingredient]
                                 (d/div {:key (:id ingredient) :class "flex justify-between items-center p-2"}
                                        (cond
                                          (= "Salad" (:name ingredient)) (d/img {:src "/images/lettuce.png" :className "w-7 h-7"})
                                          (= "Bacon" (:name ingredient)) (d/img {:src "/images/bacon.png" :className "w-7 h-7"})
                                          (= "Meat" (:name ingredient)) (d/img {:src "/images/meat.png" :className "w-7 h-7"})
                                          (= "Cheese" (:name ingredient)) (d/img {:src "/images/cheese.png" :className "w-7 h-7"}))
                                        (d/p {:class "text-sm block font-bold ml-4"} (:name ingredient))
                                        (d/p {:class "text-sm block text-gray-500 ml-2"} (:count ingredient))))
                               (get burger :ingredients)))) burgers))
                   (d/div
                    (map
                     (fn [order]
                       (d/div
                        (d/p {:class "mt-14 ml-10 text-xl font-bold mb-2"}
                             (d/span {:class "text-yellow-600 mb-4"} "Total price: ")
                             (d/span (str "$" (.toFixed (:total-price burger-builder) 2))))
                        (d/div {:class "flex justify-between ml-10 text-xl font-bold mb-60"}
                             (d/p {:class "text-yellow-600 mb-4 mr-2"} "Delivery type: ")
                             (if (= (:delivery-type order) "fastest")
                               (d/img {:src "/images/fast.png" :className "w-10 h-10"})
                               (d/img {:src "/images/low-prices.png" :className "w-10 h-10"})))))
                     orders)))))))

(def Orders (with-keechma OrdersRenderer))