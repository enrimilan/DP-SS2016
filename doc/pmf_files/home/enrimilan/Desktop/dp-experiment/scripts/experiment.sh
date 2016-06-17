#!/bin/bash

if curl --output /dev/null --silent --head --fail "http://volcano.si.edu/includes/tdpmap/volcanoes.txt"; then
  wget -O volcanoes.txt http://volcano.si.edu/includes/tdpmap/volcanoes.txt
else
  echo "Can't connect to host to download volcanoes. Looking for local files instead..."
fi

if curl --output /dev/null --silent --head --fail "http://volcano.si.edu/includes/tdpmap/earthquakes.txt"; then
  wget -O earthquakes.txt http://volcano.si.edu/includes/tdpmap/earthquakes.txt
else
  echo "Can't connect to host to download earthquakes. Looking for local files instead..."
fi

if [ -f volcanoes.txt ] && [ -f earthquakes.txt ]; then
  java -jar Experiment.jar volcanoes.txt earthquakes.txt 40000
else
  echo "File(s) missing to perform this experiment"
fi



