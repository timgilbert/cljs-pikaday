(ns cljs-pikaday.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [shodan.console :as console :include-macros true]
              [secretary.core :as secretary :include-macros true]
              [cljs-pikaday.reagent :as pikaday])
  (:require-macros [reagent.ratom :refer [reaction]]))

;; -------------------------
;; Date stuff

(defn- before [date days]
  (js/Date. (.getFullYear date) (.getMonth date) (- (.getDate date) days)))

(def today (js/Date.))
(def yesterday (before today 1))
(def last-week (before today 7))
(def last-week-yesterday (before today 8))

;; -------------------------
;; App state

(defonce start-date (atom last-week-yesterday))

(defonce end-date (atom yesterday))

;; -------------------------
;; Views

(defn set-date! [which date]
  "Set a date. which is either :start or :end."
  (reset! (which {:start start-date :end end-date}) date))

(defn get-date! [which]
  (let [js-date @(which {:start start-date :end end-date})]
    (if (= (type js-date) js/Date)
      (.toLocaleDateString js-date "en" "%d-%b-%Y")
      "unselected")))

(defn home-page []
  [:div [:h2 "Welcome to cljs-pikaday"]
    [:div 
      [:label {:for "start"} "Start date: "]
      [pikaday/date-selector 
        {:date-atom start-date
         :max-date-atom end-date
         :pikaday-attrs {:max-date today}
         :input-attrs {:id "start"}}]]
    [:div 
      [:label {:for "end"} "End date: "]
      [pikaday/date-selector 
        {:date-atom end-date
         :min-date-atom start-date
         :pikaday-attrs {:max-date today}
         :input-attrs {:id "end"}}]]
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
