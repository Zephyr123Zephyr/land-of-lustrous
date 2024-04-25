package org.example.landoflustrous.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.landoflustrous.controller.StartController;

public class GameStartScene {
    private StartController controller;
    private TextField nameInput;

    public Scene createStartScene(Stage stage) {

        controller = new StartController(stage);

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/start_bg.png"));
        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        // Create a BackgroundImage object
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        Label intro = new Label("\n" +
                "In a realm where every whisper and stride breathes life into the very carbon that threatens our existence," + "the Earth teeters on the edge of destiny. " +
                "Embark upon an epic quest in the Land of Lustrous. " +
                "Here, gather the sacred gems of purification and emerge as the savior our world desperately seeks. " +
                "Embrace this call to adventure, and forge a legacy of salvation! ");

        intro.getStyleClass().add("intro");
        intro.setWrapText(true); // 确保文本在Label宽度不够时能够换行
        intro.setMaxWidth(750);

        Label prompt = new Label("                                                      Enter Your Name");
        prompt.setAlignment(Pos.TOP_LEFT);
        prompt.setTextAlignment(TextAlignment.LEFT);
        prompt.setWrapText(true); // 确保文本在Label宽度不够时能够换行
        prompt.setMaxWidth(750);


        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Your Name");
        nameInput.setMaxWidth(200);


        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(120, 0, 0, 0));
        vbox.setAlignment(Pos.CENTER); // Ensures that VBox contents are centered

        Button buttonNewGame = new Button("");
        buttonNewGame.getStyleClass().add("btn_play");

        //2️⃣ 此处点击后调用controller的方法，进入选关场景
        buttonNewGame.setOnAction(e -> controller.handlePlay(nameInput.getText()));

        Button buttonExit = new Button("");
        buttonExit.getStyleClass().add("btn_exit");

        //绑定退出事件
        buttonExit.setOnAction(controller::handleExit);

        vbox.getChildren().addAll(intro, prompt, nameInput, buttonNewGame, buttonExit);

        StackPane root = new StackPane(); // Using StackPane to center VBox

        root.getChildren().addAll(vbox);
        // Set the background of the root StackPane
        root.setBackground(new Background(background));


        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        return scene;
    }

}