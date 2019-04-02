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

    public Set<SolarSystem> getSolarSystems() {
        return Collections.unmodifiableSet(solarSystems);
    }

    public SolarSystem getSolarSystemByName(String name) {
        for (SolarSystem curr : solarSystems) {
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


    public SolarSystem getRandomSolarSystem() {
        SolarSystem toReturn = null;
        for (SolarSystem curr : solarSystems) {
            //noinspection MagicNumber
            if (Math.random() < .9) {
                toReturn = curr;
            }
        }
        return toReturn;
    }

}
