(ns tutorial.books
  (:use compojure
        [clojure.contrib.duck-streams :only [pwd]]
        [tutorial.utils :only [render render-snippet]]
        [clojure.contrib.pprint])
  (:require [net.cgrand.enlive-html :as html]
            [tutorial.macroology :as m])
  (:import [org.apache.commons.lang StringEscapeUtils]))

(def *resources* (str (pwd) "/src/resources/"))

(def *books* [{:title "Gravity's Rainbow"
               :author "Thomas Pynchon"
               :publisher "Viking Press"
               :date "1973"
               :edition "1st"
               :ISBN "?"}
              {:title "Life A User's Manual"
               :author "Georges Perec"
               :publisher "Hachette Litteratures"
               :date "1978"
               :edition "1st"
               :ISBN "?"}
              {:title "2666"
               :author "Roberto Bolano"
               :publisher "Editorial Anagrama"
               :date "2004"
               :edition "1st"
               :ISBN "978-8433968678"}])

;; =============================================================================
;; This is it
;; =============================================================================

(m/quick-snippet book "resources/widgets.html" [:.book])

(html/deftemplate index "resources/index.html"
  [ctxt]
  [:.books] (html/content (map book (:books ctxt))))

;; =============================================================================
;; Routes
;; =============================================================================

(defroutes app-routes
  (GET "/"
       (render (index {:books *books*})))
  (GET "*/main.css"
       (serve-file *resources* "main.css"))
  (ANY "*"
       [404 "Page Not Found"]))

;; =============================================================================
;; The App
;; =============================================================================

(defonce *app* (atom nil))

(defn start-app []
  (if (not (nil? @*app*))
    (stop @*app*))
  (reset! *app* (run-server {:port 8080}
                            "/*" (servlet app-routes))))

(defn stop-app []
  (when @*app* (stop @*app*)))