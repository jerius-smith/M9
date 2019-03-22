package edu.gatech.cs2340.spacetraders.model;

public class SavedPlayer {

    private String name;
    private String currentPlayerText;
    private String playerJsonName;
    private String universeJsonName;

    public SavedPlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPlayerText() {
        return currentPlayerText;
    }

    public void setCurrentPlayerText(String currentPlayerText) {
        this.currentPlayerText = currentPlayerText;
    }

    public String getPlayerJsonName() {
        return playerJsonName;
    }

    public void setPlayerJsonName(String playerJsonName) {
        this.playerJsonName = playerJsonName;
    }

    public String getUniverseJsonName() {
        return universeJsonName;
    }

    public void setUniverseJsonName(String universeJsonName) {
        this.universeJsonName = universeJsonName;
    }

    public String toString() {
        return name;
    }
}


