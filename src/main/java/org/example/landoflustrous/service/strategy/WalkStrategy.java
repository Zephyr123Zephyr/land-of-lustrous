package org.example.landoflustrous.service.strategy;

import org.example.landoflustrous.model.*;

import java.util.List;

public class WalkStrategy extends TrafficStrategy {

    public WalkStrategy(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public Route navigate(Coordinated start, Coordinated end) {
        Path toAdd = findPathRoaming(start, end, TrafficType.WALK);
        if (toAdd != null) return new Route(List.of(toAdd));
        else return null;
    }
}
