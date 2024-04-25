package org.example.landoflustrous.model;

public class PlayerCharacter extends Coordinated {
    // extends TrafficEntity (TODO: ADD LATER, implement getSpeed())
    // Name of player character
    private String name;
    private int carbonHP;
    private int gemNumber;
    private int gemScore;


    public PlayerCharacter(int x, int y, String name) {
        super(x, y);
        this.name = name;
        this.carbonHP = 3000;
        this.gemNumber = 0;
        this.gemScore = 0;


    }

    // Time elements of player character in the current level
//    private int remainingFictionalTime;
//    private int maxFictionalTime;

    // Attribute of Traffic Type,instance of class TrafficType
//    private TrafficType trafficType;

    public int getGemNumber() {
        return gemNumber;
    }

    public int getGemScore() {
        return gemScore;
    }

    private int maxCarbonFootprint; // The CFP limit of the level


    public void addGemNumber(int gemNumber) {
        this.gemNumber += gemNumber;

    }

    public void addGemScore(int gemScore) {
        this.gemScore += gemScore;
    }


//    private int currentLevel; //!!!ADJUST LATER to see if this needs to be linked with level class!!!


    public int[] getCoordinates() {
        return new int[]{this.getX(), this.getY()};
    }


    // Constructor
//    public PlayerCharacter(String name, int x, int y, int maxFictionalTime, TrafficType trafficType, int maxCarbonFootprint, int currentLevel) {
//        super(x, y);
//        this.name = name;
//
////        this.maxFictionalTime = maxFictionalTime;
////        this.remainingFictionalTime = maxFictionalTime; // Remaining time is full at start
////        this.trafficType = trafficType;
//        this.maxCarbonFootprint = maxCarbonFootprint;
//        this.carbonFootprint = maxCarbonFootprint; //Carbon foot print is full at the start
////        this.currentLevel = currentLevel;
//        this.score = 0; //Starting score is 0
//    }

    // Getter Setter Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


//    public int getRemainingFictionalTime() {
//        return remainingFictionalTime;
//    }

//    public void setRemainingFictionalTime(int remainingFictionalTime) {
//        this.remainingFictionalTime = remainingFictionalTime;
//    }
//
//    public int getMaxFictionalTime() {
//        return maxFictionalTime;
//    }
//
//    public void setMaxFictionalTime(int maxFictionalTime) {
//        this.maxFictionalTime = maxFictionalTime;
//    }
//
//    public TrafficType getTrafficType() {
//        return trafficType;
//    }
//
//    public void setTrafficType(TrafficType trafficType) {
//        this.trafficType = trafficType;
//    }

    public int getCarbonHP() {
        return carbonHP;
    }

    public void setCarbonHP(int carbonHP) {
        this.carbonHP = carbonHP;
    }

    public int getMaxCarbonFootprint() {
        return maxCarbonFootprint;
    }

    public void setMaxCarbonFootprint(int maxCarbonFootprint) {
        this.maxCarbonFootprint = maxCarbonFootprint;
    }


    public int getScore() {
        return gemScore;
    }

    public void setScore(int score) {
        this.gemScore = score;
    }


    //(TODO: ADD GETSTART() GETEND() in route class)
    // Methods to move the player to a new location and apply a route's effects
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
//(TODO: do this afterwards)

}