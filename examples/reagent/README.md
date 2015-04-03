# cljs-pikaday example reagent project

This is a pretty simple project, created with 
[`lein new reagent`](https://github.com/reagent-project/reagent-template). 

To run it, run `lein figwheel` in this directory and open http://localhost:3449/
in a browser.

The [main code](src/cljs/cljs_pikaday_reagent_example/core.cljs) 
creates two atoms which represent the start and end dates of a trip, and 
displays the total number of days in the trip.

This example also demonstrates passing atoms into the pikaday constructor 
to dynamically set the minimum and maximum dates available for each range 
(so you can't have a trip which starts in the future and extends into the 
past).

You can see this in action (eventually) at 
http://timgilbert.github.io/cljs-pikaday/reagent-example/index.html.
