(defproject cljs-pikaday "0.1.3"
  :description "ClojureScript interface to the Pikaday JS date picker"
  :url "https://github.com/timgilbert/cljs-pikaday"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :source-paths ["src"]

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [reagent "0.6.0-rc"]
                 [org.clojure/clojurescript "1.9.93" :scope "provided"]
                 [cljsjs/pikaday "1.4.0-1"]
                 [camel-snake-kebab "0.4.0" :exclusions [org.clojure/clojure]]])
