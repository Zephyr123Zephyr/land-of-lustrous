//package org.example.landoflustrous.view;
//
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import org.example.landoflustrous.model.Gem;
//import org.example.landoflustrous.model.Route;
//
//import java.util.List;
//
//public class OptionBoard {
//    //    private int optionBoardID;//自己的ID
////    private int gemID;//针对哪个ID的宝石
//    private int gemPoint;//携带多少宝石分
//    private double carbonPoint;//携带多少碳分
//    private int lifeTime;//用户选择时间
//    private boolean successFlg;//用户是否选择成功
//    private boolean visible = true;
//
//    public OptionBoard() {
//
//    }
//
//    public OptionBoard(int gemPoint, double carbonPoint, boolean successFlg) {
//        this.gemPoint = gemPoint;
//        this.carbonPoint = carbonPoint;
//        this.successFlg = successFlg;
//    }
//
//
//
//    private void updateCountdown(Label countdownLabel) {
//        int secondsLeft = Integer.parseInt(countdownLabel.getText());
//        secondsLeft--;
//        if (secondsLeft >= 0) {
//            countdownLabel.setText(Integer.toString(secondsLeft));
//        }
//    }
//
////
////    public int getOptionBoardID() {
////        return optionBoardID;
////    }
////
////    public void setOptionBoardID(int optionBoardID) {
////        this.optionBoardID = optionBoardID;
////    }
////
////    public int getGemID() {
////        return gemID;
////    }
////
////    public void setGemID(int gemID) {
////        this.gemID = gemID;
////    }
//
//    public int getGemPoint() {
//        return gemPoint;
//    }
//
//    public void setGemPoint(int gemPoint) {
//        this.gemPoint = gemPoint;
//    }
//
//    public double getCarbonPoint() {
//        return carbonPoint;
//    }
//
//    public void setCarbonPoint(int carbonPoint) {
//        this.carbonPoint = carbonPoint;
//    }
//
//    public int getLifeTime() {
//        return lifeTime;
//    }
//
//    public void setLifeTime(int lifeTime) {
//        this.lifeTime = lifeTime;
//    }
//
//    public boolean isSuccessFlg() {
//        return successFlg;
//    }
//
//    public void setSuccessFlg(boolean successFlg) {
//        this.successFlg = successFlg;
//    }
////
////    @Override
////    public String toString() {
////        return
////                "optionBoardID=" + optionBoardID ;
////    }
//
//    public boolean isVisible() {
//        return this.visible;  // 返回当前可见性状态
//    }
//
//    public void setVisible(boolean visible) {
//        this.visible = visible;  // 设置可见性状态
//    }
//
//    public VBox createOptionBoard(Route route) {
//        return null;
//    }
//}
