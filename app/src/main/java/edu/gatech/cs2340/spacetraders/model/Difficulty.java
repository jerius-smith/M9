package edu.gatech.cs2340.spacetraders.model;

/**
 * The enum Difficulty .
 */
public enum Difficulty {
    /**
     * Beginner difficulty.
     */
    BEGINNER("Beginner"),
    /**
     * Easy difficulty.
     */
    EASY("Easy"),
    /**
     * Normal difficulty.
     */
    NORMAL("Normal"),
    /**
     * Hard difficulty.
     */
    HARD("Hard"),
    /**
     * Impossible difficulty.
     */
    IMPOSSIBLE("Impossible");

    /**
     * The Difficulty.
     */
    String difficulty;

    private Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    Difficulty() {
    }

    @Override
    public String toString() {
        return this.difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
