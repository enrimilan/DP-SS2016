#!/bin/bash

sudo rm volcanoes.txt earthquakes.txt
sudo wget http://volcano.si.edu/includes/tdpmap/volcanoes.txt
sudo wget http://volcano.si.edu/includes/tdpmap/earthquakes.txt
sudo java -jar Experiment.jar volcanoes.txt earthquakes.txt 40000