package edu.gatech.cs2340.spacetraders.model;

/**
 * The type Ship.
 */
public abstract class Ship {

    private String name;

    /**
     * Instantiates a new Ship.
     *
     * @param name the name
     */
    public Ship(String name) {
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

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " spaceship";
    }
}
