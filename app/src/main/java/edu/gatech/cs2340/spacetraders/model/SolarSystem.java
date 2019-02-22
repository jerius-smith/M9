package edu.gatech.cs2340.spacetraders.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Math.floor;

public class SolarSystem {

    private String name;
    private double xLoc;
    private double yLoc;
    private Set<Planet> planets;
    private Set<Mercenary> mercenaries;
    private static final int NUM_PLANETS = ((int) (Math.random() * 7)) + 4;

    public SolarSystem(String name) {
        planets = new HashSet<>();
        for (int i = 0; i < NUM_PLANETS; i++) {
            planets.add(new Planet(GameLogistics.PLANET_NAMES[new Random()
                    .nextInt(GameLogistics.PLANET_NAMES.length)]));
        }

        mercenaries = new HashSet<>();
        for (int i = 0; i < GameLogistics.MAX_MERCENARIES; i++) {
            mercenaries.add(new Mercenary());
        }

        this.name = name;
        xLoc = floor(new Random().nextDouble() * GameLogistics.MAX_WIDTH);
        yLoc = floor(new Random().nextDouble() * GameLogistics.MAX_HEIGHT);
    }

    public Set<Planet> getPlanets() {
        return planets;
    }

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
}
