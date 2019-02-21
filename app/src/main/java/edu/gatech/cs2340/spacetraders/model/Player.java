package edu.gatech.cs2340.spacetraders.model;

/**
 * The type Player.
 */
public class Player {

    private String name;
    private Skills[] skills;
    private Difficulty preferredDifficulty;
    private int credits;
    private Ship ship;

    /**
     * Instantiates a new Player.
     */
    public Player() {
        this("", Difficulty.BEGINNER, Skills.values());
    }

    /**
     * Instantiates a new Player.
     *
     * @param name                the name
     * @param preferredDifficulty the preferred difficulty
     * @param skillPoints         the skill points
     */
    public Player(String name, Difficulty preferredDifficulty, Skills[] skillPoints) {
        this.name = name;
        this.preferredDifficulty = preferredDifficulty;
        this.skills = skillPoints;
        this.ship = new Gnat();
        this.credits = 1000;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get skills skills [ ].
     *
     * @return the skills [ ]
     */
    public Skills[] getSkills() {
        return skills;
    }

    /**
     * Sets skills.
     *
     * @param skills the skills
     */
    public void setSkills(Skills[] skills) {
        this.skills = skills;
    }

    /**
     * Gets preferred difficulty.
     *
     * @return the preferred difficulty
     */
    public Difficulty getPreferredDifficulty() {
        return preferredDifficulty;
    }

    /**
     * Sets preferred difficulty.
     *
     * @param preferredDifficulty the preferred difficulty
     */
    public void setPreferredDifficulty(Difficulty preferredDifficulty) {
        this.preferredDifficulty = preferredDifficulty;
    }

    /**
     * Gets credits.
     *
     * @return the credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets credits.
     *
     * @param credits the credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gets ship.
     *
     * @return the ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets ship.
     *
     * @param ship the ship
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public String toString() {
        StringBuilder playerInfo = new StringBuilder();
        playerInfo.append("\t\nPlayer: " + name)
                  .append("\nSelected Difficulty: " + preferredDifficulty)
                  .append("\nPilot points: " + skills[0].getPoints())
                  .append("\nFighter points: " + skills[1].getPoints())
                  .append("\nTrader points: " + skills[2].getPoints())
                  .append("\nEngineer points: " + skills[3].getPoints())
                  .append("\nCredits : " + credits)
                  .append("\nShip type: " + ship);
        return playerInfo.toString();
    }

}
