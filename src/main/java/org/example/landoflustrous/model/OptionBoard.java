package org.example.landoflustrous.model;

public class OptionBoard {
    private int optionBoardID;//自己的ID
    private int gemID;//针对哪个ID的宝石
    private int point;//携带多少分
    private int lifeTime;//用户选择时间
    private boolean successFlg;//用户是否选择成功

    public OptionBoard() {

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
