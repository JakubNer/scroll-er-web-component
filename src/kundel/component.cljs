(ns kundel.component
  (:require
    [reagent.core :as r]
    [debux.cs.core :refer-macros [clog dbg break]]))

(def unfurled? (r/atom false))

(defn get-button-size [num-sections]
  (/ 100 num-sections))

(defn get-text-ems [title-col]
      20)

(defn get-animated-style-map [dimension-k collapsed-width-em unfurled-width-em]
  {:-webkit-transition (str (name dimension-k) " .5s ease-in-out")
   :-moz-transition (str (name dimension-k) " .5s ease-in-out")
   :-o-transition (str (name dimension-k) " .5s ease-in-out")
   :transition (str (name dimension-k) " .5s ease-in-out")
   dimension-k (str (if @unfurled? unfurled-width-em collapsed-width-em) "em")})

(defn render-horizontal [this titles events button-size collapsed-width-em unfurled-width-em]
  [:div
   [:div {:style (merge (get-animated-style-map :height collapsed-width-em unfurled-width-em)
                        {:position         "fixed"
                         :display          "flex"
                         :background-color "black"
                         :bottom           "0"
                         :left             "0"
                         :width            "100%"})}
    (for [title titles]
      (let [title-width (+ unfurled-width-em 1)]
        [:div {:style {:position         "relative"
                       :background-color "white"
                       :height           (str title-width "em")
                       :width            (str button-size "%")
                       :top              "2px"
                       :margin-left      "1px"
                       :margin-right     "1px"}}
         [:div {:style {:-webkit-transform "rotate(-90deg)"
                        :-moz-transform    "rotate(-90deg)"
                        :-ms-transform     "rotate(-90deg)"
                        :-o-transform      "rotate(-90deg)"
                        :position          "absolute"
                        :left              (str (/ button-size 2) "%")
                        :bottom            (str (/ unfurled-width-em 2) "em")}}
          "FOO FOO BAR BAZ"]]))]
   [:div {:style (merge (get-animated-style-map :bottom collapsed-width-em unfurled-width-em)
                        {:position "fixed"
                         :left     "50%"})}
    [:div {:style {:position "absolute"
                   :left      "-2.5em"
                   :bottom    "0px"
                   :background-color "black"
                   :width           "5em"
                   :height            (str collapsed-width-em "em")
                   :border-top-right-radius (str collapsed-width-em "em .5em")
                   :-moz-border-radius-topright (str collapsed-width-em "em .5em")
                   :border-top-left-radius (str collapsed-width-em "em .5em");
                   :-moz-border-radius-topleft (str collapsed-width-em "em .5em")}}];
    [:div {:style {:position "absolute"
                   :right      "-2em"
                   :bottom    "0px"
                   :width   "4em"
                   :height    (str collapsed-width-em "em")
                   :background "linear-gradient(to bottom,
                                  black, black 20%,
                                  lightgrey 30%, lightgrey 40%,
                                  black 40%, black 60%,
                                  lightgrey 60%, lightgrey 70%,
                                  black 80%, black 100%)"}
           :on-click #(reset! unfurled? (not @unfurled?))}]]])

(defn render-vertical [this titles events button-size collapsed-width-em unfurled-width-em]
  [:div
   [:div {:style (merge (get-animated-style-map :width collapsed-width-em unfurled-width-em)
                        {:position         "fixed"
                         :background-color "black"
                         :top              "0"
                         :right            "0"
                         :height           "100%"})}
    (for [title titles]
      (let [title-width (+ unfurled-width-em 1)]
        [:div {:style {:position         "relative"
                       :background-color "white"
                       :height           (str button-size "%")
                       :width            (str title-width "em")
                       :left             "2px"
                       :margin-top       "1px"
                       :margin-bottom    "1px"}}
         [:div {:style {:position        "absolute"
                        :text-align      "center"
                        :width           (str unfurled-width-em "em")
                        :top             "30%"}}
           "FOO FOO BAR BAZ"]]))]
   [:div {:style (merge (get-animated-style-map :right collapsed-width-em unfurled-width-em)
                        {:position "fixed"
                         :top      "50%"})}
    [:div {:style {:position "absolute"
                   :top      "-2.5em"
                   :right    "0px"
                   :background-color "black"
                   :height           "5em"
                   :width            (str collapsed-width-em "em")
                   :border-top-left-radius (str collapsed-width-em "em .5em")
                   :-moz-border-radius-topleft (str collapsed-width-em "em .5em")
                   :border-bottom-left-radius (str collapsed-width-em "em .5em");
                   :-moz-border-radius-bottomleft (str collapsed-width-em "em .5em")}}];
    [:div {:style {:position "absolute"
                   :top      "-2em"
                   :right    "0px"
                   :height   "4em"
                   :width    (str collapsed-width-em "em")
                   :background "linear-gradient(to right,
                                  black, black 20%,
                                  lightgrey 30%, lightgrey 40%,
                                  black 40%, black 60%,
                                  lightgrey 60%, lightgrey 70%,
                                  black 80%, black 100%)"}
           :on-click #(reset! unfurled? (not @unfurled?))}]]])

(defn render [this attrs]
  (let [horizontal? @(get attrs "horizontal")
        pages @(get attrs "pages")
        collapsed-width-em @(get attrs "collapsed-width-em")
        unfurled-width-em @(get attrs "unfurled-width-em")
        titles (keys pages)
        events (vals pages)
        pages-count (count titles)
        button-size (get-button-size pages-count)
        text-ems (get-text-ems titles)]
    (if horizontal?
      (render-horizontal this titles events button-size collapsed-width-em unfurled-width-em)
      (render-vertical this titles events button-size collapsed-width-em unfurled-width-em))))
