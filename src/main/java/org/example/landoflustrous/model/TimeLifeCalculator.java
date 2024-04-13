package org.example.landoflustrous.model;

//import src.OptionBoard;

import javafx.scene.control.Label;

import java.util.LinkedList;
import java.util.List;

public class TimeLifeCalculator {

    // record every valid OptionBoard Result
    private int curLifeRemain = 10;
    private int initialLifeRemain = 10;

    public TimeLifeCalculator(int initialLifeRemain) {
        this.initialLifeRemain = initialLifeRemain;
        this.curLifeRemain = initialLifeRemain;
    }
// -1 game not start


    public int getCurLifeRemain() {
        return curLifeRemain;
    }

    public void setCurLifeRemain(int curLifeRemain) {
        this.curLifeRemain = curLifeRemain;
    }

    public int getInitialLifeRemain() {
        return initialLifeRemain;
    }


    public Label createCurTimeLifeLabel(){
        Label label = new Label("Initial TimeLife:"+this.getInitialLifeRemain()+" Current TimeLife:"+this.getCurLifeRemain());
        return label;
    }


}
