package org.example.landoflustrous.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//加载游戏地图和公共交通线路的信息，并提供方法用于查询地图和交通线路的相关信息。
public class GameMap {

    private final List<List<Tile>> tileGrid;
    private final Set<Station> bikeStations;
    private final List<BusLine> busLines;
    private final List<RailLine> railLines;

    public GameMap(String filePath, List<String> railLineFilePaths, List<String> busLineFilePaths) throws IOException {
        this.tileGrid = new ArrayList<>();
        this.bikeStations = new HashSet<>();
        this.busLines = new ArrayList<>();
        this.railLines = new ArrayList<>();

        parseTileGrid(filePath);
        parseRailLines(railLineFilePaths);
        parseBusLines(busLineFilePaths);
    }

    // TileGrid is transposed! Use getTile(x, y) or getTile(coord) to get the tile!
    public Tile getTile(int x, int y) {
        return tileGrid.get(y).get(x);
    }

    public Tile getTile(Coordinated coordinated) {
        return tileGrid.get(coordinated.y).get(coordinated.x);
    }

    public int getWidth() {
        return tileGrid.getFirst().size();
    }

    public int getHeight() {
        return tileGrid.size();
    }

    public Set<Station> getBikeStations() {
        return bikeStations;
    }

    public List<BusLine> getBusLines() {
        return busLines;
    }

    public List<Station> getBusStations() {
        return busLines.stream().map(PublicTransLine::getStations).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<RailLine> getRailLines() {
        return railLines;
    }

    public List<Station> getRailStations() {
        return railLines.stream().map(PublicTransLine::getStations).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Station> getPublicStations() {
        List<Station> stations = new ArrayList<>(getBusStations());
        stations.addAll(getRailStations());
        return stations;
    }

    private void parseTileGrid(String filePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new FileNotFoundException("Cannot find resource " + filePath);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                int y = 0;
                while ((line = br.readLine()) != null) {
                    List<Tile> row = new ArrayList<>();
                    for (int x = 0; x < line.length(); x++) {
                        int index = Character.getNumericValue(line.charAt(x));
                        Tile tile = Tile.createTile(index, x, y);
                        row.add(tile);
                        if (index == 7) {
                            bikeStations.add(new Station(x, y, "CityBikes"));
                        }
                    }
                    tileGrid.add(row);
                    y++;
                }
            }
        }
    }


    private void parseBusLines(List<String> filePaths) throws IOException {
        int id = 0;
        for (String filePath : filePaths) {
            BusLine busLine = new BusLine(id++);
            parseTransLine(filePath, busLine);
            busLines.add(busLine);
        }
    }

    private void parseRailLines(List<String> filePaths) throws IOException {
        int id = 0;
        for (String filePath : filePaths) {
            RailLine railLine = new RailLine(id++);
            parseTransLine(filePath, railLine);
            railLines.add(railLine);
        }
    }

    private void parseTransLine(String filePath, PublicTransLine line) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new FileNotFoundException("Cannot find resource " + filePath);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String lineContent;
                int y = 0;
                while ((lineContent = br.readLine()) != null) {
                    for (int x = 0; x < lineContent.length(); x++) {
                        int index = Character.getNumericValue(lineContent.charAt(x));
                        if (index != 0) {
                            line.addTile(getTile(x, y));
                            if (index == 8 || index == 9) {
                                line.getStations().add(new Station(x, y, line.getLineCode()));
                            }
                        }
                    }
                    y++;
                }
            }
        }
    }


    public void print() {
        for (List<Tile> row : tileGrid) {
            for (Tile tile : row) {
                if (tile.isForbidden) {
                    System.out.print("X  ");
                } else if (tile.isRail) {
                    System.out.print("R  ");
                } else if (tile.isRoad) {
                    System.out.print("B  ");
                } else {
                    System.out.print(".  ");
                }
            }
            System.out.println();
        }
    }

}
