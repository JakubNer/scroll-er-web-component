(ns kundel.scroll-er
  (:require
    [goog.object :as go]
    [reagent.core :as r]
    [kundel.component :as c]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; w3c custom element registration and callback handlers.
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Registration occurs by calling the exported 'register' below.

;; The registered element will have the following name:
(def element-name "scroll-er")

;; The registered element works with the following attributes.
;;
;; NOTE:
;;   A reagent atom is created for each of the component's attributes.
;;
;;   The reagent component render function gets 'element' and 'attrs'
;;   properties.  The 'attrs' property contains the ratom with these
;;   element properties.  To read the value in the reagent component:
;;
;;      @(get attrs "some-text")
;;
;;   Note that each 'attrs' has to have a corresponding 'fns' entry below.
;;
;; Modify these to suite your element:
(def attrs {"pages" (r/atom nil)
            "current" (r/atom nil)
            "horizontal" (r/atom nil)
            "reappear" (r/atom nil) ;; just change this in some fashion to trigger, update as timestamp?
            "font-size-supplement-vmin" (r/atom nil)
            "collapsed-width-em" (r/atom nil)
            "unfurled-width-em" (r/atom nil)})

;; Custom translation functions for each attribute.
;; %1 is original property value, %2 is the new value.
;; Examples:
;;    #(do %2)              ;; just replaces old value.
;;    #(.parse js/JSON %2)  ;; parses JSON into JS object
;;    #(= "true" %2)        ;; parses boolean
;; This list's keys must match the 'attrs' list.
(def fns {"pages" #(js->clj (.parse js/JSON %2))
          "current" #(do %2)
          "horizontal" #(do
                          (.log js/console "horizontal attr :: " %2)
                          (= "true" (str %2)))
          "reappear" #(identity true)
          "font-size-supplement-vmin" #(identity 1)
          "collapsed-width-em" #(identity 1)
          "unfurled-width-em" #(identity 20)})

;; events:  "goto" :: event detail is page title to go to.





;; NO NEED TO MODIFY ANYTHING BELOW

(defn ^:export created [this]
  (doseq [keyval attrs]
    (swap! (val keyval) (get fns (key keyval)) (.getAttribute this (key keyval))))
  (r/render [c/render this attrs] this))      ;; attach reagent component

(defn attached [this]) ;; not wired into reagent component

(defn detached [this]) ;; not wired into reagent component

(defn ^:export changed [this property-name old-value new-value]
  (swap! (get attrs property-name) (get fns property-name) new-value))

;; register the w3c custom element.
(defn ^:export register []
  (when (.-registerElement js/document)
    (let [proto (.create js/Object (.-prototype js/HTMLElement))
          proto' (go/create "createdCallback" #(this-as this (created this))
                            "attachedCallback" #(this-as this (attached this))
                            "detachedCallback" #(this-as this (detached this))
                            "attributeChangedCallback" #(this-as this (changed this %1 %2 %3)))]
      (go/extend proto proto')
      (.registerElement js/document element-name #js{"prototype" proto}))))
