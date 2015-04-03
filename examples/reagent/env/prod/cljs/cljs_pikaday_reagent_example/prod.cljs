(ns cljs-pikaday-reagent-example.prod
  (:require [cljs-pikaday-reagent-example.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
