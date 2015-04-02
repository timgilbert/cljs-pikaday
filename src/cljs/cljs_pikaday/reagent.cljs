(ns cljs-pikaday.reagent
    (:require [reagent.core :as reagent :refer [atom]]
              [shodan.console :as console :include-macros true]
              [shodan.inspection :refer [inspect]]
              [cljsjs.pikaday]))

; cf https://github.com/thomasboyt/react-pikaday/blob/master/src/Pikaday.js

(defn selector-form-1 [{:keys [atom path]}]
  (let [pikaday-opts (clj->js {:abc 123})
        pik (js/Pikaday. pikaday-opts)]
    ;(inspect pik)
    [:span
      [:input {:type "text"}]
      ;(.-el pik)
      ]))

(defn- did-mount-handler 
  ""
  [this]
  (let [dom-node (.getDOMNode this)
        pikaday-opts {:field dom-node}
        pik-instance (js/Pikaday. (clj->js pikaday-opts))]
    (console/warn "mounted:" dom-node)))


(defn selector-form-3
  ""
  [pikaday-attrs]
  (let []
    (reagent/create-class
      {:component-did-mount
        (fn [this]
          (let [dom-node (.getDOMNode this)
                opts (merge {:field dom-node} pikaday-attrs)
                instance (js/Pikaday. (clj->js opts))]
            (console/warn "mounted:" instance)))
       :display-name "pikaday-component"
       :reagent-render
        (fn [input-attrs options]
          (console/log "render")
          [:input input-attrs])})))


(def selector selector-form-3)
