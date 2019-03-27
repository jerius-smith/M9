package edu.gatech.cs2340.spacetraders.model;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Objects;

/**
 * The type Player.
 */
public class Player {

    private String name;
    private Skills[] skills;
    private Difficulty preferredDifficulty;
    private double credits;
    private Ship ship;
    private Inventory inventory;
    private Planet location;

    /**
     * Instantiates a new Player.
     */
//    public Player() {
//        this("", Difficulty.BEGINNER, Skills.values(), new Planet("Vandor"));
//    }
    public Player() {

    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name                the name
     * @param preferredDifficulty the preferred difficulty
     * @param skillPoints         the skill points
     */
    public Player(String name, Difficulty preferredDifficulty, Skills[] skillPoints, Planet location) {
        this.name = name;
        this.preferredDifficulty = preferredDifficulty;
        this.skills = skillPoints;
        this.ship = new Gnat();
        this.credits = 1000;
        this.inventory = new Inventory();
        this.location = location;
    }

    public void updateStock(Good toUpdate) {
        inventory.setStock(toUpdate, inventory.getStock(toUpdate) + 1);
    }


    public Inventory getInventory() {
        return inventory;
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
    public double getCredits() {
        return credits;
    }

    /**
     * Sets credits.
     *
     * @param credits the credits
     */
    public void setCredits(double credits) {
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

    public void setLocation(Planet planet) {
        location = planet;
    }

    public Planet getLocation() {
        return location;
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
                .append("\nCredits : " + credits).append("\nShip type: " + ship)
                .append("\nLocation : " + location);
        return playerInfo.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Double.compare(player.credits, credits) == 0 && Objects.equals(name, player.name)
               && Arrays.equals(skills, player.skills)
               && preferredDifficulty == player.preferredDifficulty && Objects
                       .equals(ship, player.ship) && Objects.equals(inventory, player.inventory)
               && Objects.equals(location, player.location);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, preferredDifficulty, credits, ship, inventory, location);
        result = 31 * result + Arrays.hashCode(skills);
        return result;
    }

    public String toJSONString() {
        String json = new Gson().toJson(this);
        return json;
    }


}
