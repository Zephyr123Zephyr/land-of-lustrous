package org.example.landoflustrous.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.landoflustrous.controller.GameStartController;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;

public class GameStartScene {
    private GameStartController controller;

    public Scene createStartScene(Stage stage, ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        controller = new GameStartController(stage);


        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/map_level1.png"));
        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        // Create a BackgroundImage object
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);


        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/title.png")));
        imageView.setId("title"); // 设置ID以便在CSS中引用
        imageView.setFitWidth(300);
        imageView.setFitHeight(30);


        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Your Name"); // Placeholder text
        nameInput.setMaxWidth(200); // Set a max width for the text field


        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER); // Ensures that VBox contents are centered

        Button buttonNewGame = new Button("");
        buttonNewGame.getStyleClass().add("btn_play");
        buttonNewGame.setOnAction(e -> controller.handlePlay(scoreCalculator, timeLifeCalculator));

        Button buttonExit = new Button("");
        buttonExit.getStyleClass().add("btn_exit");
        buttonExit.setOnAction(e -> controller.handleExit());
        vbox.getChildren().addAll(nameInput, buttonNewGame, buttonExit);

        StackPane root = new StackPane(); // Using StackPane to center VBox

        root.getChildren().addAll(imageView, vbox);
        // Set the background of the root StackPane
        root.setBackground(new Background(background));

        // Set label at the top and VBox centered
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);


        Scene scene = new Scene(root, 1500, 900);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

//         在这里设置全屏
//        stage.setFullScreen(true);

        return scene;
    }
}
