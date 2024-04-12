package org.example.landoflustrous;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.landoflustrous.model.Gem;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.view.GameStartScene;

import java.util.ArrayList;
import java.util.List;

public class GameApplication extends Application {
    private ScoreCalculator scoreCalculator = new ScoreCalculator();
    public static List<Gem> GemList;

    @Override
    public void start(Stage primaryStage) {
        GameStartScene gameStartScene = new GameStartScene();
        primaryStage.setScene(gameStartScene.createStartScene(primaryStage,scoreCalculator));
        primaryStage.setTitle("Game Demo");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}



