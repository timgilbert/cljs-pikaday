(defproject cljs-pikaday-reagent-example "0.1.0-SNAPSHOT"
  :description "reagent example project for cljs-pikaday"
  :url "https://github.com/timgilbert/cljs-pikaday"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :source-paths ["src/clj" "../../src" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring-server "0.4.0"]
                 [cljsjs/react "0.13.1-0"]
                 [reagent "0.5.0"]
                 [reagent-forms "0.4.9"]
                 [reagent-utils "0.1.4"]
                 [org.clojure/clojurescript "0.0-3169" :scope "provided"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.4"]
                 [prone "0.8.1"]
                 [compojure "1.3.3"]
                 [selmer "0.8.2"]
                 [environ "1.0.0"]
                 [cljsjs/pikaday "1.3.2-0"]
                 [camel-snake-kebab "0.3.1" :exclusions [org.clojure/clojure]]
                 [shodan "0.4.1"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-environ "1.0.0"]
            [lein-ring "0.9.1"]
            [lein-asset-minifier "0.2.2"]]

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

                   :dependencies [[ring-mock "0.1.5"]
                                  [ring/ring-devel "1.3.2"]
                                  [leiningen "2.5.1"]
                                  [figwheel "0.2.5"]
                                  [weasel "0.6.0"]
                                  [com.cemerick/piggieback "0.2.0"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [pjstadig/humane-test-output "0.7.0"]]

                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.2.3-SNAPSHOT"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler cljs-pikaday-reagent-example.handler/app}

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:main "cljs-pikaday-reagent-example.dev"
                                                         :source-map true}}
}
}}

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
