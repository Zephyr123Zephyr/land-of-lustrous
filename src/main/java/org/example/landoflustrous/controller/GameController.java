package org.example.landoflustrous.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;
import org.example.landoflustrous.view.GameOverScene;
import org.example.landoflustrous.view.LevelSelectionScene;
import org.example.landoflustrous.view.MapViewerScene;
import org.example.landoflustrous.view.ScoreBoardScene;

public class GameController {
    private Stage stage;
    private MapViewerScene mapViewerScene;

    public GameController(Stage stage, MapViewerScene mapViewerScene) {
        this.stage = stage;
        this.mapViewerScene = mapViewerScene;
    }

    public void goToLevelSelection() {
        LevelSelectionScene levelSelectionScene = new LevelSelectionScene();
        Scene levelSelectionSceneView = levelSelectionScene.createLevelSelectionScene(stage, new ScoreCalculator(), new TimeLifeCalculator(100000));
        stage.setScene(levelSelectionSceneView);
        stage.show();
    }

    public void goToScoreBoard() {
        int carbon = mapViewerScene.getCurLevelCarbonPoint();
        int gemNum = mapViewerScene.getCurLevelGemNum();
        int gemScore = mapViewerScene.getCurLevelGemPoint();
        int time = mapViewerScene.getCurLevelTimeUse();

        ScoreBoardScene scoreBoardScene = new ScoreBoardScene(stage, carbon, gemNum, gemScore, time);
        stage.setScene(scoreBoardScene.getScene());
        stage.show();
    }

    public void goToGameOver() {
        try {
            GameOverScene gameOverScene = new GameOverScene(stage, new ScoreCalculator(), new TimeLifeCalculator(1000));
            stage.setScene(gameOverScene.getScene());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();  // This will print the stack trace to the console
        }
    }
}