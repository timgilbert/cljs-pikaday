(ns cljs-pikaday.reagent
    (:require [reagent.core :as reagent :refer [atom]]
              [shodan.console :as console :include-macros true]
              [shodan.inspection :refer [inspect]]
              [cljsjs.pikaday]))

(defn selector-form-1 [attrs selected-date]
  (let [pik (js/Pikaday.)]
    [:input attrs]))
