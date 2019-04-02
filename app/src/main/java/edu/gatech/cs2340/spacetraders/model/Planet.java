package edu.gatech.cs2340.spacetraders.model;

import java.util.Random;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;

import static java.lang.Math.floor;

/**
 * The type Planet.
 */
public class Planet {
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private String name;
    private double xLoc;
    private double yLoc;
    private TechLevel techLevel;
    private Resource resource;
    private Government politicalSystem;
    private Market market;
    private String solarSystemCurrentlyIn;

    public void setName(String name) {
        this.name = name;
    }

    public void setTechLevel(TechLevel techLevel) {
        this.techLevel = techLevel;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Government getPoliticalSystem() {
        return politicalSystem;
    }

    public void setPoliticalSystem(Government politicalSystem) {
        this.politicalSystem = politicalSystem;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Planet() {
    }

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
            return this.name.equals(that.name) && (Double.compare(xLoc, that.xLoc) == 0) && (
                    Double.compare(yLoc, that.yLoc) == 0);
        }
    }

    @Override
    public String toString() {
        String stringBuilder = "\n\t\t\tPlanet: " + name + String
                .format("\n\t\t\tLocation: (%.0f, %.0f)", xLoc, yLoc) + "\n\t\t\tTech level: "
                               + techLevel + "\n\t\t\tResource: " + resource
                               + "\n\t\t\tSolar System: " + solarSystemCurrentlyIn;
        return stringBuilder;
    }


    public TechLevel getTechLevel() {
        return techLevel;
    }

    public Market getPlanetsMarket() {
        return market;
    }

}
