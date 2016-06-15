package org.tuwien.experiment.entity;

import java.util.ArrayList;

public class Correlation {

    private Volcano volcano;
    private ArrayList<Earthquake> earthquakes;

    public Correlation(Volcano volcano) {
        this.volcano = volcano;
        this.earthquakes = new ArrayList<>();
    }

    public void addDependentEarthquake(Earthquake e) {
        earthquakes.add(e);
    }

    public Volcano getVolcano() {
        return volcano;
    }

    public void setVolcano(Volcano volcano) {
        this.volcano = volcano;
    }

    public ArrayList<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public void setEarthquakes(ArrayList<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }
}
