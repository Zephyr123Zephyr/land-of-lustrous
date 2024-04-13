package org.example.landoflustrous.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.landoflustrous.view.GameStartScene;

import java.util.List;

public class OptionBoard {
    private int optionBoardID;//自己的ID
    private int gemID;//针对哪个ID的宝石
    private int gemPoint;//携带多少宝石分
    private double carbonPoint;//携带多少碳分
    private int lifeTime;//用户选择时间
    private boolean successFlg;//用户是否选择成功
    private boolean visible = true;

    public OptionBoard() {

    }

    public OptionBoard(int gemPoint, double carbonPoint, boolean successFlg) {
        this.gemPoint = gemPoint;
        this.carbonPoint = carbonPoint;
        this.successFlg = successFlg;
    }



    public VBox createOptionBoard(List<Route> routeList, Gem gem) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        int test =1;
        // 创建倒计时标签
        int maxTime = gem.getLiveTime();

        Label countdownLabel = new Label(""+maxTime);
        countdownLabel.setStyle("-fx-font-size: 24px;");

        // 创建表单内容
        Label titleLabel = new Label("Choose Route:");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // 设置标题样式
        for(int i=0;i<routeList.size();i++){
            String s = "Route "+(i+1)+" Time Cost: "+routeList.get(i).getTotalCost();
            Button routeButton = new Button(s);
            routeButton.setId("Route"+(i+1)); // 设置按钮ID
            routeButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
            routeButton.setOnAction(event -> {
                this.successFlg = true;
                setVisible(false);  // 用户做出选择，设置面板不可见
            });
            root.getChildren().add(routeButton);
        }
        root.getChildren().addAll(countdownLabel, titleLabel);







        // 创建倒计时动画
        Timeline countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateCountdown(countdownLabel))
        );

        countdownTimeline.setCycleCount(maxTime); // 设置动画循环次数为当前关卡宝石的最大持续时间

        countdownTimeline.setOnFinished(e -> {
            root.setVisible(false); // 倒计时结束后隐藏表单面板
        });

        countdownTimeline.play(); // 启动倒计时动画

        // 创建用于显示gemList内容的VBox
        VBox gemInfoBox = new VBox(10);
        gemInfoBox.setAlignment(Pos.CENTER_RIGHT);
        gemInfoBox.setPadding(new Insets(0, 20, 0, 0)); // 设置左边距
        gemInfoBox.getChildren().add(new Label("Gem Info"));
        // 循环遍历gemList并创建Label显示每个Gem的信息

            Label gemLabel = new Label("Gem ID: " + gem.getGemID() +
                    ", Type: " + gem.getType() +
                    ", X: " + gem.getX() +
                    ", Y: " + gem.getY() +
                    ", Live Time: " + gem.getLiveTime());
            gemInfoBox.getChildren().add(gemLabel);

        root.getChildren().add(gemInfoBox);



        return root;
    }
    private void updateCountdown(Label countdownLabel) {
        int secondsLeft = Integer.parseInt(countdownLabel.getText());
        secondsLeft--;
        if (secondsLeft >= 0) {
            countdownLabel.setText(Integer.toString(secondsLeft));
        }
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

    public int getGemPoint() {
        return gemPoint;
    }

    public void setGemPoint(int gemPoint) {
        this.gemPoint = gemPoint;
    }

    public double getCarbonPoint() {
        return carbonPoint;
    }

    public void setCarbonPoint(int carbonPoint) {
        this.carbonPoint = carbonPoint;
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

    public boolean isVisible() {
        return this.visible;  // 返回当前可见性状态
    }

    public void setVisible(boolean visible) {
        this.visible = visible;  // 设置可见性状态
    }

    public VBox createOptionBoard(Route route) {
        return null;
    }
}
