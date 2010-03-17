(ns tutorial.macroology
  (:use [tutorial.utils :only [render]])
  (:require [net.cgrand.enlive-html :as html])
  (:import [java.io ByteArrayInputStream]))

(def *nodes-with-class* (html/selector #{[[:* (html/attr? :class)]]}))

(defn- to-in-s [str] (ByteArrayInputStream. (.getBytes str "UTF-8")))
 
(defn- selector-for-node [{tag :tag, attrs :attrs}]
  (let [css-class (:class attrs)]
    `([~(keyword (str (name tag) "." css-class))] (html/content (~(keyword css-class) ~'ctxt)))))
 
(defmacro quick-snippet [name rsrc selector]
  (let [nodes (-> (html/html-resource (eval rsrc))
                  (html/select (html/selector (eval selector)))
                  (html/select *nodes-with-class*))]
    `(html/defsnippet ~name ~rsrc ~selector
       [~'ctxt]
       ~@(reduce concat (map selector-for-node nodes)))))
