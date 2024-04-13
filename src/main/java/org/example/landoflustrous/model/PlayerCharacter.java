package org.example.landoflustrous.model;

public class PlayerCharacter extends Coordinated{
    // extends TrafficEntity (TODO: ADD LATER, implement getSpeed())
    // Name of player character
    private String name;

    // Time elements of player character in the current level
    private int remainingFictionalTime;
    private int maxFictionalTime;

    // Attribute of Traffic Type,instance of class TrafficType
    private TrafficType trafficType;

    // Carbon footprint attribute of the player
    private CarbonFootprint carbonFootprint; // The player's current value  of CFP
    private CarbonFootprint maxCarbonFootprint; // The CFP limit of the level

    private int currentLevel; //!!!ADJUST LATER to see if this needs to be linked with level class!!!
    private int score; // Current score held by hte player  character

    // Constructor
    public PlayerCharacter(String name, int x, int y, int maxFictionalTime, TrafficType trafficType,CarbonFootprint maxCarbonFootprint, int currentLevel)
    {
        super(x, y);
        this.name = name;
        this.maxFictionalTime = maxFictionalTime;
        this.remainingFictionalTime = maxFictionalTime; // Remaining time is full at start
        this.trafficType = trafficType;
        this.maxCarbonFootprint = maxCarbonFootprint;
        this.carbonFootprint = maxCarbonFootprint; //Carbon foot print is full at the start
        this.currentLevel=currentLevel;
        this.score = 0; //Starting score is 0
    }

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

    public int getRemainingFictionalTime() {
        return remainingFictionalTime;
    }

    public void setRemainingFictionalTime(int remainingFictionalTime) {
        this.remainingFictionalTime = remainingFictionalTime;
    }

    public int getMaxFictionalTime() {
        return maxFictionalTime;
    }

    public void setMaxFictionalTime(int maxFictionalTime) {
        this.maxFictionalTime = maxFictionalTime;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public CarbonFootprint getCarbonFootprint() {
        return carbonFootprint;
    }

    public void setCarbonFootprint(CarbonFootprint carbonFootprint) {
        this.carbonFootprint = carbonFootprint;
    }

    public CarbonFootprint getMaxCarbonFootprint() {
        return maxCarbonFootprint;
    }

    public void setMaxCarbonFootprint(CarbonFootprint maxCarbonFootprint) {
        this.maxCarbonFootprint = maxCarbonFootprint;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    // Following are methods for the playerCharacter's interaction with other lcasses
    // 1. With route: TALK TO ZHITENG ABOUT THE CONTENT OF ROUTE, below is some suggested attributes for class Route
    // Route {
    // private int endX;
    // private int endY;
    // private CarbonFootprint carbonFootprint;
    // private int travelTime;}

    //(TODO: ADD GETSTART() GETEND() in route class)
    // Methods to move the player to a new location and apply a route's effects
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //(TODO: do this afterwards)
//    public void applyRoute(Route route) {
//        // Update the player's location
//        moveTo(route.getEndX(), route.getEndY());
//
//        // Update the player's carbon footprint, ensuring it doesn't exceed the maximum
//        CarbonFootprint routeFootprint = route.getCarbonFootprint();
//        this.carbonFootprint.increase(routeFootprint.getValue()); // Assuming a method to increase carbon footprint value
//        if (this.carbonFootprint.compareTo(this.maxCarbonFootprint) > 0) {
//            this.carbonFootprint = new CarbonFootprint(this.maxCarbonFootprint.getValue()); // Set to max if exceeded
//        }
//
//        // Deduct the travel time from the remaining fictional time, ensuring it doesn't go below zero
//        this.remainingFictionalTime -= route.getTravelTime();
//        if (this.remainingFictionalTime < 0) {
//            this.remainingFictionalTime = 0; // Set to zero if overused
//        }
//
//    }

    // 2. With gem and score calculator: TALK TO HAN AND BINGYANG ABOUT INTERNAL STRUCTURE OF THESE 2 CLASSES
    // 2.1 Suggested structure of class gem

    // public class Gem {
    // private int gemID;
    // private Map<String, Integer> gemType; // Assume String is the type name, and Integer is the score
    // private boolean collected;
    // private int x; // x-coordinate
    // private int y; // y-coordinate
    // private int liveTime;
    // private int elapsedTime;

    // Constructor, getters, setters...
    // TODO: Bingyang: Communicate with Han about the gem type attribute. use int?
//    public void collect() {
//        // Method to simulate the player character action of collecting the gem
//        this.collected = true;
//        // Assuming ScoreCalculator is a static class or you have a means to access the instance
//        ScoreCalculator.addPoints(gemType.get("TypeOfThisGem")); // Replace with actual type
//    }

    // TODO: HAN: A get Method that doesnt exist yet in Gem class

//    public int getGemType() {
//        // Implement based on your gemType data structure
//        return gemType.get("TypeOfThisGem"); // Replace with actual type
//    }


    // 2.2 Suggested structure of class ScoreCalculator
    // private static int totalPoints = 0; // Static if you have one global score, non-static if per-player

    // public static void addPoints(int points) {
    //     totalPoints += points;
    // }

    // public static int getTotalPoints() {
    //     return totalPoints;
    // }

    // TODO: HAN: communicate about GemType attribute.
    // Suggested ways for player character to interact with handle gem colleciton
//    public void collectGem(Gem gem) {
//        gem.collect(); // This method should handle the logic of adding the gem's value to the score
//        this.score += gem.getGemType(); // Update the player's score with the value of the gem
//    }

    //TODO: ZHITENG: communicate about implementing this abstract method
//    @Override
//    public int getSpeed(Tile tile1, Tile tile2) {
//        return 0;
//    }
//
//    @Override
//    public void followPath(Path path) {
//
//    }
}
