package edu.gatech.cs2340.spacetraders.model;

/**
 * The enum Skills.
 */
public enum Skills {
    /**
     * Pilot skills.
     */
    PILOT("Pilot"),
    /**
     * Fighter skills.
     */
    FIGHTER("Fighter"),
    /**
     * Trader skills.
     */
    TRADER("Trader"),
    /**
     * Engineer skills.
     */
    ENGINEER("Engineer");

    private final String skill;
    private int points;
    // --Commented out by Inspection (4/8/2019 2:10 PM):private static int totalPoints;
    /**
     * The constant MAX_POINTS.
     */
    public static final int MAX_POINTS = 16;

    Skills(String skill) {
        this.skill = skill;
        this.points = 0;
    }

//    public static int getTotalPoints() {
//        return totalPoints;
//    }

//    public static void setTotalPoints(int totalPoints) {
//        Skills.totalPoints = totalPoints;
//    }

    /**
     * Total points int.
     *
     * @return the int
     */
    public static int totalPoints() {
        int sum = 0;
        for (Skills curr : values()) {
            sum += curr.points;
        }
        return sum;
    }

//    /**
//     * Gets skill.
//     *
//     * @return the skill
//     */
//    public String getSkill() {
//        return skill;
//    }

//    /**
//     * Sets skill.
//     *
//     * @param skill the skill
//     */
//    public void setSkill(String skill) {
//        this.skill = skill;
//    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    public String toString() {
        return String.format("Skill %s is assigned %d points", this.skill, this.points);
    }
}
