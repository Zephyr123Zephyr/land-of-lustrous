package org.example.landoflustrous.model;

/**
 * Every class that has a coordinate must extend from this class.
 */

public abstract class Coordinated {

    public int x;
    public int y;

    public int distance(Coordinated other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public String getCoordinateString() {
        return String.format("(%d, %d)", x, y);
    }
}