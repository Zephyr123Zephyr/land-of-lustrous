package org.example.landoflustrous.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.landoflustrous.controller.GameStartController;

public class GameStartScene {
    private GameStartController controller;

    public Scene createStartScene(Stage stage) {
        controller = new GameStartController(stage);

        Label label = new Label("Lust of Lustrous");
        Button buttonNewGame = new Button("Play");
        buttonNewGame.setOnAction(e -> controller.handlePlay());

        Button buttonExit = new Button("Exit");
        buttonExit.setOnAction(e -> controller.handleExit());

        label.setLayoutX(200);
        label.setLayoutY(100);
        label.setStyle("-fx-border-color: black");
        label.setPrefWidth(100);
        label.setPrefHeight(50);
        label.setAlignment(Pos.CENTER);
//        button style
        buttonNewGame.setLayoutX(225);
        buttonNewGame.setLayoutY(200);
        buttonExit.setLayoutX(225);
        buttonExit.setLayoutY(250);

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(label, buttonNewGame, buttonExit);
        return new Scene(root, 500, 500);
    }
}
