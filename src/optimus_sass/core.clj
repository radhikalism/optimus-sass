(ns optimus-sass.core
  (:import [org.apache.commons.io FileUtils]
           [java.io FileNotFoundException])
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [optimus.assets.creation :refer [last-modified existing-resource]]
            [optimus.assets.load-css :refer [create-css-asset]]))

(defn compile-file [file output compile-file?]
  (when compile-file? (shell/sh "sass" file output))
  (FileUtils/readFileToString (io/file output) "utf-8"))

(defn to-string&-substring [^java.net.URL file-url]
  (-> file-url
      .toString
      (.substring 5)))

(defn load-sass-asset [public-dir path]
  (let [resource (existing-resource public-dir path)
        new-path (str/replace (to-string&-substring resource) #"\.sass\z|\.scss\z" ".css")
        output-uri (.toURL (io/file new-path))
        output-last-modified (try (last-modified output-uri) (catch FileNotFoundException _e -1))
        compile-file? (< output-last-modified (last-modified resource))]
    (-> (create-css-asset new-path
                          (compile-file (to-string&-substring resource) new-path compile-file?)
                          (last-modified resource))
        (assoc :original-path path))))

(doseq [ext ["sass" "scss"]]
  (defmethod optimus.assets.creation/load-asset ext
    [public-dir path]
    (load-sass-asset public-dir path)))
