//package org.example.landoflustrous.controller;
//
//import javafx.stage.Stage;
//import org.example.landoflustrous.model.GameMap;
//import org.example.landoflustrous.model.PlayerCharacter;
//import org.example.landoflustrous.service.NavigationService;
//import org.example.landoflustrous.view.MapViewerScene;
//
//import java.io.IOException;
//
//public class MapViewController {
//    private GameMap gameMap;
//    private MapViewerScene mapViewerScene;
//    private PlayerCharacter player;
//    private NavigationService navigationService;
//
//    public MapViewController(String levelIdentifier, Stage stage) throws IOException {
//        this.mapViewerScene = new MapViewerScene(levelIdentifier);
//        this.gameMap = mapViewerScene.getGameMap();
//        this.navigationService = new NavigationService(gameMap);
//        this.player = new PlayerCharacter("TestPlayer", 0, 0, 100, null, null, 1);
//        createMapScene(stage);
//    }
//
//    private void createMapScene(Stage stage) {
//        // Call method from MapViewerScene to create and display the scene
//        mapViewerScene.createMapScene(stage, this);
//    }
//
//    public void navigateToGem() {
//        // Add navigation logic here, using the NavigationService
//    }
//
//    public void updateGameState() {
//        // Implement game state update logic, such as scoring and time management
//    }
//
//    public PlayerCharacter getPlayer() {
//        return this.player;
//    }
//
//    public GameMap getGameMap() {
//        return this.gameMap;
//    }
//
//    // Add other necessary methods to interact with the view or handle game logic
//}
