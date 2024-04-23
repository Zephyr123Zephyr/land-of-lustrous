package org.example.landoflustrous.controller;

import javafx.stage.Stage;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;
import org.example.landoflustrous.view.GameStartScene;
import org.example.landoflustrous.view.MapViewerScene;

public class LevelSelectionController extends GameController {

    private final Stage stage;
    private final ScoreCalculator scoreCalculator;
    private final TimeLifeCalculator timeLifeCalculator;

    public LevelSelectionController(Stage stage, ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        this.stage = stage;
        this.scoreCalculator = scoreCalculator;
        this.timeLifeCalculator = timeLifeCalculator;
    }

    public void openMapPage(String levelIdentifier) {
        MapViewerScene mapViewer = new MapViewerScene(levelIdentifier);
        mapViewer.createMapScene(stage, scoreCalculator, timeLifeCalculator);
    }

    public void returnToMainMenu() {
        GameStartScene startScene = new GameStartScene();
        stage.setScene(startScene.createStartScene(stage, scoreCalculator, timeLifeCalculator));
    }
}
