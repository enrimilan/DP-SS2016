package org.tuwien.experiment;

import org.tuwien.experiment.Utils.Utils;
import org.tuwien.experiment.entity.Earthquake;
import org.tuwien.experiment.entity.Volcano;

import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("USAGE: experiment VOLCANOES_FILE EARTHQUAKES_FILE");
        }
        ArrayList<Volcano> volcanoes = Utils.parseVolcanoes(args[0]);
        ArrayList<Earthquake> earthquakes = Utils.parseEarthquakes(args[1]);
    }

}