package edu.gatech.cs2340.spacetraders.model;

import java.util.Collection;
import java.util.Objects;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;

/**
 * The type Ship.
 */
public class Ship {

    private String name;
    private final int cargoCapacity;
    private final int gadgetCapacity;
    private Collection<Weapons> weapons;
    private int fuelCapacity;

    /**
     * Instantiates a new Ship.
     *
     * @param name the name
     */
    public Ship(String name) {
        this.name = name;
        this.cargoCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[2];
        this.gadgetCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[1];
        this.fuelCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[3];
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

    public int getFuelCapacity() { return fuelCapacity; }

    public void setFuelCapacity(int newFuelCapacity) {
        this.fuelCapacity = newFuelCapacity;
    }

    @Override
    public String toString() {
        return String.format("%s with %d fuel left", name, fuelCapacity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }
        Ship ship = (Ship) o;
        return cargoCapacity == ship.cargoCapacity && gadgetCapacity == ship.gadgetCapacity
               && fuelCapacity == ship.fuelCapacity && Objects.equals(name, ship.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cargoCapacity, gadgetCapacity, fuelCapacity);
    }
}
