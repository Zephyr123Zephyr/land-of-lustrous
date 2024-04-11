package org.example.landoflustrous.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class OptionBoard {
    private int optionBoardID;//自己的ID
    private int gemID;//针对哪个ID的宝石
    private int point;//携带多少分
    private int lifeTime;//用户选择时间
    private boolean successFlg;//用户是否选择成功

    public OptionBoard() {

    }
    public VBox createOptionBoard(Route route) {
        VBox root = new VBox(20); // 间距增加一点
        root.setAlignment(Pos.CENTER);

        // 创建倒计时标签
        Label countdownLabel = new Label("10");
        countdownLabel.setStyle("-fx-font-size: 24px;");

        // 创建表单内容
        Label titleLabel = new Label("Choose Route:");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // 设置标题样式

        Button routeAButton = new Button("Route A");
        routeAButton.setId("routeAButton"); // 设置按钮ID
        routeAButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button routeBButton = new Button("Route B");
        routeBButton.setId("routeBButton"); // 设置按钮ID
        routeBButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #008CBA; -fx-text-fill: white;");

        Button routeCButton = new Button("Route C");
        routeCButton.setId("routeCButton"); // 设置按钮ID
        routeCButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #f44336; -fx-text-fill: white;");

        root.getChildren().addAll(countdownLabel, titleLabel, routeAButton, routeBButton, routeCButton);

        // 创建倒计时动画
        Timeline countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateCountdown(countdownLabel))
        );
        countdownTimeline.setCycleCount(10); // 设置动画循环次数为10，即10秒
        countdownTimeline.setOnFinished(e -> {
            root.setVisible(false); // 倒计时结束后隐藏表单面板
        });

        countdownTimeline.play(); // 启动倒计时动画

        return root;
    }
    private void updateCountdown(Label countdownLabel) {
        int secondsLeft = Integer.parseInt(countdownLabel.getText());
        secondsLeft--;
        if (secondsLeft >= 0) {
            countdownLabel.setText(Integer.toString(secondsLeft));
        }
    }
    public OptionBoard(int optionBoardID,int gemID, int point, int lifeTime, boolean successFlg) {
        this.optionBoardID = optionBoardID;//API根据数据库里optionBoardID数量 设置ID
        this.gemID = gemID;//API要传给我们这个borad服务哪个ID的宝石
        this.point = point;//宝石自己携带的属性
        this.lifeTime = lifeTime;//宝石自己携带属性
        this.successFlg = successFlg;//初始化设为false，前端应该监听用户是否点击来判断是否设为有校
    }

    public int getOptionBoardID() {
        return optionBoardID;
    }

    public void setOptionBoardID(int optionBoardID) {
        this.optionBoardID = optionBoardID;
    }

    public int getGemID() {
        return gemID;
    }

    public void setGemID(int gemID) {
        this.gemID = gemID;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public boolean isSuccessFlg() {
        return successFlg;
    }

    public void setSuccessFlg(boolean successFlg) {
        this.successFlg = successFlg;
    }

    @Override
    public String toString() {
        return
                "optionBoardID=" + optionBoardID ;
    }
}
