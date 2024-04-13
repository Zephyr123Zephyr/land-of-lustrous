package org.example.landoflustrous.model;

public enum TrafficType {
    WALK(0.5, 1),
    BIKE(1.0, 2),
    BUS(2.0, 3),
    CAR(20.0, 5),
    TRAIN(3.0, 4);

    private final double carbonPer;
    private final int speed;

    TrafficType(double carbonPer, int speed){

        this.carbonPer = carbonPer;
        this.speed = speed;
    }

    public double getCarbonPer() {
        return this.carbonPer;
    }

    public int getSpeed(){
        return this.speed;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
