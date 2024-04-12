package org.example.landoflustrous.model;

//import src.OptionBoard;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class ScoreCalculator {
    private List<OptionBoard> optionBoardList;
    // record every valid OptionBoard Result
    private int totalPoint = -1;
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
            if(totalPoint==-1){
                totalPoint=0;
            }
            optionBoardList.add(optionBorad);
            totalPoint+=optionBorad.getPoint();
            return "New Option Recorded";

        }
        return "Unvalid Option";

    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public Label createTotalPointLabel(){
        Label label = new Label("Current Total Point"+this.getTotalPoint());
        return label;
    }


}
