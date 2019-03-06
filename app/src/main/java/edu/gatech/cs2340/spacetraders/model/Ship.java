package edu.gatech.cs2340.spacetraders.model;

import java.util.Collection;
import java.util.Objects;

/**
 * The type Ship.
 */
public class Ship {

    private String name;
    private final int cargoCapacity;
    private final int gadgetCapacity;
    private Collection<Weapons> weapons;

    /**
     * Instantiates a new Ship.
     *
     * @param name the name
     */
    public Ship(String name) {
        this.name = name;
        this.cargoCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[2];
        this.gadgetCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[1];
    }

    public void addWeapon (Weapons weapon) {
        if (weapons.size() < cargoCapacity) {
            weapons.add(weapon);
        }
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

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public int getGadgetCapacity() {
        return gadgetCapacity;
    }

    @Override
    public String toString() {
        return name + " spaceship";
    }
}
