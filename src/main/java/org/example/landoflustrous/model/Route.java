package org.example.landoflustrous.model;

import org.example.landoflustrous.config.Constant;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private int startX, startY;
    private int endX, endY;


    List<Path> pathList;

    public Route(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.pathList = new ArrayList<>();
    }
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Route(List<Path> pathList) {
        this.pathList = pathList;
    }

    public void addPath(Path path) {
        pathList.add(path);
    }

    public int getTotalCost() {
        int totalCost = 0;
        TrafficType previousTrafficType = null;
        for (Path path : pathList) {
            totalCost += path.getCost();
            if (previousTrafficType != null && !path.trafficType.equals(previousTrafficType)) {
                totalCost += Constant.timeCostOnShift(previousTrafficType, path.trafficType);
            }
            previousTrafficType = path.trafficType;
        }
        return totalCost;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        pathList.forEach(path -> stringBuilder.append(path.toString()).append('\n'));
        stringBuilder.append("TOTAL COST: ").append(getTotalCost());
        return stringBuilder.toString();
    }
}
