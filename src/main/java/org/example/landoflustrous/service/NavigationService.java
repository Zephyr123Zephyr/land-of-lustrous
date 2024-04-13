package org.example.landoflustrous.service;

import org.example.landoflustrous.model.*;
import org.example.landoflustrous.service.strategy.BikeStrategy;
import org.example.landoflustrous.service.strategy.PublicStrategy;
import org.example.landoflustrous.service.strategy.TaxiStrategy;
import org.example.landoflustrous.service.strategy.WalkStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NavigationService {

    private final GameMap gameMap;
    private final WalkStrategy walkStrategy;
    private final BikeStrategy bikeStrategy;
    private final TaxiStrategy taxiStrategy;
    private final PublicStrategy publicStrategy;

    public NavigationService(GameMap gameMap) {
        this.gameMap = gameMap;
        this.walkStrategy = new WalkStrategy(gameMap);
        this.bikeStrategy = new BikeStrategy(gameMap);
        this.taxiStrategy = new TaxiStrategy(gameMap);
        this.publicStrategy = new PublicStrategy(gameMap);
    }

    // Get your recommended routes here!!!
    public List<Route> navigate(Coordinated start, Coordinated end) {
        Path walkPath = walkStrategy.findPathRoaming(start, end, TrafficType.WALK);
        if (walkPath == null) throw new RuntimeException("No path exists!!!");
        List<Route> listOfRouteRecommended = new ArrayList<>(List.of(walkStrategy.navigate(start, end, walkPath)));
        listOfRouteRecommended.add(taxiStrategy.navigate(start, end, walkPath));
        if (!gameMap.getBikeStations().isEmpty()) listOfRouteRecommended.add(bikeStrategy.navigate(start, end, walkPath));
        if (!gameMap.getBusLines().isEmpty()) {
            Route onlyBusRoute = publicStrategy.navigate(start, end, walkPath, false);
            listOfRouteRecommended.add(onlyBusRoute);
            if (!gameMap.getRailLines().isEmpty()) {
                Route mixedRoute = publicStrategy.navigate(start, end, walkPath, true);
                if (!mixedRoute.equals(onlyBusRoute)) listOfRouteRecommended.add(mixedRoute);
            }
        }
        return listOfRouteRecommended.stream().filter(Objects::nonNull).toList();
    }

    public static void main(String[] args) throws IOException {
        GameMap gameMap = new GameMap("/maps/map1/map.txt", List.of("/maps/map1/rail.txt"), List.of("/maps/map1/bus1.txt", "/maps/map1/bus2.txt"));
        NavigationService navigationService = new NavigationService(gameMap);
        List<Route> routeList = navigationService.navigate(new Coordinated(1,5), new Coordinated(5,24));
        routeList.forEach(System.out::println);
    }
}
