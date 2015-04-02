(ns cljs-pikaday.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [shodan.console :as console :include-macros true]
              [secretary.core :as secretary :include-macros true]
              [cljs-pikaday.reagent :as pikaday])
  (:require-macros [reagent.ratom :refer [reaction]]))

;; -------------------------
;; App state

(defonce today (js/Date.))
(defonce last-week 
  (js/Date. (.getFullYear today) (.getMonth today) (- (.getDate today) 7)))

(defonce state (atom {:dates {:start last-week :end today}}))

;; -------------------------
;; Views

(defn set-date! [which date]
  (console/log "Resetting date" (str which) "to" date)
  (swap! state assoc-in [:date which] date))

(defn get-date! [which]
  (let [js-date (get-in @state [:date which])]
    (console/log "js-date" js-date)
    (if (= (type js-date) js/Date)
      (.toLocaleDateString js-date "en" "%d-%b-%Y")
      "unselected")))

(defn home-page []
  [:div [:h2 "Welcome to cljs-pikaday"]
    [:div 
      [:label {:for "start"} "Start date: "]
      [pikaday/date-selector 
        {:on-select #(set-date! :start %)
         :max-date (js/Date.)}
        {:id "start"}]]
    [:div 
      [:label {:for "end"} "End date: "]
      [pikaday/date-selector 
        {:on-select #(set-date! :end %)
         :max-date (js/Date.)}
        {:id "end"}]]
    [:div
      [:p "Your selected range: " (get-date! :start) " to " (get-date! :end)]
      [:p [:button {:on-click #(set-date! :start today)} "Start today"]
      [:p [:button {:on-click #(set-date! :start last-week)} "Start last week"]
      [:p [:button {:on-click #(set-date! :start nil)} "Unset"]]]]]])

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
