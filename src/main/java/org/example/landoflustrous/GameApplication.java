package org.example.landoflustrous;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.landoflustrous.view.GameStartScene;

public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        //  1️⃣此处创建每个场景共用的stage，给stage设置为GameStartScene
        GameStartScene gameStartScene = new GameStartScene();

        primaryStage.setScene(gameStartScene.createStartScene(primaryStage));
        primaryStage.setTitle("LAND OF LUSTROUS");
        primaryStage.setResizable(false);
        primaryStage.setWidth(1010);
        primaryStage.setHeight(680);
//        primaryStage.setFullScreen(true);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}