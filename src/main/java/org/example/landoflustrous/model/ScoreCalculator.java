//// 包声明，该代码位于org.example.landoflustrous.model包中
//package org.example.landoflustrous.model;
//
//// 引入JavaFX的Label和VBox类，用于用户界面的标签和垂直盒子布局
//
//import javafx.scene.control.Label;
//
//import java.util.LinkedList;
//import java.util.List;
//
//// 声明一个名为ScoreCalculator的公共类，用于计算和管理分数
//public class ScoreCalculator {
//    // 声明一个List类型的私有成员变量optionBoardList，用于存储OptionBoard对象
//    private List<OptionBoard> optionBoardList;
//    // 声明两个整型私有成员变量totalGemPoint和totalCarbonPoint，用于记录总宝石点数和总碳点数
//    private int totalGemPoint = -1;
//    private int totalCarbonPoint = -1;
//    // 将这两个分数初始化为-1，代表游戏未开始
//
//    // 构造函数，无参数，初始化optionBoardList为一个空的LinkedList
//    public ScoreCalculator() {
//        this.optionBoardList = new LinkedList<OptionBoard>();
//    }
//
//    // 重载的构造函数，接收一个OptionBoard类型的List作为参数，用于初始化optionBoardList
//    public ScoreCalculator(List<OptionBoard> optionBoardList) {
//        this.optionBoardList = optionBoardList;
//    }
//
//    // getOptionBoardList方法，返回optionBoardList
//    public List<OptionBoard> getOptionBoardList() {
//        return optionBoardList;
//    }
//
//    // setOptionBoardList方法，接收一个OptionBoard类型的List，用于设置optionBoardList
//    public void setOptionBoardList(List<OptionBoard> optionBoardList) {
//        this.optionBoardList = optionBoardList;
//    }
//
//    // addPoints方法，接收一个OptionBoard对象作为参数，用于添加分数
//    public String addPoints(OptionBoard optionBoard) {
//        // 检查optionBoard的isSuccessFlg方法是否返回true
//        if (optionBoard.isSuccessFlg()) {
//            // 如果totalGemPoint为-1，则初始化为0，表示开始计分
//            if (totalGemPoint == -1) {
//                totalGemPoint = 0;
//            }
//            // 如果totalCarbonPoint为-1，则同样初始化为0
//            if (totalCarbonPoint == -1) {
//                totalCarbonPoint = 0;
//            }
//            // 将optionBoard添加到optionBoardList中
//            optionBoardList.add(optionBoard);
//            // 更新totalGemPoint和totalCarbonPoint
//            totalGemPoint += optionBoard.getGemPoint();
//            totalCarbonPoint += optionBoard.getCarbonPoint();
//            // 返回"New Option Recorded"字符串，表示成功记录新选项
//            return "New Option Recorded";
//        }
//        // 如果isSuccessFlg为false，返回"Unvalid Option"字符串，表示选项无效
//        return "Unvalid Option";
//    }
//
//    // getTotalGemPoint方法，返回totalGemPoint
//    public int getTotalGemPoint() {
//        return totalGemPoint;
//    }
//
//    // setTotalGemPoint方法，接收一个整数参数设置totalGemPoint
//    public void setTotalGemPoint(int totalGemPoint) {
//        this.totalGemPoint = totalGemPoint;
//    }
//
//    // getTotalCarbonPoint方法，返回totalCarbonPoint
//    public int getTotalCarbonPoint() {
//        return totalCarbonPoint;
//    }
//
//    // setTotalCarbonPoint方法，接收一个整数参数设置totalCarbonPoint
//    public void setTotalCarbonPoint(int totalCarbonPoint) {
//        this.totalCarbonPoint = totalCarbonPoint;
//    }
//
//    // createTotalCarbonPointLabel方法，创建并返回一个显示当前总碳点数的Label对象
//    public Label createTotalCarbonPointLabel() {
//        Label label = new Label("Current Total Carbon Point: " + this.getTotalCarbonPoint());
//        return label;
//    }
//
//    // createTotalGemPointLabel方法，创建并返回一个显示当前总宝石点数的Label对象
//    public Label createTotalGemPointLabel() {
//        Label label = new Label("Current Total Gem Point: " + this.getTotalGemPoint());
//        return label;
//    }
//}
