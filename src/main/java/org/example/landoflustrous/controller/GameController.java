package org.example.landoflustrous.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.example.landoflustrous.view.MapViewerScene;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    //场景通用的保存游戏记录到txt文件
    public void saveGameRecord(String playerName, int gemNum, String levelId) throws IOException {
        // 根据 LevelId 选择文件名
        String fileName;
        if ("Level 1".equals(levelId)) {
            fileName = "game_records1.txt";
        } else if ("Level 2".equals(levelId)) {
            fileName = "game_records2.txt";
        } else {
            // 如果 LevelId 不是 Level 1 或 Level 2，可以选择抛出异常或者保存到默认文件
            fileName = "game_records_other.txt";
        }

        // 构建路径
        Path path = Paths.get("src/main/resources", fileName);

        // 使用 try-with-resources 语句确保 PrintWriter 能正确关闭
        try (PrintWriter out = new PrintWriter(new FileWriter(path.toString(), true))) {
            System.out.println("保存记录到文件：" + fileName);
            // 将玩家名字、宝石数量和等级ID存储到文件
            out.println(levelId + "," + gemNum + "," + playerName);
        }
    }


    public List<String[]> readFileByLevelId(String levelId) throws IOException {
        String fileName = null;
        if (levelId == "Level 1") {
            fileName = "game_records1.txt";
        }
        if (levelId == "Level 2") {
            fileName = "game_records2.txt";
        }
        
        Path path = Paths.get("src/main/resources", fileName);
        List<String[]> content = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.add(line.split(","));
            }
        }

        return content;
    }


//    public void openMapPage(String levelIdentifier, String playerName) throws IOException {
//        MapViewerScene mapViewer = new MapViewerScene(stage, levelIdentifier, playerName);
//        stage.setScene(mapViewer.getScene());
//        stage.show();
//    }


//    public void openMapPage(String levelIdentifier, String playerName) throws IOException {
//        MapViewerScene mapViewer = new MapViewerScene(stage, levelIdentifier, playerName);
//        stage.setScene(mapViewer.getScene());
//        stage.show();
//    }


//    public void goToLevelSelection() {
//        LevelSelectionScene levelSelectionScene = new LevelSelectionScene();
//        Scene levelSelectionSceneView = levelSelectionScene.createLevelSelectionScene(stage, new ScoreCalculator(), new TimeLifeCalculator(100000));
//        stage.setScene(levelSelectionSceneView);
//        stage.show();
//    }
//
//    public void goToScoreBoard() {
//        int carbon = mapViewerScene.getCurLevelCarbonPoint();
//        int gemNum = mapViewerScene.getCurLevelGemNum();
//        int gemScore = mapViewerScene.getCurLevelGemPoint();
//        int time = mapViewerScene.getCurLevelTimeUse();
//
//        // 创建ScoreBoardController的实例
//        ScoreBoardController scoreBoardController = new ScoreBoardController(stage);
//
//        // 现在构造器参数齐全
//        ScoreBoardScene scoreBoardScene = new ScoreBoardScene(stage, carbon, gemNum, gemScore, time, scoreBoardController);
//        stage.setScene(scoreBoardScene.getScene());
//        stage.show();
//    }
//
//    public void goToGameOver() {
//        int carbon = mapViewerScene.getCurLevelCarbonPoint();
//        int gemNum = mapViewerScene.getCurLevelGemNum();
//        int gemScore = mapViewerScene.getCurLevelGemPoint();
//        int time = mapViewerScene.getCurLevelTimeUse();
//
//        GameOverController controller = new GameOverController(stage);
//
//        GameOverScene gameover = new GameOverScene(stage, carbon, gemNum, gemScore, time,controller);
//        stage.setScene(gameover.getScene());
//        stage.show();
//    }


}