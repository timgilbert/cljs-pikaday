# cljs-pikaday

`cljs-pikaday` is intended to provide a native ClojureScript interface to the 
[Pikaday](https://github.com/dbushell/Pikaday) JavaScript date picker, intended 
for use in the various ClojureScript react frameworks.

The implementation currently works for 
[reagent](https://github.com/reagent-project/reagent) (I'm hoping to add an 
[Om]() implementation soon, and maybe a nicer interface for 
[re-frame](https://github.com/Day8/re-frame)).

`cljs-pikaday` currently uses plain JavaScript `Date` objects for 
its values - I'd like to have it able to use cljs-time and/or 
moment at some point, because ugh, JavaScript `Date` objects.

## Installation

Add `[cljs-pikaday "0.1.1"]` to your project's `:dependencies` vector:

[![Clojars Project](http://clojars.org/cljs-pikaday/latest-version.svg)](http://clojars.org/cljs-pikaday)

## reagent interface

The reagent implementation accepts an ratom 
(or [reaction](https://github.com/Day8/re-frame#how-flow-happens-in-reagent))
as its input. When the user selects a new date, the component will update 
the atom, and if the atom is updated elsewhere, the date-picker will display 
the new date.

There is a more fleshed-out example in the 
[examples/reagent directory](examples/reagent/), but the simplest way to use
it would be something like this:

```clojure
(ns cljs-pikaday.core
    (:require [reagent.core :as reagent]
              [cljs-pikaday.reagent :as pikaday]))

(defonce the-date (reagent/atom (js/Date.)))

(defn home-page []
  [:div [:h2 "Select a date"]
    [pikaday/date-selector {:date-atom the-date}]])

(reagent/render [home-page] (.getElementById js/document "app")))
```

`pikaday/date-selector` returns code for an `<input>` tag, and 
sets up various reagent lifecycle methods to instantiate and bind 
a `Pikaday` instance. When the user selects a new date in the input 
field, the atom passed in to the `:date-atom` property will be 
`reset!` with its new value.

There is [an example reagent project](examples/reagent/) which demonstrates 
some additional functionality.

## CSS

Pikaday comes with its own CSS file, which is autoloadable through various 
javascript packaging tools. If an interface to these exists in ClojureScript 
I can't find it, so as a workaround it's probably easiest to simplly download 
the [pikaday.css file from github](https://github.com/dbushell/Pikaday/blob/master/css/pikaday.css)
and reference it in your HTML.

## TODO

* document min/max date atoms
* Om interface
* tests(!)
