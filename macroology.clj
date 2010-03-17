(ns tutorial.macroology
  (:use [tutorial.utils :only [render]])
  (:require [net.cgrand.enlive-html :as html])
  (:import [java.io ByteArrayInputStream]))

(def *nodes-with-class* (html/selector #{[[:* (html/attr? :class)]]}))

(defn- to-in-s [str] (ByteArrayInputStream. (.getBytes str "UTF-8")))
 
(defn- selector-for-node [{tag :tag, attrs :attrs}]
  (let [css-class (:class attrs)]
    `([~(keyword (str (name tag) "." css-class))] (html/content (~(keyword css-class) ~'ctxt)))))
 
(defmacro quick-template [name rsrc]
  (let [nodes (html/select (html/html-resource (eval rsrc)) *nodes-with-id*)]
    `(html/deftemplate ~name ~rsrc
       [~'ctxt]
       ~@(reduce concat (map selector-for-node nodes)))))
