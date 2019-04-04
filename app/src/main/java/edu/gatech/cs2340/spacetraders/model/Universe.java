package edu.gatech.cs2340.spacetraders.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;

/**
 * The type Universe.
 */
public final class Universe {
    private static final Universe ourInstance = new Universe();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Universe getInstance() {
        return ourInstance;
    }

    private final Set<SolarSystem> solarSystems;

    private Universe() {
        solarSystems = new HashSet<>();
        for (int i = 0; i < GameLogistics.MAX_SOLAR_SYSTEMS; i++) {
            solarSystems.add(new SolarSystem(GameLogistics.SOLAR_SYSTEM_NAMES[i]));
        }
    }

    /**
     * Gets solar systems.
     *
     * @return the solar systems
     */
    public Set<SolarSystem> getSolarSystems() {
        return Collections.unmodifiableSet(solarSystems);
    }

    /**
     * Gets solar system by name.
     *
     * @param name the name
     * @return the solar system by name
     */
    public static SolarSystem getSolarSystemByName(String name) {
        Universe universe = getInstance();
        for (SolarSystem curr : universe.solarSystems) {
            if (name.equals(curr.getName())) {
                return curr;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SolarSystem curr : solarSystems) {
            stringBuilder.append("\n\t").append(curr.toString());
        }
        return stringBuilder.toString();
    }


    /**
     * Gets random solar system.
     *
     * @return the random solar system
     */
    public static SolarSystem getRandomSolarSystem() {
        SolarSystem toReturn = null;
        Universe uni = getInstance();
        for (SolarSystem curr : uni.solarSystems) {
            if (Math.random() < .9) {
                toReturn = curr;
            }
        }
        return toReturn;
    }

    /**
     * Gets random planet.
     *
     * @return the random planet
     */
    public static Planet getRandomPlanet() {
        SolarSystem solar = getRandomSolarSystem();
        return solar.getRandomPlanet();
    }

}
