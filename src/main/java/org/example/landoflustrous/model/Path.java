package org.example.landoflustrous.model;

import org.example.landoflustrous.config.Constant;

import java.util.ArrayList;
import java.util.List;

public class Path {

    public TrafficType trafficType;
    private final List<Tile> tileList;
    private int cost;

    public List<Tile> getTileList() {
        return tileList;
    }

    public Path(TrafficType trafficType) {
        this.trafficType = trafficType;
        this.tileList = new ArrayList<>();
        this.cost = 0;
    }

    public Path(TrafficType trafficType, List<Tile> tileList) {
        this.trafficType = trafficType;
        this.tileList = new ArrayList<>();
        this.cost = 0;
        tileList.forEach(this::addTile);
    }

    public void addTile(Tile tile) {
        if (!tileList.isEmpty()) {
            cost += Constant.timeCostOfTraffic(tileList.getLast(), tile, trafficType);
        }
        tileList.add(tile);
    }

    public int getCost() {
        return cost;
    }

    public int getLength() {
        return Math.max(tileList.size() - 1, 0);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile tile : tileList) {
            stringBuilder.append(tile.getCoordinateString()).append(", ");
        }
        return String.format("%s: %s cost: %s", trafficType.toString(), stringBuilder, cost);
    }
}
