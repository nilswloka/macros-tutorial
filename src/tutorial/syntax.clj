(ns tutorial.syntax)

(defmacro int-add [a b]
  `(+ (int ~a) (int ~b)))

(defn slow-add [a b]
  (dotimes [_ 5]
    (time
     (dotimes [_ 5000000]
       (+ a b)))))

(defn fast-add [a b]
  (dotimes [_ 5]
    (time
     (dotimes [_ 5000000]
       (int-add a b)))))