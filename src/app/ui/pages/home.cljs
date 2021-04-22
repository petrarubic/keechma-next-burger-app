(ns app.ui.pages.home
  "Example homepage 2 3"
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.core :refer [with-keechma use-sub dispatch]]
            [keechma.next.helix.lib :refer [defnc]]
            [keechma.next.helix.classified :refer [defclassified]]
            [app.ui.components.navbar :refer [Navbar]]
            [helix.hooks :as hooks]))

(defclassified HomepageWrapper :div "h-screen w-screen")

;; burger component build
(defclassified BreadBottom :div "m-auto h-24 w-3/6 rounded-b-3xl mt-1 shadow-inner bg-gradient-to-r from-yellow-700 to-yellow-600")
(defclassified BreadTop :div "m-auto h-1/6 w-3/6 mb-1 rounded-t-full shadow-inner relative bg-gradient-to-r from-yellow-700 to-yellow-600")

(defclassified IngredientsContainer :div "flex flex-col justify-center items-center bg-gray-500 w-screen py-28 text-sm text-gray-100")
(defclassified AddButton :button "bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-4 border border-gray-700 rounded outline-none mr-5 disabled:opacity-50 disabled:cursor-not-allowed")
(defclassified RemoveButton :button "bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-4 border border-gray-700 rounded outline-none disabled:opacity-50 disabled:cursor-not-allowed")
(defclassified ActionLink :a "cursor-pointer bg-gray-600 hover:bg-gray-700 text-white text-lg font-bold py-2 px-8 border border-gray-700 rounded outline-none mt-5 disabled:opacity-50 disabled:cursor-not-allowed")

;; define burger ingredient components
(defnc Salad [{:keys [count price]}]
  (d/div {:class "m-auto h-12 w-3/6 mb-2 mt-2 rounded-full bg-gradient-to-r from-lime-600 to-lime-500"}))

(defnc Bacon [{:keys [count price]}]
  (d/div {:class "m-auto h-5 w-3/6 mb-2 mt-2 bg-gradient-to-r from-red-700 to-red-600"}))

(defnc Cheese [{:keys [count price]}]
  (d/div {:class "m-auto h-8 w-3/6 mb-2 mt-2 rounded-full bg-gradient-to-r from-yellow-500 to-yellow-400"}))

(defnc Meat [{:keys [count price]}]
  (d/div {:class "m-auto h-12 w-3/6 mb-2 mt-2 rounded-lg bg-gradient-to-r from-yellow-900 to-yellow-800"}))

(defnc HomeRenderer [props]
  (let [burger-ingredients (use-sub props :burger-builder)
        current-user-data (use-sub props :current-user)
        all-ingredients (:ingredients burger-ingredients)
        burger-price (:total-price burger-ingredients)
        ;; get count from every ingredient
        salad-count (get-in all-ingredients [0 :count])
        bacon-count (get-in all-ingredients [1 :count])
        cheese-count (get-in all-ingredients [2 :count])
        meat-count (get-in all-ingredients [3 :count])
        ;; get price from every ingredient
        salad-price (* (get-in all-ingredients [0 :price]) salad-count)
        bacon-price (* (get-in all-ingredients [1 :price]) bacon-count)
        cheese-price (* (get-in all-ingredients [2 :price]) cheese-count)
        meat-price (* (get-in all-ingredients [3 :price]) meat-count)
        ingredients-price (+ salad-price bacon-price cheese-price meat-price)
        no-ingredients (and (= 0 bacon-count) (= 0 cheese-count) (= 0 meat-count) (= 0 salad-count))]
    ($ HomepageWrapper
       ($ Navbar)
       (d/div {:class "h-full w-full flex flex-col justify-center"}
              ($ BreadTop)
              (if (= 0 salad-count bacon-count cheese-count meat-count)
                (d/div {:class "text-xl font-semibold text-center mt-5 mb-5"} "Please start adding ingredients!")
                nil)      
              (map (fn [] ($ Salad)) (range 0 salad-count))
              (map (fn [] ($ Bacon)) (range 0 bacon-count))
              (map (fn [] ($ Cheese)) (range 0 cheese-count))
              (map (fn [] ($ Meat)) (range 0 meat-count))
              ($ BreadBottom))
       ($ IngredientsContainer
          (d/div {:class "mb-10 text-xl"} (str "Current price: " (.toFixed (+ ingredients-price burger-price) 2)))
          (map (fn [ingredient]
                 (d/div {:key (:id ingredient) :class "flex justify-between items-center p-2"}
                        (cond 
                          (= "Salad" (:name ingredient)) (d/img {:src "/images/lettuce.png" :className "w-12 h-12 p-2 mr-2"})
                          (= "Bacon" (:name ingredient)) (d/img {:src "/images/bacon.png" :className "w-12 h-12 p-2 mr-2"})
                          (= "Meat" (:name ingredient)) (d/img {:src "/images/meat.png" :className "w-12 h-12 p-2 mr-2"})
                          (= "Cheese" (:name ingredient)) (d/img {:src "/images/cheese.png" :className "w-12 h-12 p-2 mr-2"}))
                        (d/p {:class "block mr-10 text-xl font-bold w-60"} (:name ingredient))
                        ($ AddButton {:on-click #(dispatch props :burger-builder :add-ingredient (:id ingredient))
                                      :disabled (= 5 (:count ingredient))} "+")
                        ($ RemoveButton {:on-click #(dispatch props :burger-builder :remove-ingredient (:id ingredient))
                                         :disabled (= 0 (:count ingredient))} "-"))) all-ingredients)
          (if current-user-data
            ($ ActionLink {:href (router/get-url props :router {:page "checkout"})
                           :disabled no-ingredients} "Order your burger")
            ($ ActionLink {:href (router/get-url props :router {:page "auth"})
                           :disabled no-ingredients} "Sign up to order"))))))

(def Home (with-keechma HomeRenderer))