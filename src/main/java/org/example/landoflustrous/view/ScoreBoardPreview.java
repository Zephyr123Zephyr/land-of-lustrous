//package org.example.landoflustrous.view;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//import org.example.landoflustrous.controller.ScoreBoardController;
//
//public class ScoreBoardPreview extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // 现在将primaryStage传递给ScoreBoardController的构造器
//        ScoreBoardController controller = new ScoreBoardController(primaryStage);
//
//        // 传入一些示例数据以初始化界面
//        ScoreBoardScene scene = new ScoreBoardScene(primaryStage, 100, 50, 200, 120, controller);
//        primaryStage.setScene(scene.getScene());
//        primaryStage.setTitle("Score Board Preview");
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
