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

(defn- watch [ratom predicate func])

(defn- component-did-mount
  "Helper for creating component-did-mount functions"
  [{:keys [date-atom max-date-atom min-date-atom pikaday-attrs] :as args} dom-node]
  (let [default-opts
        {:field dom-node
         :default-date @date-atom
         :set-default-date true
         :on-select #(when date-atom (reset! date-atom %))}
        opts (opts-transform (merge default-opts pikaday-attrs))
        instance (js/Pikaday. opts)]
    ; This code could probably be neater
    (console/log "invoke" (clj->js opts))
    (when date-atom
      (add-watch date-atom :update-instance
        (fn [key ref old new]
          ; final parameter here causes pikaday to skip onSelect() callback
          (.setDate instance new true))))
    (when min-date-atom
      (add-watch min-date-atom :update-min-date
        (fn [key ref old new]
          (.setMinDate instance new)
          ; If new max date is less than selected date, reset actual date to max
          (if (< @date-atom new)
            (reset! date-atom new)))))
    (when max-date-atom
      (add-watch max-date-atom :update-max-date 
        (fn [key ref old new]
          (.setMaxDate instance new)
          ; If new max date is less than selected date, reset actual date to max
          (if (> @date-atom new)
            (reset! date-atom new)))))))

(defn date-selector
  "Return a date-selector reagent component. Takes a single map as its 
  argument, with the following keys:
  date-atom: an atom or reaction bound to the date value represented by the picker.
  max-date-atom: atom representing the maximum date for the selector.
  min-date-atom: atom representing the minimum date for the selector.
  pikaday-attrs: a map of options to be passed to the Pikaday constructor.
  input-attrs: a map of options to be used as <input> tag attributes."
  [{:keys [input-attrs] :as opts}]
  (reagent/create-class
    {:component-did-mount #(component-did-mount opts (.getDOMNode %))
     :display-name "pikaday-component"
     :reagent-render (fn [] [:input input-attrs])}))
