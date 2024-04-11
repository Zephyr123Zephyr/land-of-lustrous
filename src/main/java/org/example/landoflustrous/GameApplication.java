package org.example.landoflustrous;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.landoflustrous.view.GameStartScene;

public class GameApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameStartScene gameStartScene = new GameStartScene();
        primaryStage.setScene(gameStartScene.createStartScene(primaryStage));
        primaryStage.setTitle("Game Demo");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
