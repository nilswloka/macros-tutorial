(ns tutorial.serious
  (:use compojure
        [tutorial.utils :only [render render-snippet]])
  (:require [net.cgrand.enlive-html :as html]
            [tutorial.macroology :as m]))

(def *data* {:title "Gravity's Rainbow"
             :author "Thomas Pynchon"
             :publisher "Penguin"
             :date "1974"
             :edition "1st"
             :ISBN "foo"})

(m/quick-snippet book "resources/widgets.html" [:.book])

(println (render-snippet (book *data*)))