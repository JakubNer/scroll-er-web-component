(ns kundel.element
  (:require
    [reagent.core :as r]
    [lucuma.core :as lc]))

(defn fire-event [this]
  (let [some-text (js->clj (.-some-text this))                        ;; get property from custom element
        event (.createEvent js/document "Event")]                     ;; new event to fire from custom element
    (.log js/console "created event :: " event)
    (.log js/console "against element :: " this)
    (.initEvent event "meh" true true)
    (aset event "detail" (str "Text added by event to " some-text))   ;; add some detail text to event
    (.dispatchEvent this event)))                                     ;; fire event

(defn my-app [this]                                                   ;; top level Reagent render
  [:div
    [:h1 {:on-click #(fire-event this)} "CLICK ME"]                   ;; call fire-event (above) on click
    [:p "Click above."]])

(lc/defcustomelement my-element                                       ;; instrument custom element using lucuma
                  :on-created #()
                  :on-attached #(r/render [my-app %] %)               ;; attach top level Reagent component (above)
                  :properties {:some-text "defalult"})                ;; expose a property

(defn ^:export register []                                            ;; export method to register our custom element
  (lc/register my-element))

