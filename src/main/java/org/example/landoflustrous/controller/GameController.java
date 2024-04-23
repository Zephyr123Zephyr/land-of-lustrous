package org.example.landoflustrous.controller;

import javafx.event.ActionEvent;
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


    public GameController() {
    }


    public GameController(Stage stage, MapViewerScene mapViewerScene) {
        this.stage = stage;
        this.mapViewerScene = mapViewerScene;
    }


    //各个场景通用的结束游戏方法
    public void handleExit(ActionEvent event) {
        System.exit(0);
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

        // 创建ScoreBoardController的实例
        ScoreBoardController scoreBoardController = new ScoreBoardController(stage);

        // 现在构造器参数齐全
        ScoreBoardScene scoreBoardScene = new ScoreBoardScene(stage, carbon, gemNum, gemScore, time, scoreBoardController);
        stage.setScene(scoreBoardScene.getScene());
        stage.show();
    }


    public void goToGameOver() {
        int carbon = mapViewerScene.getCurLevelCarbonPoint();
        int gemNum = mapViewerScene.getCurLevelGemNum();
        int gemScore = mapViewerScene.getCurLevelGemPoint();
        int time = mapViewerScene.getCurLevelTimeUse();

        GameOverScene gameover = new GameOverScene(stage, carbon, gemNum, gemScore, time);
        stage.setScene(gameover.getScene());
        stage.show();
    }


}