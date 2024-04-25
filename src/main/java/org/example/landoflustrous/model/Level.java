//package org.example.landoflustrous.model;
//
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class Level {
//    private int totalGems;
//    private int failedGems;
//    private int requiredSuccesses;
//    private Stage stage;
//    private PlayerCharacter player;
//    private OptionBoard optionBoard;
//
//    public Level(Stage stage, PlayerCharacter player, int totalGems, int requiredSuccesses) {
//        this.stage = stage;
//        this.player = player;
//        this.totalGems = totalGems;
//        this.requiredSuccesses = requiredSuccesses;
//        this.failedGems = 0;
//    }
//
//    public void start() {
//        for (int i = 0; i < totalGems; i++) {
//            Gem gem = new Gem(); // Gem 随机生成位置
////            Route route = new Route(player.getX(), player.getY(), gem.getX(), gem.getY());
//            optionBoard = new OptionBoard();
////          optionBoard = new OptionBoard(1, gem.getGemID(), 10, 10, false);
////            VBox board = optionBoard.createOptionBoard(route);
////
////            stage.setScene(new Scene(board));
//            stage.show();
//
//            // 等待玩家做出选择或时间结束
//            while (optionBoard.isVisible()) {
//                // 在这里可以用一个锁或其他同步机制等待选择或倒计时结束
//            }
//
//            if (!optionBoard.isSuccessFlg()) {
//                failedGems++;
//                if (failedGems >= totalGems - requiredSuccesses) {
//                    endLevel(false); // 失败
//                    return;
//                }
//            }
//        }
//
//        endLevel(true); // 成功
//    }
//
//    private void endLevel(boolean success) {
//        if (success) {
//            System.out.println("Level Passed!");
//            // 进入下一关或重置游戏等
//        } else {
//            System.out.println("Level Failed!");
//            // 显示失败信息，提供重试或退出选项
//        }
//    }
//}
//
//
//
//
