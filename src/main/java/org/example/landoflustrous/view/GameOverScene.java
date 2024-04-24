package org.example.landoflustrous.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.landoflustrous.controller.GameOverController;
import org.example.landoflustrous.model.Popup;

//All styling matching that of scoreboard scene
public class GameOverScene {
    private VBox root; // 使用VBox作为根容器
    private Scene scene;
    private Stage stage;
    private GameOverController controller;

    public GameOverScene(Stage stage, int carbon, int gemNum, int gemScore, int time, GameOverController controller) {
        this.stage = stage;
        this.controller = controller;


        //---------------------一个vbox放各种分数-------------------------------
        VBox textContainer = new VBox(20); // 10是元素之间的间距
        textContainer.setAlignment(Pos.CENTER); // 设置VBox居中对齐
        textContainer.getStyleClass().add("text-container"); // CSS类名

        Text title = new Text("GAME OVER");
        title.getStyleClass().add("gameover_title");

        Text textGem = new Text("GEM NUMBER: " + gemNum);
        textGem.getStyleClass().add("score");

        Text textCarbon = new Text("C-EMISSION: " + carbon);
        textCarbon.getStyleClass().add("score");

        Text textTime = new Text("TIME COST: " + time);
        textTime.getStyleClass().add("score");

        Text textTotalScore = new Text("GEM SCORE: " + gemScore);
        textTotalScore.getStyleClass().add("score");

        textContainer.getChildren().addAll(textGem, textCarbon, textTime, textTotalScore);

        Rectangle rectangle = new Rectangle(600, 230);
        rectangle.getStyleClass().add("rectangle");

        // 使用StackPane来允许背景和文本层叠
        StackPane scores = new StackPane();
        scores.getChildren().addAll(rectangle, textContainer);

        //------------------------------一个hbox放popup-----------------------
        HBox hbox_popup = new HBox(0);
        hbox_popup.setAlignment(Pos.CENTER);

        Popup popup = new Popup();
        Text ecoTip = new Text(popup.getRandomEcoTip());

        ecoTip.setWrappingWidth(330);
        ecoTip.getStyleClass().add("eco_tip");

        // 随机图片
        ImageView imageView = controller.getRandomImageView();
        imageView.setFitWidth(220);
        imageView.setFitHeight(220);
        imageView.setPreserveRatio(true);

        // 将ImageView和Text添加到HBox
        hbox_popup.getChildren().addAll(imageView, ecoTip);
        Rectangle rectangle2 = new Rectangle(600, 200);
        rectangle2.getStyleClass().add("rectangle");

        StackPane stackpan_popup = new StackPane();
        stackpan_popup.getChildren().addAll(rectangle2, hbox_popup);

        //--------------------------一个hbox放两个按钮----------------------------
        HBox hbox_twobtn = new HBox(50);
        hbox_twobtn.setAlignment(Pos.CENTER);

        Button btn1 = new Button("");
        btn1.getStyleClass().add("play_again");
        btn1.setOnAction(controller::goToNextLevel);


        Button btn3 = new Button("");
        btn3.getStyleClass().add("exit");
        btn3.setOnAction(controller::handleExit);


        hbox_twobtn.getChildren().addAll(btn1, btn3);


        // -------------------------VBox组织子元素-------------------
        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("gameover_root");
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // 引入CSS样式

        root.setStyle("-fx-background-color:rgb(243,243,243);");
        root.getChildren().addAll(title, scores, stackpan_popup, hbox_twobtn);

        this.scene = new Scene(root, 1300, 800);


    }

    public Scene getScene() {
        return this.scene;
    }
}