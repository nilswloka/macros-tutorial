(ns tutorial.macroology
  (:use [tutorial.utils :only [render]])
  (:require [net.cgrand.enlive-html :as html]
            [clojure.contrib.str-utils2 :as s]))

(def *child-nodes-with-class* (html/selector [html/root [:* (html/attr? :class)]]))
 
(defn- selector-for-node [{tag :tag, attrs :attrs}]
  (let [css-class (s/trim (first (s/split (:class attrs) #" ")))]
    `([~(keyword (str (name tag) "." css-class))] (html/content (~(keyword css-class) ~'ctxt)))))
 
(defmacro quick-snippet [name rsrc selector]
  (let [nodes (-> (html/html-resource (eval rsrc))
                  (html/select (eval `(html/selector ~selector)))
                  (html/select *child-nodes-with-class*))]
    `(html/defsnippet ~name ~rsrc ~selector
       [~'ctxt]
       ~@(reduce concat (map selector-for-node nodes)))))
