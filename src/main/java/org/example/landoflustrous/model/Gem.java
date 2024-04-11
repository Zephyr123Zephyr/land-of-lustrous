package org.example.landoflustrous.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Gem extends Coordinated {

    private int gemID;
    private Map<String, Integer> gemType; // Map to hold gem types and their scores
    public boolean collected;
    private int liveTime; // Defines how long the gem appears
    private int elapsedTime; // Tracks time since gem appeared

    // Static fields for unique ID generation and randomization
    private static int nextID = 1;
    private static Random random = new Random();

    // Example gem types and scores (could be replaced with actual game data)
    private static Map<String, Integer> availableGemTypes = new HashMap<>();
    static {
        availableGemTypes.put("Ruby", 100);
        availableGemTypes.put("Sapphire", 200);
        availableGemTypes.put("Emerald", 300);
    }

    // Constructor
    public Gem() {
        this.gemID = nextID++;
        this.gemType = new HashMap<>();
        selectRandomGemType();
        this.collected = false;
        this.x = random.nextInt(30); // Assuming a 100x100 grid
        this.y = random.nextInt(30);
        this.liveTime = random.nextInt(301) + 300; // 300 to 600 seconds of life
        this.elapsedTime = 0;
    }

    // Method to randomly select a gem type and its score
    private void selectRandomGemType() {
        Object[] gemTypes = availableGemTypes.keySet().toArray();
        String selectedType = (String) gemTypes[random.nextInt(gemTypes.length)];
        this.gemType.put(selectedType, availableGemTypes.get(selectedType));
    }

    // Method to simulate collecting the gem
    public void collect() {
        this.collected = true;
    }

    // Method to handle gem disappearing logic
    public void gemDisappear() {
        if (this.elapsedTime >= this.liveTime) {
            this.collected = true;
        }
    }

    // Update gem's state
    public void update(int secondsPassed) {
        if (!collected) {
            this.elapsedTime += secondsPassed;
            gemDisappear();
        }
    }

    // Getters and Setters
    public int getGemID() {
        return gemID;
    }

    public boolean isCollected() {
        return collected;
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

    public int getLiveTime() {
        return liveTime;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    // Returns the score of the gem based on its type
    public int getScore() {
        return this.gemType.values().iterator().next(); // Since only one type is selected
    }

    // Returns a string representation of the gem type
    public String getType() {
        return this.gemType.keySet().iterator().next(); // Since only one type is selected
    }
}