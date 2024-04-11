package org.example.landoflustrous.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.landoflustrous.model.*;

public class MapViewerScene {

    private GameMap gameMap;
    private static final int TILE_SIZE = 20;
    private ImageView playerSprite;

    // A mapping from level identifiers to file paths
    private static final Map<String, Map<String, Object>> levelPathMapping = new HashMap<>();

    //TODO: MOVE TO CONFIG AFTER MAKING SURE IT RUNS
    static {
        // Initialize the mapping of levels to files
        levelPathMapping.put("Level 1", Map.of(
                "map", "/maps/map1/map.txt", // Single string for the map file path
                "rail", List.of("/maps/map1/rail.txt"), // List for rail file paths
                "bus", List.of("/maps/map1/bus1.txt", "/maps/map1/bus2.txt"))); // List for bus file paths
//        levelPathMapping.put("Level 2", Map.of(
//                "map", "LocalJavaGame/MapEdit/level2/map.txt",
//                "rail", List.of("LocalJavaGame/MapEdit/leve2/rail.txt"),
//                "bus", List.of("MapEdit/leve2/bus.txt")));
        // Add mappings for other levels as needed
    }

    public MapViewerScene(String levelIdentifier) {
        Map<String, Object> paths = levelPathMapping.get(levelIdentifier);
        if (paths != null) {
            try {
                // Cast the objects back to their expected types
                String mapPath = (String) paths.get("map");
                List<String> railPaths = (List<String>) paths.get("rail");
                List<String> busPaths = (List<String>) paths.get("bus");

                // Pass the extracted paths to the GameMap constructor
                this.gameMap = new GameMap(mapPath, railPaths, busPaths);
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Consider a better error handling strategy
            } catch (ClassCastException e) {
                e.printStackTrace(); // This will catch any cast errors in case of a wrong type
            }
        } else {
            throw new IllegalArgumentException("Invalid level identifier: " + levelIdentifier);
        }
    }

    //    public Scene createMapScene() {
//        Pane root = new Pane();
//        drawMap(root);
//        // TODO: this is for testing purpose. Move player instantiation to controller later
//        // Instantiate a player character for testing
//        PlayerCharacter testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1); // Adjust the parameters as needed
//
//        // Add the player character to the scene
//        addPlayerCharacter(root, testPlayer);
//        playerSprite.toFront();
//        playerSprite.setOpacity(1.0);
//    playerSprite.setVisible(true);
//        return new Scene(root, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE);
//    }
    public void createMapScene(Stage stage) {
        Pane root = new Pane();

        drawMap(root);
        // TODO: this is for testing purpose. Move player instantiation to controller later
        // Instantiate a player character for testing
        PlayerCharacter testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1); // Adjust the parameters as needed

        // Add the player character to the scene
        addPlayerCharacter(root, testPlayer);


        playerSprite.toFront();
        playerSprite.setOpacity(1.0);
        playerSprite.setVisible(true);

        Button buttonMove = new Button("OptionBoard Demo");
        buttonMove.setLayoutY(100);
        root.getChildren().add(buttonMove);

        buttonMove.setOnAction(event -> {
            ////开始模拟走路
            List<Tile> tileList = new LinkedList<Tile>();
            tileList.add(new Tile(100, 100, false, false, false));
            tileList.add(new Tile(200, 200, false, false, false));
            tileList.add(new Tile(300, 300, false, false, false));
            tileList.add(new Tile(400, 400, false, false, false));
            tileList.add(new Tile(500, 500, false, false, false));

            Path path = new Path(TrafficType.BIKE, tileList);//自己生成的
            ////需要生成当前地图的Route实例-保存PathList
            //然后绑定表单控件的id来选择
            createMapScene_AfterChooseOption(stage, path);
        });

        stage.setScene(new Scene(root, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE));
        stage.show();

    }

    public void createMapScene_AfterChooseOption(Stage stage, Path path) {
        Pane root = new Pane();
        drawMap(root);
        // TODO: this is for testing purpose. Move player instantiation to controller later
        // Instantiate a player character for testing
        PlayerCharacter testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1); // Adjust the parameters as needed

        // Add the player character to the scene
        addPlayerCharacter(root, testPlayer);
        playerSprite.toFront();
        playerSprite.setOpacity(1.0);
        playerSprite.setVisible(true);
        stage.setScene(new Scene(root, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE));
        final int[] changeCount = {0};
        int maxChanges = 3;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (changeCount[0] < path.getTileList().size()) {
                        double newX = path.getTileList().get(changeCount[0]).x; // 增加水平方向的坐标
                        double newY = path.getTileList().get(changeCount[0]).y; // 增加垂直方向的坐标
                        //循环条件可以改成便利Path中的Tile的X Y坐标 当前时间间隔是0.5秒
                        playerSprite.setLayoutX(newX);
                        playerSprite.setLayoutY(newY);
                        changeCount[0]++;
                    } else {
                        new Timeline().stop();
                    }
                })
        );


        timeline.setCycleCount(Timeline.INDEFINITE); // 设置为无限循环
        timeline.play(); // 启动时间轴
        stage.show();

    }

    private void drawMap(Pane root) {
        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                Tile tile = gameMap.getTile(x, y);
                Rectangle rect = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                rect.setFill(getTileColor(tile));
                root.getChildren().add(rect);
            }
        }
    }

    private Color getTileColor(Tile tile) {
        // Replace with your actual tile property checks and color assignments
        if (tile.isRail) {
            return Color.GRAY;
        } else if (tile.isRoad) {
            return Color.YELLOW;
        } else if (tile.isForbidden) {
            return Color.RED;
        } else {
            return Color.GREEN;
        }
    }

    public void addPlayerCharacter(Pane root, PlayerCharacter player) {
//        Image image = new Image("/gui/img/playercharacter.png");
//        playerSprite = new ImageView(image);
//        updatePlayerPosition(player.getX(), player.getY());
//        root.getChildren().add(playerSprite);
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/gem.png"));
            if (image.isError()) {
                throw new RuntimeException("Error loading player image.");
            }

            playerSprite = new ImageView(image);
            playerSprite.setFitWidth(TILE_SIZE);  // Set the image view size to fit the tile size
            playerSprite.setFitHeight(TILE_SIZE);
            playerSprite.setX(player.getX() * TILE_SIZE);  // Position the sprite based on the player's grid coordinates
            playerSprite.setY(player.getY() * TILE_SIZE);
            root.getChildren().add(playerSprite);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add player character.", e);
        }
    }

    public void updatePlayerPosition(int x, int y) {
        if (playerSprite != null) {
            playerSprite.setX(x * TILE_SIZE);
            playerSprite.setY(y * TILE_SIZE);
        }
    }

//    public Scene getScene() {
//        return new Scene(root, 600, 400); // Example dimensions
//    }

}