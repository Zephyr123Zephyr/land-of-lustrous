package org.example.landoflustrous.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.landoflustrous.GameApplication;
import org.example.landoflustrous.model.Gem;
import org.example.landoflustrous.model.ScoreCalculator;
import org.example.landoflustrous.model.TimeLifeCalculator;

import java.awt.*;

public class GameOverScene {
    private Stage stage;
    private Scene scene;

    public GameOverScene(Stage stage, ScoreCalculator scoreCalculator,  TimeLifeCalculator timeLifeCalculator) {
        this.stage = stage;
        initializeScene(scoreCalculator,timeLifeCalculator);
    }

    private void initializeScene(ScoreCalculator scoreCalculator,  TimeLifeCalculator timeLifeCalculator) {
//        Label gameOverLabel = new Label("Game Over");
//        Label educationalLabel = new Label("Educational pop up: Placeholder for bad choices explanation.");
//        Button buttonBackHome = new Button("Home");
//        Button redoButton = new Button("Redo Level");
//
//        // Placeholder action for the redo button
//        redoButton.setOnAction(event -> {
//            // Logic to reset the game or go back to a previous scene
//        });
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(gameOverLabel, educationalLabel, redoButton);
        // 创建标签和按钮
        Label resultHeadLabel = new Label("Your Game Result");
        Label carbonLabel = new Label("Total Carbon Point: "+scoreCalculator.getTotalCarbonPoint());
        Label gemLabel = new Label("Total Carbon Point: "+scoreCalculator.getTotalGemPoint());
        Label gemNumLabel = new Label("Num of collected gem: "+scoreCalculator.getOptionBoardList().size());
        Button buttonExit = new Button("Exit");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER); // 设置布局中心对齐
        layout.setPadding(new Insets(20)); // 设置内边距



        DropShadow dropShadow = new DropShadow();
        resultHeadLabel.setEffect(dropShadow);
        carbonLabel.setEffect(dropShadow);
        gemLabel.setEffect(dropShadow);
        gemNumLabel.setEffect(dropShadow);

        buttonExit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        buttonExit.setPrefWidth(120);
        buttonExit.setOnAction(event -> {
            System.exit(0);
        });




        layout.getChildren().addAll(resultHeadLabel, carbonLabel, gemLabel,gemNumLabel,buttonExit);
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