(defproject cljs-pikaday-reagent-example "0.2.0-SNAPSHOT"
  :description "reagent example project for cljs-pikaday"
  :url "https://github.com/timgilbert/cljs-pikaday"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :source-paths ["src/clj" "../../src/cljs" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring-server "0.4.0"]
                 [cljsjs/react "15.2.1-1"]
                 [reagent "0.6.0-rc"]
                 [reagent-forms "0.5.24"]
                 [reagent-utils "0.1.9"]
                 [org.clojure/clojurescript "1.9.93" :scope "provided"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [prone "1.1.1"]
                 [compojure "1.5.1"]
                 [selmer "1.0.7"]
                 [environ "1.0.3"]
                 [secretary "1.2.3"]
                 [cljsjs/pikaday "1.4.0-1"]
                 [camel-snake-kebab "0.4.0" :exclusions [org.clojure/clojure]]
                 [shodan "0.4.2"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-environ "1.0.3"]
            [lein-ring "0.9.7"]
            [lein-asset-minifier "0.3.0"]]

  :ring {:handler cljs-pikaday-reagent-example.handler/app
         :uberwar-name "cljs-pikaday-reagent-example.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "cljs-pikaday-reagent-example.jar"

  :main cljs-pikaday-reagent-example.server

  :clean-targets ^{:protect false} ["resources/public/js"]

  :minify-assets
  {:assets
    {"resources/public/css/site.min.css" ["resources/public/css/site.css"
                                          "resources/public/css/pikaday.css"]}}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs" "../../src"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :asset-path    "js/out"
                                        :optimizations :none
                                        :pretty-print  true}}
                        :prod {:source-paths ["env/prod/cljs" "../../src"]
                               :compiler {:output-to     "resources/public/js/app.js"
                                          :optimizations :advanced
                                          :pretty-print  false}}}}

  :profiles {:dev {:repl-options {:init-ns cljs-pikaday-reagent-example.repl
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[ring/ring-mock "0.3.0"]
                                  [ring/ring-devel "1.5.0"]
                                  [leiningen "2.6.1"]
                                  [figwheel "0.5.4-7"]
                                  [weasel "0.7.0" :exclusions [org.clojure/clojurescript]]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [pjstadig/humane-test-output "0.8.0"]]

                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.5.4-7"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler cljs-pikaday-reagent-example.handler/app}

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:main "cljs-pikaday-reagent-example.dev"
                                                         :source-map true}}}}}

             :uberjar {:hooks [leiningen.cljsbuild minify-assets.plugin/hooks]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                             {:source-paths ["env/prod/cljs"]
                                              :compiler
                                              {:optimizations :advanced
                                               :pretty-print false}}}}}})

; Note to self: to compile for github pages, "lein cljsbuild once prod" and 
; copy resources/public/js/app.js into gh-pages fork at reagent-example/js/app.js,
; then "lein minify-assets" and copy resources/public/css/site.min.css over
