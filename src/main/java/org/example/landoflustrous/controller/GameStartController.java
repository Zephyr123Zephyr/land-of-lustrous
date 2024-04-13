package org.example.landoflustrous.controller;

import javafx.stage.Stage;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;
import org.example.landoflustrous.view.LevelSelectionScene;

public class GameStartController {

    private Stage stage;

    public GameStartController(Stage stage) {
        this.stage = stage;
    }

    public void handlePlay(ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        LevelSelectionScene levelSelection = new LevelSelectionScene();
        stage.setScene(levelSelection.createLevelSelectionScene(stage,scoreCalculator,timeLifeCalculator));
    }

    public void handleExit() {
        System.exit(0);
    }
}
