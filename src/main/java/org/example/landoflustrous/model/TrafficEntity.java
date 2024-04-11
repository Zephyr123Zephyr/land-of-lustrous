package org.example.landoflustrous.model;

public abstract class TrafficEntity extends Actor {

    public abstract int getSpeed(Tile tile1, Tile tile2);

    public abstract void followPath(Path path);

    public void move(int dX, int dY) {
        x += dX;
        y += dY;
    }
}
