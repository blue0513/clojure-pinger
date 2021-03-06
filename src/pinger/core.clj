(ns pinger.core
  (:import (java.net URL HttpURLConnection))
  (:gen-class))

(defn response-code [address]
  (let [conn ^HttpURLConnection (.openConnection (URL. address))
        code (.getResponseCode conn)]
    (when (< code 400)
      (-> conn .getInputStream .close))
    code))
;; (response-code "http://google.com")

(defn available? [address]
  (= 200 (response-code address)))
;; (available? "http://google.com")

(defn -main []
  (let [addresses '("http://google.com"
                    "http://amazon.com"
                    "http://google.com/badurl")]
    (while true
      (doseq [address addresses]
        (println (available? address)))
      (Thread/sleep (* 1000 60)))))
