(ns cljs-pikaday-reagent-example.core
    (:require [reagent.core :as reagent]
              [reagent.session :as session]
              [shodan.console :as console :include-macros true]
              [secretary.core :as secretary :include-macros true]
              [cljs-pikaday.reagent :as pikaday])
  (:require-macros [reagent.ratom :refer [reaction]]))

;; -------------------------
;; Date stuff

(defn- before [date days]
  "Return a new js/Date which is the given number of days before the given date"
  (js/Date. (.getFullYear date) (.getMonth date) (- (.getDate date) days)))

(defn date? [x]
  (= (type x) js/Date))

(defn days-between [x y]
  "Return the number of days between the two js/Date instances"
  (when (every? date? [x y])
    (let [ms-per-day (* 1000 60 60 25)
          x-ms (.getTime x)
          y-ms (.getTime y)]
      (.round js/Math (.abs js/Math (/ (- x-ms y-ms) ms-per-day))))))

(def today (js/Date.))
(def yesterday (before today 1))
(def last-week (before today 7))
(def last-week-yesterday (before today 8))

;; -------------------------
;; App state

(defonce start-date (reagent/atom last-week-yesterday))

(defonce end-date (reagent/atom yesterday))

(def total-days-selected (reaction (days-between @start-date @end-date)))

;; -------------------------
;; Views

(defn set-date! [which date]
  "Set a date. which is either :start or :end."
  (reset! (which {:start start-date :end end-date}) date))

(defn get-date! [which]
  (let [js-date @(which {:start start-date :end end-date})]
    (if (date? js-date)
      (.toLocaleDateString js-date "en" "%d-%b-%Y")
      "unselected")))

(defn home-page []
  [:div [:h2 "Select a start and end date"]
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
      [:p "Days selected: " @total-days-selected]
      [:p [:button {:on-click #(set-date! :start today)} "Start today"]
      [:p [:button {:on-click #(set-date! :start last-week)} "Start last week"]
      [:p [:button {:on-click #(do (set-date! :start nil) (set-date! :end nil))} "Unset both"]]]]]
    [:div [:p "This is a demonstration page for the "
              [:a {:href "https://github.com/timgilbert/cljs-pikaday"} "cljs-pikaday"] " library."]]])

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
