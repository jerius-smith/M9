package edu.gatech.cs2340.spacetraders.model;

import com.google.gson.Gson;

import java.util.Random;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;

import static java.lang.Math.floor;

/**
 * The type Planet.
 */
public class Planet {

    private String name;
    private double xLoc;
    private double yLoc;
    private TechLevel techLevel;
    private Resource resource;
    private Government politicalSystem;
    private Market market;
    private String solarSystemCurrentlyIn;

    public double getxLoc() {
        return xLoc;
    }

    public void setxLoc(double xLoc) {
        this.xLoc = xLoc;
    }

    public double getyLoc() {
        return yLoc;
    }

    public void setyLoc(double yLoc) {
        this.yLoc = yLoc;
    }

    public String getSolarSystemCurrentlyIn() {
        return solarSystemCurrentlyIn;
    }

    public void setSolarSystemCurrentlyIn(String solarSystemCurrentlyIn) {
        this.solarSystemCurrentlyIn = solarSystemCurrentlyIn;
    }

    /**
     * Instantiates a new Planet.
     *
     * @param name the name
     */
    public Planet(String name) {
        this.name = name;
        xLoc = floor(new Random().nextDouble() * GameLogistics.MAX_WIDTH);
        yLoc = floor(new Random().nextDouble() * GameLogistics.MAX_HEIGHT);
        techLevel = TechLevel.values()[new Random().nextInt(TechLevel.numElements())];
        resource = Resource.values()[new Random().nextInt(Resource.numElements())];
        politicalSystem = null;
        market = new Market(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Planet)) {
            return false;
        } else {
            Planet that = (Planet) other;
            return this.name.equals(that.name) && Double.compare(xLoc, that.xLoc) == 0
                   && Double.compare(yLoc, that.yLoc) == 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().
                append("\n\t\t\tPlanet: " + name).
                append(String.format("\n\t\t\tLocation: (%.0f, %.0f)", xLoc, yLoc)).
                append("\n\t\t\tTech level: " + techLevel).
                append("\n\t\t\tResource: " + resource).
                append("\n\t\t\tSolar System: " + solarSystemCurrentlyIn);
        return stringBuilder.toString();
    }

    public String toJSONString() {
        String json = new Gson().toJson(this);
        return json;
    }

    public TechLevel getTechLevel() {
        return techLevel;
    }

    public Market getPlanetsMarket() {
        return market;
    }

}
