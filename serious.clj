(ns tutorial.serious
  (:use compojure
        [tutorial.utils :only [render]])
  (:require [net.cgrand.enlive-html :as html]
            [tutorials.macroology :as m]))

(m/quick-template book "resources/book.html" [:.book])
 