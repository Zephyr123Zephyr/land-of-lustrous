package org.example.landoflustrous.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.landoflustrous.model.Popup;

public class ScoreBoardScene {
    private StackPane root;
    private Scene scene;
    private Popup popup;

    // 主构造器
    public ScoreBoardScene(int carbon, int gemNum, int gemScore, int time) {
        setupScene(carbon, gemNum, gemScore, time);
    }

    // 默认构造器
    public ScoreBoardScene() {
        // 调用主构造器，所有参数设置为默认值0
        this(0, 0, 0, 0);
    }

    private void setupScene(int carbon, int gemNum, int gemScore, int time) {
        Popup popup1 = new Popup();
        root = new StackPane();
        root.setStyle("-fx-background-color:rgb(243,243,243);");

        Button btn = new Button("NEXT LEVEL");
        btn.setBackground(new Background(new BackgroundFill(Color.web("#4CAF50"), null, null)));
        btn.setTextFill(Color.WHITE);
        btn.setPadding(new Insets(10, 20, 10, 20));
        btn.setFont(Font.font("Agency FB", FontWeight.BOLD, 30));

        Text text = new Text("SCORE BOARD");
        text.setFont(Font.font("Agency FB", FontWeight.BOLD, 65));
        text.setFill(Color.DARKGREEN);

        Text text2 = new Text(popup1.getRandomEcoTip());
        text2.setFont(Font.font("Agency FB", 30));
        text2.setFill(Color.DARKGREEN);
        text2.setWrappingWidth(400);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(text2);

        Text text_gem = new Text("GEM NUMBER " + gemNum);
        text_gem.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
        text_gem.setFill(Color.DARKGREEN);

        Text text_carbon = new Text("C-EMISSION " + carbon);
        text_carbon.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
        text_carbon.setFill(Color.DARKGREEN);

        Text text_time = new Text("TIME COST" + time);
        text_time.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
        text_time.setFill(Color.DARKGREEN);

        Text text_totalscore = new Text("GEM SCORE " + gemScore);
        text_totalscore.setFont(Font.font("Agency FB", FontWeight.BOLD, 45));
        text_totalscore.setFill(Color.DARKGOLDENROD);

        Rectangle rectangle = new Rectangle(600, 200, Color.WHITE);
        rectangle.setStrokeWidth(3);
        rectangle.setStroke(Color.DARKGREEN);

        Rectangle rectangle2 = new Rectangle(600, 330, Color.WHITE);
        rectangle2.setStrokeWidth(3);
        rectangle2.setStroke(Color.DARKGREEN);

        Line line = new Line(50, 0, 650, 0);
        line.setStroke(Color.DARKGREEN);
        line.setStrokeWidth(3);

        // Assuming image files are properly placed in the src/ directory
//        Image image = new Image("src/tree.png");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(image.getWidth() / 4);
//        imageView.setFitHeight(image.getHeight() / 4);
//
//        Image image_gem = new Image("src/gem.png");
//        ImageView imageView_gem = new ImageView(image_gem);
//        imageView_gem.setFitWidth(image.getWidth() / 15);
//        imageView_gem.setFitHeight(image.getHeight() / 18);
//
//        Image image_carbon = new Image("src/carbon3.png");
//        ImageView imageView_carbon = new ImageView(image_carbon);
//        imageView_carbon.setFitWidth(image.getWidth() / 14);
//        imageView_carbon.setFitHeight(image.getHeight() / 17);
//
//        Image image_time = new Image("src/time2.png");
//        ImageView imageView_time = new ImageView(image_time);
//        imageView_time.setFitWidth(image.getWidth() / 17);
//        imageView_time.setFitHeight(image.getHeight() / 17);

        // Add all elements to root StackPane
//        root.getChildren().addAll(btn, text, rectangle, imageView, rectangle2, vbox, line,
//                imageView_gem, imageView_carbon, imageView_time,
//                text_gem, text_carbon, text_time, text_totalscore);


        root.getChildren().addAll(btn, text, rectangle, rectangle2, vbox, line,
                text_gem, text_carbon, text_time, text_totalscore);

        // Adjustments and margins as needed
        StackPane.setMargin(text, new Insets(-700, 0, 0, 0));
        StackPane.setMargin(btn, new Insets(0, 0, -700, 0));
        StackPane.setMargin(rectangle, new Insets(350, 0, 0, 0));
//        StackPane.setMargin(imageView, new Insets(360, 400, 20, 0));
        StackPane.setMargin(vbox, new Insets(530, 100, 0, 290));
        StackPane.setMargin(rectangle2, new Insets(-220, 0, 20, 0));
//        StackPane.setMargin(imageView_gem, new Insets(-450, 400, 20, 0));
        StackPane.setMargin(text_gem, new Insets(-450, -100, 20, 0));
//        StackPane.setMargin(imageView_carbon, new Insets(-300, 400, 20, 0));
        StackPane.setMargin(text_carbon, new Insets(-300, -100, 20, 0));
//        StackPane.setMargin(imageView_time, new Insets(-150, 400, 20, 0));
        StackPane.setMargin(text_time, new Insets(-150, -100, 20, 0));
        StackPane.setMargin(text_totalscore, new Insets(10, 0, 0, 0));
        StackPane.setMargin(line, new Insets(-70, 0, 0, 0));

        scene = new Scene(root, 1500, 900);
    }

    public Scene getScene() {
        return this.scene;
    }
}
