(ns cljs-pikaday.reagent
    (:require [reagent.core :as reagent :refer [atom]]
              [shodan.console :as console :include-macros true]
              [shodan.inspection :refer [inspect]]
              [camel-snake-kebab.core :refer [->camelCaseString]]
              [camel-snake-kebab.extras :refer [transform-keys]]
              [cljsjs.pikaday]))

; cf https://github.com/thomasboyt/react-pikaday/blob/master/src/Pikaday.js

(defn- opts-transform [opts]
  "Given a clojure map of options, return a js object for a pikaday constructor argument."
  (clj->js (transform-keys ->camelCaseString opts)))

(defn date-selector
  ""
  [pikaday-attrs input-attrs]
  (let []
    (reagent/create-class
      {:component-did-mount
        (fn [this]
          (let [dom-node (.getDOMNode this)
                opts (opts-transform (merge {:field dom-node} pikaday-attrs))
                instance (js/Pikaday. opts)]
            (console/warn "opts:" opts)
            (console/warn "mounted:" instance)))
       :display-name "pikaday-component"
       :reagent-render
        (fn [input-attrs]
          (console/log "render")
          [:input input-attrs])})))
