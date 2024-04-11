package org.example.landoflustrous.model;

public class Bike extends TrafficEntity {

    public Bike(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getSpeed(Tile tile1, Tile tile2) {
        return 0;
    }

    @Override
    public void followPath(Path path) {

    }
}
