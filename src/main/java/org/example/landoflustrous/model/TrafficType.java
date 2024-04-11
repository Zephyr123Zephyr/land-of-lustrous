package org.example.landoflustrous.model;

public enum TrafficType {
    WALK, BIKE, BUS, CAR, TRAIN;

    @Override
    public String toString() {
        return switch (this) {
            case WALK -> "walk";
            case BIKE -> "bike";
            case BUS -> "bus";
            case CAR -> "car";
            case TRAIN -> "train";
        };
    }
}
