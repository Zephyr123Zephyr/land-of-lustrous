package org.example.landoflustrous.config;

import org.example.landoflustrous.model.Tile;
import org.example.landoflustrous.model.TrafficType;

public class Constant {

    public static int WALK_TOLERANCE = 10;

    public static int timeCostOfTraffic(Tile tile1, Tile tile2, TrafficType trafficType) {
        switch (trafficType) {
            case WALK:
                if (tile1.isRoad && tile2.isRoad) {
                    return 25;
                } else if (tile1.isRail || tile2.isRail || tile1.isForbidden || tile2.isForbidden) {
                    return Integer.MAX_VALUE;
                } else {
                    return 50;
                }
            case BIKE:
                return (tile1.isRoad && tile2.isRoad) ? 12 : Integer.MAX_VALUE;
            case BUS:
                return (tile1.isRoad && tile2.isRoad) ? 4 : Integer.MAX_VALUE;
            case CAR:
                return (tile1.isRoad && tile2.isRoad) ? 3 : Integer.MAX_VALUE;
            case TRAIN:
                return (tile1.isRail && tile2.isRail) ? 2 : Integer.MAX_VALUE;
            default:
                return Integer.MAX_VALUE;
        }
    }

    // This method calculates the estimated shifting time between two trafficTypes.
    public static int timeCostOnShift(TrafficType trafficType1, TrafficType trafficType2) {
        if (trafficType2.equals(TrafficType.WALK)) {
            if (trafficType1.equals(TrafficType.BIKE)) return 10;  // Return a bike
            else return 0;
        } else if (trafficType2.equals(TrafficType.BIKE)) return 10;  // Rent a bike
        else if (trafficType2.equals(TrafficType.CAR)) return 50;  // Wait for a taxi
        else return 100;  // Wait for a bus or train
    }
}
