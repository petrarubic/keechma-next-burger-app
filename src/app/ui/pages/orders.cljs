(ns app.ui.pages.orders
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.core :refer [with-keechma use-sub dispatch]]
            [keechma.next.controllers.router :as router]
            [keechma.next.helix.lib :refer [defnc]]
            [helix.hooks :as hooks]
            [keechma.next.helix.classified :refer [defclassified]]
            [app.ui.components.navbar :refer [Navbar]]))

(defclassified OrdersWrapper :div "h-screen w-screen")

(defnc OrdersRenderer [props]
  (let [burger-builder (use-sub props :burger-builder)
        _ (println burger-builder)])
  ($ OrdersWrapper
     ($ Navbar)
     (d/div {:class "h-full w-full flex justify-center bg-gray-100"}
            "Orders page")))

(def Orders (with-keechma OrdersRenderer))