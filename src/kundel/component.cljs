(ns kundel.component
  (:require
    [reagent.core :as r]
    [debux.cs.core :refer-macros [clog dbg break]]))

(def unfurled? (r/atom false))
(def visible?  (r/atom false))

(defn fire-event [this event-text]
  "Dispatch 'goto' events."
  (let [event (.createEvent js/document "Event")]
    (.initEvent event "goto" true true)
    (aset event "detail" event-text)
    (.dispatchEvent this event)))

(defn get-button-size [num-sections]
  (/ 100 num-sections))

(defn get-animated-style-map [dimension-k collapsed-width-em unfurled-width-em]
  {:-webkit-transition (str (name dimension-k) " .5s ease-in-out")
   :-moz-transition (str (name dimension-k) " .5s ease-in-out")
   :-o-transition (str (name dimension-k) " .5s ease-in-out")
   :transition (str (name dimension-k) " .5s ease-in-out")
   dimension-k (str (if @unfurled? unfurled-width-em collapsed-width-em) "em")})

(defn render-horizontal [this pages current button-size font-size-vmin collapsed-width-em unfurled-width-em]
  (let [unfurled-container-width-em (+ unfurled-width-em 2)
        half-text-width-em (/ unfurled-width-em 2)]
    [:div {:style {:visibility (if @visible? "visible" "hidden")
                   :opacity    (if @visible? 1 0)
                   :transition "visibility 0s, opacity .5s linear"
                   :font-size (str font-size-vmin "vmin")}}
     [:div {:style (merge (get-animated-style-map :height collapsed-width-em unfurled-container-width-em)
                          {:position         "fixed"
                           :display          "flex"
                           :background-color "black"
                           :bottom           "0"
                           :left             "0"
                           :width            "100%"})
            :on-mouse-leave #(reset! unfurled? false)}
      (for [page pages]
        (let [title (key page)
              event (val page)
              half-text-height-em (/ (+ (quot (count title) unfurled-width-em) 1) 2)]
          [:div {:key   (gensym "key-")
                 :style {:position         "relative"
                         :background-color (if (= current event) "lightgrey" "white")
                         :height           (str unfurled-container-width-em "em")
                         :width            (str button-size "%")
                         :top              "2px"
                         :margin-left      "1px"
                         :margin-right     "2px"
                         :overflow         "hidden"
                         :cursor           "pointer"}
                 :on-click #(fire-event this event)}
           [:div {:style {:position "absolute"
                          :top      "50%"
                          :left     "50%"}}
            [:div {:style {:-webkit-transform "rotate(-90deg)"
                           :-moz-transform    "rotate(-90deg)"
                           :-ms-transform     "rotate(-90deg)"
                           :-o-transform      "rotate(-90deg)"}}
             [:div {:style {:text-align  "center"
                            :font-family "monospace, monospace"
                            :position    "absolute"
                            :width       (str unfurled-width-em "em")
                            :top         (str "-" half-text-height-em "em")
                            :left        (str "-" half-text-width-em "em")}}
              title]]]]))]
     [:div {:style (merge (get-animated-style-map :bottom collapsed-width-em unfurled-container-width-em)
                          {:position "fixed"
                           :left     "50%"})}
      [:div {:style {:position                    "absolute"
                     :left                        "-2.5em"
                     :bottom                      "0px"
                     :background-color            "black"
                     :width                       "5em"
                     :height                      (str collapsed-width-em "em")
                     :border-top-right-radius     (str collapsed-width-em "em .5em")
                     :-moz-border-radius-topright (str collapsed-width-em "em .5em")
                     :border-top-left-radius      (str collapsed-width-em "em .5em") ;
                     :-moz-border-radius-topleft  (str collapsed-width-em "em .5em")}}] ;
      [:div {:style    {:position   "absolute"
                        :right      "-2em"
                        :bottom     "0px"
                        :width      "4em"
                        :height     (str collapsed-width-em "em")
                        :background "linear-gradient(to bottom,
                                  black, black 20%,
                                  lightgrey 30%, lightgrey 40%,
                                  black 40%, black 60%,
                                  lightgrey 60%, lightgrey 70%,
                                  black 80%, black 100%)"
                        :cursor     "n-resize"}
             :on-click #(reset! unfurled? (not @unfurled?))}]]]))

(defn render-vertical [this pages current button-size font-size-vmin collapsed-width-em unfurled-width-em]
  (let [unfurled-container-width-em (+ unfurled-width-em 2)
        half-text-width-em (/ unfurled-width-em 2)
        half-collapsed-width-em (/ collapsed-width-em 2)]
    [:div {:style {:visibility (if @visible? "visible" "hidden")
                   :opacity    (if @visible? 1 0)
                   :transition "visibility 0s, opacity .5s linear"
                   :font-size (str font-size-vmin "vmin")}}
     [:div {:style (merge (get-animated-style-map :width collapsed-width-em unfurled-container-width-em)
                          {:position         "fixed"
                           :background-color "black"
                           :top              "0"
                           :right            "0"
                           :height           "100%"})
            :on-mouse-leave #(reset! unfurled? false)}
      (for [page pages]
        (let [title (key page)
              event (val page)
              half-text-height-em (/ (+ (quot (count title) unfurled-width-em) 1) 2)]
          [:div {:key   (gensym "key-")
                 :style {:position         "relative"
                         :background-color (if (= current event) "lightgrey" "white")
                         :height           (str button-size "%")
                         :width            (str unfurled-container-width-em "em")
                         :left             "2px"
                         :margin-top       "1px"
                         :margin-bottom    "2px"
                         :overflow         "hidden"
                         :cursor           "pointer"}
                 :on-click #(fire-event this event)}
           [:div {:style {:position "absolute"
                          :top      "50%"
                          :left     "50%"}}
            [:div {:style {:text-align "center"
                           :font-family "monospace, monospace"
                           :position   "absolute"
                           :width      (str unfurled-width-em "em")
                           :top        (str "-" half-text-height-em "em")
                           :left       (str "-" half-text-width-em "em")}}
             title]]]))]
     [:div {:style (merge (get-animated-style-map :right collapsed-width-em unfurled-container-width-em)
                          {:position "fixed"
                           :top      "0px"
                           :background-color "black"
                           :height (str (* 3 collapsed-width-em) "em")
                           :width (str (* 2 collapsed-width-em) "em")
                           :border-left "1px solid #000"
                           :border-bottom "1px solid #000"
                           :overflow "hidden"
                           :border-bottom-left-radius     (str collapsed-width-em "em")
                           :-moz-border-radius-bottomleft (str collapsed-width-em "em")})}
      [:div {:style {:position "absolute"
                     :left     "1px"
                     :top      "1px"
                     :width    "110%"
                     :height   "100%"
                     :cursor           "pointer"
                     :background-color (if (= current (val (first pages))) "lightgrey" "white")}
             :on-click #(fire-event this (val (first pages)))}]]
     [:div {:style (merge (get-animated-style-map :right 0 (- unfurled-container-width-em collapsed-width-em))
                          {:position "fixed"
                           :top      "0px"
                           :width    (str (* 2 collapsed-width-em) "em")
                           :border-top "1px solid #000"
                           :height "1em"
                           :cursor   "pointer"
                           :background-color (if (= current (val (first pages))) "lightgrey" "white")})
            :on-click #(fire-event this (val (first pages)))}]
     [:div {:style (merge (get-animated-style-map :right 0 (- unfurled-container-width-em collapsed-width-em))
                          {:position "fixed"
                           :top      "1em"
                           :width    (str (* 2 collapsed-width-em) "em")
                           :height (str (- (* 3 collapsed-width-em) 1) "em")
                           :cursor   "pointer"
                           :background-color (if (= current (val (first pages))) "lightgrey" "white")})
            :on-click #(fire-event this (val (first pages)))}]
     [:img {:src "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAJPSURBVGhD7do5ixRBGIfx8QYRDERQWPAIzQQRBAURv4LgCoLifSsiiIGBkZmIibBgZiAaKpgIot9AEBMxMRINDNREPJ7/sK80RU3121d1g/3CL5iarmaenZmlZ3Yn44zzf806bF20Sgt9zzye4SZWaMEx1/EbfxZ9xnb0NldgD0YewxPzDcV9soBe5jLCByOemNi+h8g+syJMWUxsT/YQRRRf3/IjuC2pmPBYyRpyCWHEO2zCk8KamRUTHifZQmZFbIRmJbwx4TGSJeQiUhE23pjwfuk85AJSEUtwDmumt9Ixy6EJ75NOQzwRd6D1V/DGhOvSWch5eCOMNyZck05C9FKpGmE8MTGthzSJMHViWg05i6YRpmpMayFn0FaEqRLTSkgXEcYb0zjkNLqKMJ6Yl1iKWnMKXUcYT8wDVI7JGWFajzmJ3BGmtZgT6CvCNI4ZQoSpHXMcQ4kwxRhd5pfG7MIvFA/oO8J4Ym5gOuGXBUOJMGUxLzCdzfgELb7BkCJMGPMIWtfb4Rj+zVrshN5YmroRb3EUBxIO4Sli+1OKMXp8e7FjemvGNHkm9Ox6Rj/VD4idI6UYk5wmEV9RZeo8K+KK0W+w2GaPXCFyDcnZjdhGj5wh+sY+OWMIxpAaxhCPMaSGMcRjUCG6cIxt9NBfY6vMc8TO43EVyZlDbKOXrkg9sx5fEDuHx0GUjj5YxTZ7fIc+ft5OuIePiO33+IkNKJ3DiJ1gKO7DPXcRO0nfXmM1Ks0RvEfshLnp/XQLjf7BZgv2YH8P9mEbliExk8lfN8HbebLAzesAAAAASUVORK5CYII="
            :style (merge (get-animated-style-map :right (- collapsed-width-em half-collapsed-width-em) (- unfurled-container-width-em half-collapsed-width-em))
                          {:position "fixed"
                           :top (str half-collapsed-width-em "em")
                           :width (str (* 2 collapsed-width-em) "em")
                           :cursor   "pointer"})
            :on-click #(fire-event this (val (first pages)))}]
     [:div {:style (merge (get-animated-style-map :right collapsed-width-em unfurled-container-width-em)
                          {:position "fixed"
                           :top      "50%"})}
      [:div {:style {:position                      "absolute"
                     :top                           "-2.5em"
                     :right                         "0px"
                     :background-color              "black"
                     :height                        "5em"
                     :width                         (str collapsed-width-em "em")
                     :border-top-left-radius        (str collapsed-width-em "em .5em")
                     :-moz-border-radius-topleft    (str collapsed-width-em "em .5em")
                     :border-bottom-left-radius     (str collapsed-width-em "em .5em") ;
                     :-moz-border-radius-bottomleft (str collapsed-width-em "em .5em")}}] ;
      [:div {:style    {:position   "absolute"
                        :top        "-2em"
                        :right      "0px"
                        :height     "4em"
                        :width      (str collapsed-width-em "em")
                        :background "linear-gradient(to right,
                                  black, black 20%,
                                  lightgrey 30%, lightgrey 40%,
                                  black 40%, black 60%,
                                  lightgrey 60%, lightgrey 70%,
                                  black 80%, black 100%)"
                        :cursor     "w-resize"}
             :on-click #(reset! unfurled? (not @unfurled?))}]]]))

(defn render [this attrs]
  (let [horizontal? @(get attrs "horizontal")
        pages (into [] @(get attrs "pages"))
        current @(get attrs "current")
        font-size-vmin @(get attrs "font-size-vmin")
        collapsed-width-em @(get attrs "collapsed-width-em")
        unfurled-width-em @(get attrs "unfurled-width-em")
        pages-count (count pages)
        button-size (get-button-size pages-count)]
    (when @(get attrs "reappear") ;; whenever reappear triggered, make divs not visible, and animate visible after 500ms
      (reset! (get attrs "reappear") false)
      (reset! visible? false)
      (js/setTimeout #(reset! visible? true) 500))
    (if horizontal?
      (render-horizontal this pages current button-size font-size-vmin collapsed-width-em unfurled-width-em)
      (render-vertical this pages current button-size font-size-vmin collapsed-width-em unfurled-width-em))))
