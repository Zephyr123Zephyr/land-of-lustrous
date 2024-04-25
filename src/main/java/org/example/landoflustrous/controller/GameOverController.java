package org.example.landoflustrous.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.landoflustrous.view.MapViewerScene;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameOverController extends GameController {
    private Stage stage;

    public GameOverController(Stage stage) {
        super();
        this.stage = stage;
    }

    public void openMapPage(String LevelIdentifier, String playerName) {
        try {
            MapViewerScene mapViewer = new MapViewerScene(stage, LevelIdentifier, playerName);
            stage.setScene(mapViewer.getScene());
            stage.show();
        } catch (IOException e) {
            // Handle exception here
            e.printStackTrace(); // For example, print the stack trace to the console.
            // You could also log the error or show an error message to the user.
        }
    }

//    public void goToNextLevel(ActionEvent event) {
//        LevelSelectionScene levelSelectionScene = new LevelSelectionScene();
//        stage.setScene(levelSelectionScene.createLevelSelectionScene(stage, new ScoreCalculator(), new TimeLifeCalculator(1000)));
//        stage.show();
//    }

    public ImageView getRandomImageView() {
        List<String> imagePaths = Arrays.asList("/images/earth.png", "/images/tree2.png", "/images/water.png", "/images/dophine.png");
        Random random = new Random();
        String path = imagePaths.get(random.nextInt(imagePaths.size()));
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            throw new RuntimeException("Image not found at path: " + path);
        }
        Image image = new Image(stream);
        return new ImageView(image);
    }

//    public void openMapPage(ActionEvent actionEvent) {
//        try {
//            MapViewerScene mapViewer = new MapViewerScene(stage, LevelIdentifier, playerName);
//            stage.setScene(mapViewer.getScene());
//            stage.show();
//        } catch (IOException e) {
//            // Handle exception here
//            e.printStackTrace(); // For example, print the stack trace to the console.
//            // You could also log the error or show an error message to the user.
//        }
//    }
}
