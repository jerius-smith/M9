package edu.gatech.cs2340.spacetraders.model;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;

import static java.lang.Math.floor;

/**
 * The type Solar system.
 */
public class SolarSystem {

    private String name;
    private double xLoc;
    private double yLoc;
    private Set<Planet> planets;
    private Set<Mercenary> mercenaries;
    private static final int NUM_PLANETS = ((int) (Math.random() * 7)) + 4;

    /**
     * Instantiates a new Solar system.
     *
     * @param name the name
     */
    public SolarSystem(String name) {
        this.name = name;
        planets = new HashSet<>();
        for (int i = 0; i < NUM_PLANETS; i++) {
            Planet planet = new Planet(GameLogistics.PLANET_NAMES[new Random()
                    .nextInt(GameLogistics.PLANET_NAMES.length)]);
            planet.setSolarSystemCurrentlyIn(name);
            planets.add(planet);
        }

        mercenaries = new HashSet<>();
        for (int i = 0; i < GameLogistics.MAX_MERCENARIES; i++) {
            mercenaries.add(new Mercenary());
        }

        xLoc = floor(new Random().nextDouble() * GameLogistics.MAX_WIDTH);
        yLoc = floor(new Random().nextDouble() * GameLogistics.MAX_HEIGHT);
    }

    /**
     * Gets planets.
     *
     * @return the planets
     */
    public Set<Planet> getPlanets() {
        return planets;
    }

    /**
     * Sets planets.
     *
     * @param planets the planets
     */
    public void setPlanets(Set<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().
                append("\nSolar System: " + name).
                append(String.format("\nLocation: (%.0f, %.0f)", xLoc, yLoc));
        for (Planet curr : planets) {
            stringBuilder.append("\n\t" + curr.toString());
        }
        return stringBuilder.toString();
    }

    public String toJSONString() {

        String json = new Gson().toJson(this);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SolarSystem that = (SolarSystem) o;
        return Double.compare(that.xLoc, xLoc) == 0 && Double.compare(that.yLoc, yLoc) == 0
               && Objects.equals(name, that.name) && Objects.equals(planets, that.planets)
               && Objects.equals(mercenaries, that.mercenaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, xLoc, yLoc, planets, mercenaries);
    }
}
