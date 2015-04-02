(ns cljs-pikaday.prod
  (:require [cljs-pikaday.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
