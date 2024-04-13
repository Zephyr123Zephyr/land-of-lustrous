package org.example.landoflustrous;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.landoflustrous.model.Gem;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.*;
import org.example.landoflustrous.view.GameStartScene;

import java.util.ArrayList;
import java.util.List;

public class GameApplication extends Application {

    private PlayerCharacter player;
    private Gem gem;
    private Route route;

    private ScoreCalculator scoreCalculator = new ScoreCalculator();


    @Override
    public void start(Stage primaryStage) {
        GameStartScene gameStartScene = new GameStartScene();
        primaryStage.setScene(gameStartScene.createStartScene(primaryStage,scoreCalculator));
        primaryStage.setTitle("Game Demo");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //初始Player
    private void initPlayer(){
        CarbonFootprint carbonFootprint = new CarbonFootprint(100);

        PlayerCharacter player = new PlayerCharacter("PlayerOne", 0, 0, 100,
                null, carbonFootprint, 1);
    }

    //生成Gem
    private void generateGem(){
        gem = new Gem();
    }

    //根据player位置和Gem位置生成route
    private void createRoute(){
        route = new Route(player.getX(), player.getY(), gem.getX(), gem.getY());
    }

    private void gameLoop(Stage primaryStage) {
        Level level = new Level(primaryStage, player, 3, 2);
        level.start();

    }

    private void endGame(Stage primaryStage){

    }


    public static void main(String[] args) {
        launch(args);
    }

}



