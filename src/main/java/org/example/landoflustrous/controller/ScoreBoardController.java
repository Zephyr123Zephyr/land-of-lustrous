package org.example.landoflustrous.controller;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;
import org.example.landoflustrous.view.LevelSelectionScene;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ScoreBoardController extends Controller {
    private Stage stage;

    public ScoreBoardController(Stage stage) {
        this.stage = stage;
    }

    public void goToNextLevel(ActionEvent event) {
        LevelSelectionScene levelSelectionScene = new LevelSelectionScene();
        stage.setScene(levelSelectionScene.createLevelSelectionScene(stage, new ScoreCalculator(), new TimeLifeCalculator(1000)));
        stage.show();
    }

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

}
