package edu.gatech.cs2340.spacetraders.model;

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

//    public void setInventory(Inventory inventory) {
//        this.inventory = inventory;
//    }

    /**
     * Instantiates a new Player.
     *
     * @param name                the name
     * @param preferredDifficulty the preferred difficulty
     * @param skillPoints         the skill points
     */
    public Player(String name, Difficulty preferredDifficulty, Skills[] skillPoints,
                  Planet location) {
        this.name = name;
        this.preferredDifficulty = preferredDifficulty;
        this.skills = skillPoints.clone();
        this.ship = new Gnat();
        this.credits = 1000;
        this.inventory = new Inventory();
        this.location = location;
    }

//    public void updateStock(Good toUpdate) {
//        inventory.setStock(toUpdate, inventory.getStock(toUpdate) + 1);
//    }


    public Inventory getInventory() {
        return inventory;
    }

    public int getTotalStock() {
        return inventory.getTotalStock();
    }

    public int getStock(Good toSell) {
        return inventory.getStock(toSell);
    }

    public void setStock(Good toBuy,int stock) {
        inventory.setStock(toBuy,stock);
    }

    public void adjustTotalStock(int stock) {
        inventory.adjustTotalStock(stock);
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

//    /**
//     * Get skills skills [ ].
//     *
//     * @return the skills [ ]
//     */
//    public Skills[] getSkills() {
//        return skills.clone();
//    }

    /**
     * Sets skills.
     *
     * @param skills the skills
     */
    public void setSkills(Skills[] skills) {
        this.skills = skills.clone();
    }

//    /**
//     * Gets preferred difficulty.
//     *
//     * @return the preferred difficulty
//     */
//    public Difficulty getPreferredDifficulty() {
//        return preferredDifficulty;
//    }

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

    public int getShipFuelCapacity() {
        return ship.getFuelCapacity();
    }

    public void setShipFuelCapacity(int fuelCapacity) {
        ship.setFuelCapacity(fuelCapacity);
    }

//    /**
//     * Sets ship.
//     *
//     * @param ship the ship
//     */
//    public void setShip(Ship ship) {
//        this.ship = ship;
//    }

    public void setLocation(Planet planet) {
        location = planet;
    }

    public Planet getLocation() {
        return location;
    }

    public String getLocationName() { return location.getName(); }


    public Market getPlanetsMarket() {
        return location.getPlanetsMarket();
    }

    @Override
    public String toString() {
        return "\t\nPlayer: " + name + "\nSelected Difficulty: " + preferredDifficulty
                            + "\nPilot points: " + skills[0].getPoints() + "\nFighter points: "
                            + skills[1].getPoints() + "\nTrader points: " + skills[2].getPoints()
                            + "\nEngineer points: " + skills[3].getPoints() + "\nCredits : "
                            + credits + "\nShip type: " + ship + "\nLocation : " + location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Player player = (Player) o;
        return (Double.compare(player.credits, credits) == 0) && Objects.equals(name, player.name)
               && Arrays.equals(skills, player.skills) && (preferredDifficulty
                                                           == player.preferredDifficulty) && Objects
                       .equals(ship, player.ship) && Objects.equals(inventory, player.inventory)
               && Objects.equals(location, player.location);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, preferredDifficulty, credits, ship, inventory, location);
        result = (31 * result) + Arrays.hashCode(skills);
        return result;
    }


}
