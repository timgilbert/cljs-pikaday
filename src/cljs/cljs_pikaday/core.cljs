(ns cljs-pikaday.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [shodan.console :as console :include-macros true]
              [secretary.core :as secretary :include-macros true]
              [cljs-pikaday.reagent :as pikaday])
  (:require-macros [reagent.ratom :refer [reaction]]))

;; -------------------------
;; App state
(def state (atom {:date nil}))

;; -------------------------
;; Views

(defn set-date! [date]
  (console/log "Resetting date to" date)
  (swap! state assoc-in [:date] date))

(defn get-date! []
  (let [js-date (:date @state)]
    (console/log "js-date" js-date)
    (if (= (type js-date) js/Date)
      (.toLocaleDateString js-date "en" "%d-%b-%Y")
      "unselected")))

(defn last-week []
  (let [today (js/Date.)]
    (js/Date. (.getFullYear today) (.getMonth today) (- (.getDate today) 7))))

(defn onselect [& args]
  (set-date! (first args)))

(defn home-page []
  [:div [:h2 "Welcome to cljs-pikaday"]
    [:div 
      [pikaday/selector {:onSelect set-date!}]]
    [:div
      [:p "Your selected date: " (get-date!) "."]
      [:p [:button {:on-click #(set-date! (js/Date.))} "Today"]
      [:p [:button {:on-click #(set-date! (last-week))} "Last week"]
      [:p [:button {:on-click #(set-date! nil)} "Unset"]]]]]])

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
