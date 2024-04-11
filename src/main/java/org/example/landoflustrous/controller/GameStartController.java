package org.example.landoflustrous.controller;

import javafx.stage.Stage;
import org.example.landoflustrous.view.LevelSelectionScene;

public class GameStartController {

    private Stage stage;

    public GameStartController(Stage stage) {
        this.stage = stage;
    }

    public void handlePlay() {
        LevelSelectionScene levelSelection = new LevelSelectionScene();
        stage.setScene(levelSelection.createLevelSelectionScene(stage));
    }

    public void handleExit() {
        System.exit(0);
    }
}
