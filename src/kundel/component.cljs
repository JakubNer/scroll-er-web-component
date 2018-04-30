(ns kundel.component
  (:require
    [reagent.core :as r]
    [debux.cs.core :refer-macros [clog dbg break]]))

(defn get-button-size [num-sections]
      2)

(defn get-space-size [num-sections]
      2)

(defn get-text-ems [title-col]
      2)

(defn render-horizontal [this]
  [:div {:style {:position "fixed"
                 :bottom   "0"
                 :left     "0"
                 :height   "1em"
                 :width    "100%"}}])

(defn render-vertical [this]
  [:div {:style {:position "fixed"
                 :top      "0"
                 :right    "0"
                 :width    "1em"
                 :height   "100%"}}])

(defn render [this attrs]
  (let [horizontal? @(get attrs "horizontal")
        pages @(get attrs "pages")
        titles (keys pages)
        pages-count (count titles)
        button-size (get-button-size pages-count)
        space-size (get-space-size pages-count)
        text-ems (get-text-ems titles)]
    (if horizontal?
      (render-horizontal this)
      (render-vertical this))))
