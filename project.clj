(defproject cljs-pikaday "0.1.0"
  :description "ClojureScript interface to the Pikaday JS date picker"
  :url "https://github.com/timgilbert/cljs-pikaday"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :source-paths ["src"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reagent "0.5.0"]
                 [org.clojure/clojurescript "0.0-3169" :scope "provided"]
                 [cljsjs/pikaday "1.3.2-0"]
                 [camel-snake-kebab "0.3.1" :exclusions [org.clojure/clojure]]])
