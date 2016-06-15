package org.tuwien.experiment.entity;

public class Earthquake {

    private Double latitude;
    private Double longitude;
    private Double depth;

    public Earthquake(Double latitude, Double longitude, Double depth) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.depth = depth;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }
}
