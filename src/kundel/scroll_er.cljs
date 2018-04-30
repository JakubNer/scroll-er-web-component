(ns kundel.scroll-er
  (:require
    [reagent.core :as r]
    [lucuma.core :as lc]))

(defn fire-event [this]
  (let [some-text (js->clj (.-some-text this))
        event (.createEvent js/document "Event")]
    (.log js/console "created event :: " event)
    (.log js/console "against element :: " this)
    (.initEvent event "meh" true true)
    (aset event "detail" (str "Text added by event to " some-text))
    (.dispatchEvent this event)))

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

(defn root [this]
  (let [horizontal? (.-horizontal this)
        pages (.-pages this)]
        ;titles (keys pages)
        ;pages-count (count titles)
        ;button-size (get-button-size pages-count)
        ;space-size (get-space-size pages-count)
        ;text-ems (get-text-ems titles)]
    (.log js/console " :: " pages)
    (if horizontal?
      (render-horizontal this)
      (render-vertical this))))

(lc/defcustomelement scroll-er
                  :on-created #()
                  :on-attached #(r/render [root %] %)
                  :on-property-changed #(do
                                          (.log js/console "on-property-changed")
                                          (r/render [root %1] %1))
                  :properties {:pages {:default {} :type :object :attributes? true},
                               :current {:default "" :type :string}
                               :horizontal {:type :boolean :default false}})

(defn ^:export register []
  (lc/register scroll-er))

