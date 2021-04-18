(ns app.ui.main
  (:require [keechma.next.helix.core :refer [with-keechma use-sub]]
            [keechma.next.helix.lib :refer [defnc]]
            [helix.core :as hx :refer [$]]
            [helix.dom :as d]
            
            [app.ui.pages.home :refer [Home]]
            [app.ui.pages.auth :refer [Auth]]))

(defnc MainRenderer [props]
  (let [{:keys [page]} (use-sub props :router)]
    (case page
      "home" ($ Home)
      "auth" ($ Auth)
      (d/div "404"))))

(def Main (with-keechma MainRenderer))