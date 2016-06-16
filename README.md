# dp-experiment

##Running the experiment
The simplest way to run the experiment is using the bash script, which can be found under `scripts`:
```
./experiment.sh
```

Important note: Java 8 including javafx is required to run this application. 
Since Oracle has removed javafx from some ARM distributions, it might be necessary to attach the external resource to javafx, so the 
`experiment.sh` needs to be adapted:
```
sudo wget https://bitbucket.org/javafxports/arm/downloads/armv6hf-sdk.zip
sudo unzip armv6hf-sdk.zip
sudo java -Djava.ext.dirs=<path_to_extraced_zip>/rt/lib/ext -jar Experiment.jar volcanoes.txt earthquakes.txt 15000
```

If you are using OpenJDK in Ubuntu you need to install `openjfx`:
```
sudo apt-get install openjfx
```

##Building and running the application with maven
Building can be done using maven:
```
mvn install
```
Running the application:
```
mvn exec:java -Dexec.mainClass=org.tuwien.experiment.App -Dexec.args="volcanoes.txt earthquakes.txt 40000"
```
It is assumed that `volcanoes.txt` and `earthquakes.txt` were already downloaded.
