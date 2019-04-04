package edu.gatech.cs2340.spacetraders.model;

/**
 * The type Saved player.
 */
public class SavedPlayer {

    private final String name;
    private String currentPlayerText;
    private String universeJsonName;
    private String playerJsonContent;
    private String universeJsonContent;
    private String playerJsonName;

//    public String getPlayerJsonContent() {
//        return playerJsonContent;
//    }

    /**
     * Sets player json content.
     *
     * @param playerJsonContent the player json content
     */
    public void setPlayerJsonContent(String playerJsonContent) {
        this.playerJsonContent = playerJsonContent;
    }

//    public String getUniverseJsonContent() {
//        return universeJsonContent;
//    }

    /**
     * Sets universe json content.
     *
     * @param universeJsonContent the universe json content
     */
    public void setUniverseJsonContent(String universeJsonContent) {
        this.universeJsonContent = universeJsonContent;
    }

    /**
     * Instantiates a new Saved player.
     *
     * @param name the name
     */
    public SavedPlayer(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    /**
     * Gets current player text.
     *
     * @return the current player text
     */
    public String getCurrentPlayerText() {
        return currentPlayerText;
    }

    /**
     * Sets current player text.
     *
     * @param currentPlayerText the current player text
     */
    public void setCurrentPlayerText(String currentPlayerText) {
        this.currentPlayerText = currentPlayerText;
    }

//    public String getPlayerJsonName() {
//        return playerJsonName;
//    }

    /**
     * Sets player json name.
     *
     * @param playerJsonName the player json name
     */
    public void setPlayerJsonName(String playerJsonName) {
        playerJsonName = playerJsonName;
    }

//    public String getUniverseJsonName() {
//        return universeJsonName;
//    }

    /**
     * Sets universe json name.
     *
     * @param universeJsonName the universe json name
     */
    public void setUniverseJsonName(String universeJsonName) {
        this.universeJsonName = universeJsonName;
    }

    public String toString() {
        return name;
    }
}


