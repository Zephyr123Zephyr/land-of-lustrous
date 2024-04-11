package org.example.landoflustrous.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverScene {
    private Stage stage;
    private Scene scene;

    public GameOverScene(Stage stage) {
        this.stage = stage;
        initializeScene();
    }

    private void initializeScene() {
        Label gameOverLabel = new Label("Game Over");
        Label educationalLabel = new Label("Educational pop up: Placeholder for bad choices explanation.");
        Button buttonBackHome = new Button("Home");
        Button redoButton = new Button("Redo Level");

        // Placeholder action for the redo button
        redoButton.setOnAction(event -> {
            // Logic to reset the game or go back to a previous scene
        });



        VBox layout = new VBox(10);
        layout.getChildren().addAll(gameOverLabel, educationalLabel, redoButton);
        scene = new Scene(layout, 500, 500);
//        String css = this.getClass().getResource("src/main/java/resources/GameOverScene.css").toExternalForm(); // Ensure the path matches the actual CSS file location
//        scene.getStylesheets().add(css);
    }
    public Scene getScene() { // Here is the getScene method that returns the Scene object
        return scene;
    }
    public void showScene() {
        stage.setScene(scene);
        stage.show();
    }
}