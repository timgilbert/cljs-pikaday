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
  [{:keys [date-atom max-date-atom pikaday-attrs input-attrs]}]
  (let []
    (reagent/create-class
      {:component-did-mount
        (fn [this]
          (let [default-opts
                {:field (.getDOMNode this)
                 :default-date @date-atom
                 :set-default-date true
                 :on-select #(reset! date-atom %)}
                opts (opts-transform (merge default-opts pikaday-attrs))
                instance (js/Pikaday. opts)]
            (when date-atom
              (add-watch date-atom :update-instance
                (fn [key ref old new]
                  (console/log ":update-instance" old new @date-atom)
                  (console/log "=" (= new @date-atom))
                  (when (not= @date-atom new)
                    (.setDate instance new)))))
            (when max-date-atom
              (add-watch max-date-atom :update-max-date 
                (fn [key ref old new]
                  ;(console/log "watch max" key ref old new)
                  (.setMaxDate instance new)
                  ; If new max date is less than selected date, reset actual date to max
                  (if (> @date-atom new)
                    (reset! date-atom new)
                    (.setDate instance new)))))
            (console/warn "opts:" opts)
            (console/warn "mounted:" instance)))
       :display-name "pikaday-component"
       :reagent-render
        (fn [input-attrs]
          ;(console/log "render")
          [:input input-attrs])})))
