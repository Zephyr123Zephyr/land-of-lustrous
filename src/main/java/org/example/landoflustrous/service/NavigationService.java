package org.example.landoflustrous.service;

import org.example.landoflustrous.model.Bike;
import org.example.landoflustrous.model.GameMap;
import org.example.landoflustrous.model.Route;
import org.example.landoflustrous.service.strategy.BikeStrategy;
import org.example.landoflustrous.service.strategy.TaxiStrategy;
import org.example.landoflustrous.service.strategy.WalkStrategy;

import java.io.IOException;
import java.util.List;

public class NavigationService {

    private final GameMap gameMap;
    private final WalkStrategy walkStrategy;
    private final BikeStrategy bikeStrategy;
    private final TaxiStrategy taxiStrategy;

    public NavigationService(GameMap gameMap) {
        this.gameMap = gameMap;
        this.walkStrategy = new WalkStrategy(gameMap);
        this.bikeStrategy = new BikeStrategy(gameMap);
        this.taxiStrategy = new TaxiStrategy(gameMap);
    }
//    public List<Route> navigate(Coordinated start, Coordinated end) {}

    public static void main(String[] args) throws IOException {
        GameMap gameMap = new GameMap("mapedit/map.txt", List.of("mapedit/rail.txt"), List.of("mapedit/bus1.txt", "mapedit/bus2.txt"));
        NavigationService navigationService = new NavigationService(gameMap);
        Route route = navigationService.taxiStrategy.navigate(new Bike(19,7), new Bike(1, 24));
        System.out.println(route);
    }
}
