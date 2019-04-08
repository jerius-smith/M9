package edu.gatech.cs2340.spacetraders.model;

import java.util.Objects;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;

/**
 * The type Ship.
 */
public class Ship {

    private final String name;
    private final int cargoCapacity;
    private final int gadgetCapacity;
//    private Collection<Weapons> weapons;
    private int fuelCapacity;
    private boolean fuelTooLow;

    /**
     * Is fuel too low boolean.
     *
     * @return the boolean
     */
    public boolean isFuelTooLow() {
        return fuelTooLow;
    }

//    public Collection<Weapons> getWeapons() {
//        return Collections.unmodifiableCollection(weapons);
//    }

//    public void setWeapons(Collection<Weapons> weapons) {
//        this.weapons = weapons;
//    }

//    public void setCargoCapacity(int cargoCapacity) {
//        this.cargoCapacity = cargoCapacity;
//    }

//    public void setGadgetCapacity(int gadgetCapacity) {
//        this.gadgetCapacity = gadgetCapacity;
//    }


    /**
     * Sets fuel too low.
     *
     * @param fuelTooLow the fuel too low
     */
    public void setFuelTooLow(boolean fuelTooLow) {
        this.fuelTooLow = fuelTooLow;
    }

    /**
     * Instantiates a new Ship.
     *
     * @param name the name
     */
    Ship(String name) {
        this.name = name;
        this.cargoCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[2];
        this.gadgetCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[1];
        this.fuelCapacity = Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get(name))[3];
        this.fuelTooLow = false;
    }

//    public void addWeapon(Weapons weapon) {
//        if (weapons.size() < cargoCapacity) {
//            weapons.add(weapon);
//        }
//    }

    /**
     * Gets name.
     *
     * @return the name
     */
//    public String getName() {
//        return name;
//    }

//    /**
//     * Sets name.
//     *
//     * @param name the name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

//    public int getGadgetCapacity() {
//        return gadgetCapacity;
//    }

    /**
     * Gets fuel capacity.
     *
     * @return the fuel capacity
     */
    public int getFuelCapacity() {
        return fuelCapacity;
    }

    /**
     * Sets fuel capacity.
     *
     * @param newFuelCapacity the new fuel capacity
     */
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
        return (cargoCapacity == ship.cargoCapacity) && (gadgetCapacity == ship.gadgetCapacity) && (
                fuelCapacity == ship.fuelCapacity) && Objects.equals(name, ship.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cargoCapacity, gadgetCapacity, fuelCapacity);
    }
}
