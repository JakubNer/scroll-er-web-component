# Scroller W3C Custom Element

## Summary

A page-by-page scrollbar with a pull-out to see page descriptions.

Supports vertical and horizontal layout:

![vertical](https://github.com/JakubNer/scroll-er-web-component/blob/master/assets/vertical.gif)

![horizontal](https://github.com/JakubNer/scroll-er-web-component/blob/master/assets/horizontal.gif)

Compnent registered as "scroll-er".

### Attributes

#### pages

Dictionary of page titles to event strings; each key is a page title, each value an event string.

Number of key-value pairs is the number of pages.

#### current

Title of currently selected page.

#### horizontal

If *true*, the scroller will be at the bottom and pages will be side-to-side.

## Dev Run Application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```

## Connect to REPL (command-line) from IntelliJ

Our 'project.clj' sets up figwheel :nrepl-port to 7002.

Create remote REPL run configuration to 127.0.0.1:7002.

Once started paste the following in the IntelliJ REPL (Alt-8)

```
(use 'figwheel-sidecar.repl-api)
(cljs-repl)
```

## Run Unit Tests

The *dev* build compiles unit tests.

To run them, start the REPL:

```
lein figwheel dev
```

In the REPL source the test runner and run the tests:

```
    (in-ns 'kundel-runner')
    (require '[kundel.runner :as t] :reload)
    (t/run)
```
