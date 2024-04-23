package org.example.landoflustrous.controller;

import javafx.stage.Stage;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;
import org.example.landoflustrous.view.LevelSelectionScene;

public class GameStartController extends GameController {

    private Stage stage;

    public GameStartController(Stage stage) {
        this.stage = stage;

        // 此处设置stage的尺寸！！！！！
        this.stage.setWidth(1176); // 例如设置宽度为800像素
        this.stage.setHeight(700); // 例如设置高度为600像素
    }


    public void handlePlay(ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        LevelSelectionScene levelSelection = new LevelSelectionScene();
        stage.setScene(levelSelection.createLevelSelectionScene(stage, scoreCalculator, timeLifeCalculator));

    }

}
