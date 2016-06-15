package org.tuwien.experiment;

import org.tuwien.experiment.Utils.Utils;
import org.tuwien.experiment.entity.Correlation;
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
        checkForCorrelationBetween(volcanoes, earthquakes, 15000);
    }

    public static ArrayList<Correlation> checkForCorrelationBetween(ArrayList<Volcano> volcanoes, ArrayList<Earthquake> earthquakes, double maxDist) {
        int index = 0;
        ArrayList<Correlation> correlations = new ArrayList<>();
        for(Volcano v : volcanoes) {

            Correlation correlation = new Correlation(v);
            for(Earthquake e : earthquakes) {
                if(Utils.calculateDistanceBetween(v.getLatitude(), v.getLongitude(), e.getLatitude(), e.getLongitude())<=maxDist) {
                    correlation.addDependentEarthquake(e);
                }
            }
            if(correlation.getEarthquakes().size()>0) {
                correlations.add(correlation);
                System.out.println("Correlation: " + correlation.getVolcano().getLocation() + "-> " + correlation.getEarthquakes().size() + " earthquakes");
                index ++;
            }
        }
        System.out.println("Approx " + (double) (index * 100 / volcanoes.size()) + "% of the vulcanoes ("+ index +") depend on earthquakes");
        return correlations;
    }

}