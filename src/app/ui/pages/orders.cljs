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
            (d/div {:class "flex flex-col"}
                   (d/h1 {:class "text-2xl mt-4 ml-12 mb-8 font-bold"} "Order History")
                   (map
                    (fn [order]
                      (d/div {:key (:id order)
                              :class "w-full flex"}
                             (d/div {:class "bg-white w-3/4 rounded-b shadow-lg p-4 mx-20 mt-10"}
                                    (d/div {:class "mb-8"}
                                           (d/p {:class "text-yellow-600 font-bold text-xl mb-4"} (str (gensym "Order")))
                                           (d/div {:class "flex flex-row"}
                                                  (d/div
                                                   (d/p {:class "underline text-gray-900 text-base mb-2"} "Contact")
                                                   (d/p {:class "text-sm mb-2"}
                                                        (d/span {:class "font-bold"} "Street: ")
                                                        (d/span {:class "text-gray-500"} (:street order)))
                                                   (d/p {:class "text-sm mb-2"}
                                                        (d/span {:class "font-bold"} "Zipcode: ")
                                                        (d/span {:class "text-gray-500"} (:zipcode order)))
                                                   (d/p {:class "text-sm mb-2"}
                                                        (d/span {:class "font-bold"} "Country: ")
                                                        (d/span {:class "text-gray-500"} (:country order)))
                                                   (d/p {:class "text-sm mb-2"}
                                                        (d/span {:class "font-bold"} "Delivery type: ")
                                                        (d/span {:class "text-gray-500"} (:delivery-type order)))
                                                   (d/p {:class "text-sm mb-2"}
                                                        (d/span {:class "font-bold"} "Price: ")
                                                        (d/span {:class "text-gray-500"} "$" (:total-price burger-builder))))                                           
                                                  (d/div {:class "ml-20"}
                                                         (d/p {:class "underline text-gray-900 text-base mb-2"} "Ingredients")
                                                         (map
                                                          (fn [burger]
                                                            (map
                                                             (fn [ingredient]
                                                               (d/div {:key (:id ingredient) :class "flex justify-between items-center p-2"}
                                                                      (cond
                                                                        (= "Salad" (:name ingredient)) (d/img {:src "/images/lettuce.png" :className "w-5 h-5"})
                                                                        (= "Bacon" (:name ingredient)) (d/img {:src "/images/bacon.png" :className "w-5 h-5"})
                                                                        (= "Meat" (:name ingredient)) (d/img {:src "/images/meat.png" :className "w-5 h-5"})
                                                                        (= "Cheese" (:name ingredient)) (d/img {:src "/images/cheese.png" :className "w-5 h-5"}))
                                                                      (d/p {:class "text-sm block font-bold ml-4"} (:name ingredient))
                                                                      (d/p {:class "text-sm block text-gray-500 ml-2"} (:count ingredient))))
                                                             (get burger :ingredients)))
                                                          burgers)))))))
                    orders))))))

(def Orders (with-keechma OrdersRenderer))