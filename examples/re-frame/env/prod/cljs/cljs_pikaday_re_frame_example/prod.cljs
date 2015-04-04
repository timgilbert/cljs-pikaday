(ns cljs-pikaday-re-frame-example.prod
  (:require [cljs-pikaday-re-frame-example.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
