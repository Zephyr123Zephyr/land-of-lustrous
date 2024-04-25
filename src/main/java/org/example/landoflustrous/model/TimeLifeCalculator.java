//package org.example.landoflustrous.model;
//
//import javafx.scene.control.Label;
//
///**
// * TimeLifeCalculator 类用于管理游戏中剩余时间或生命的计算与展示。
// * 这个类记录了当前剩余的时间或生命值，并提供了获取、设置和展示这些值的方法。
// */
//public class TimeLifeCalculator {
//
//    /**
//     * 当前剩余时间或生命值。
//     */
//    private int curLifeRemain = 10;
//
//    /**
//     * 初始时间或生命值。
//     */
//    private int initialLifeRemain = 10;
//
//    /**
//     * 构造函数，初始化时间或生命值。
//     *
//     * @param initialLifeRemain 初始时间或生命值
//     */
//    public TimeLifeCalculator(int initialLifeRemain) {
//        this.initialLifeRemain = initialLifeRemain;
//        this.curLifeRemain = initialLifeRemain;
//    }
//
//    /**
//     * 获取当前剩余时间或生命值。
//     *
//     * @return 当前剩余时间或生命值
//     */
//    public int getCurLifeRemain() {
//        return curLifeRemain;
//    }
//
//    /**
//     * 设置当前剩余时间或生命值。
//     *
//     * @param curLifeRemain 新的当前剩余时间或生命值
//     */
//    public void setCurLifeRemain(int curLifeRemain) {
//        this.curLifeRemain = curLifeRemain;
//    }
//
//    /**
//     * 获取初始时间或生命值。
//     *
//     * @return 初始时间或生命值
//     */
//    public int getInitialLifeRemain() {
//        return initialLifeRemain;
//    }
//
//    /**
//     * 创建一个标签，展示初始时间或生命值和当前剩余时间或生命值。
//     *
//     * @return 包含初始和当前时间或生命值的标签
//     */
//    public Label createCurTimeLifeLabel() {
//        Label label = new Label("Initial TimeLife:" + this.getInitialLifeRemain() + " Current TimeLife:" + this.getCurLifeRemain());
//        return label;
//    }
//
//}