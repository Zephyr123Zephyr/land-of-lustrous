package org.example.landoflustrous.model;

//import src.OptionBoard;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class ScoreCalculator {
    private List<OptionBoard> optionBoardList;
    // record every valid OptionBoard Result
    private int totalGemPoint = -1;
    private int totalCarbonPoint = -1;
    // -1 game not start


    public ScoreCalculator() {
        this.optionBoardList = new LinkedList<OptionBoard>();
    }

    public ScoreCalculator(List<OptionBoard> optionBoardList) {
        this.optionBoardList = optionBoardList;
    }


    public List<OptionBoard> getOptionBoardList() {
        return optionBoardList;
    }

    public void setOptionBoardList(List<OptionBoard> optionBoardList) {
        this.optionBoardList = optionBoardList;
    }

    public String addPoints(OptionBoard optionBorad) {
        if(optionBorad.isSuccessFlg()){
            if(totalGemPoint==-1){
                totalGemPoint=0;

            }
            if(totalCarbonPoint==-1){
                totalCarbonPoint=0;
            }
            optionBoardList.add(optionBorad);
            totalGemPoint+=optionBorad.getGemPoint();
            totalCarbonPoint+=optionBorad.getCarbonPoint();
            return "New Option Recorded";

        }
        return "Unvalid Option";

    }

    public int getTotalGemPoint() {
        return totalGemPoint;
    }

    public void setTotalGemPoint(int totalGemPoint) {
        this.totalGemPoint = totalGemPoint;
    }

    public int getTotalCarbonPoint() {
        return totalCarbonPoint;
    }

    public void setTotalCarbonPoint(int totalCarbonPoint) {
        this.totalCarbonPoint = totalCarbonPoint;
    }

    public Label createTotalCarbonPointLabel(){
        Label label = new Label("Current Total Carbon Point"+this.getTotalCarbonPoint());
        return label;
    }
    public Label createTotalGemPointLabel(){
        Label label = new Label("Current Total Gem Point"+this.getTotalGemPoint());
        return label;
    }


}
