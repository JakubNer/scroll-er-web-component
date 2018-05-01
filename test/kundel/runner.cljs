(ns kundel.runner
  (:require [cljs.test :refer-macros [run-tests]]
            [kundel.spacing-test]))

(defn ^:export run []
  (run-tests 'kundel.spacing-test))
