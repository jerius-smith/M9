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
     *
     * @param name                the name
     * @param preferredDifficulty the preferred difficulty
     * @param skillPoints         the skill points
     * @param location            the planet the player is on
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


    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets total stock.
     *
     * @return the total stock
     */
    public int getTotalStock() {
        return inventory.getTotalStock();
    }

    /**
     * Gets stock.
     *
     * @param toSell the to sell
     * @return the stock
     */
    public int getStock(Good toSell) {
        return inventory.getStock(toSell);
    }

    /**
     * Sets stock.
     *
     * @param toBuy the to buy
     * @param stock the stock
     */
    public void setStock(Good toBuy,int stock) {
        inventory.setStock(toBuy,stock);
    }

    /**
     * Adjust total stock.
     *
     * @param stock the stock
     */
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

    /**
     * Gets ship fuel capacity.
     *
     * @return the ship fuel capacity
     */
    public int getShipFuelCapacity() {
        return ship.getFuelCapacity();
    }

    /**
     * Sets ship fuel capacity.
     *
     * @param fuelCapacity the fuel capacity
     */
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

    /**
     * Sets location.
     *
     * @param planet the planet
     */
    public void setLocation(Planet planet) {
        location = planet;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Planet getLocation() {
        return location;
    }

    /**
     * Gets location name.
     *
     * @return the location name
     */
    public String getLocationName() { return location.getName(); }


    /**
     * Gets planets market.
     *
     * @return the planets market
     */
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
