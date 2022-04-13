(defproject optimus-sass "0.2.0"
  :description "Sass/SCSS asset loader for Optimus"
  :url "http://github.com/onaio/optimus-sass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [commons-io/commons-io "2.11.0"]
                 [optimus "0.20.2"]]
  :resource-paths ["deps"]
  :global-vars {*warn-on-reflection* true})
