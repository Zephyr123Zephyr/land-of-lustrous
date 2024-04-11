package org.example.landoflustrous.model;

import java.util.ArrayList;
import java.util.List;

public abstract class PublicTransLine {

    TrafficType trafficType;
    List<Tile> tileList = new ArrayList<>();
    List<Station> stations = new ArrayList<>();
    int interval;

}
