package edu.gatech.cs2340.spacetraders.entities;

import java.util.Objects;

import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;

public class TravelProcessor {

    private static void validateTraveling(Player player, Planet travelTo) throws TravelException {
        int remainingFuel = getRemainingFuel(player, travelTo);
        if (remainingFuel < 0) {
            throw new TravelException("Not enough fuel");
        } else {
            player.getShip().setFuelCapacity(remainingFuel);
            player.setLocation(travelTo);

        }
    }

    private static int getRemainingFuel(Player player, Planet travelTo) {
        Ship currentShip = player.getShip();
        return currentShip.getFuelCapacity() - computeFuelCost(player, travelTo);
    }

    private static int computeFuelCost(Player player, Planet travelTo) {
        int maxDecrease = player.getShip().getFuelCapacity();
        double distance = computeDistance(player.getLocation(), travelTo);
        return (int) mapValues(distance,
                               0,
                               GameLogistics.MAX_DISTANCE,
                               0,
                               Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get("Gnat"))[3]);
    }

    private static double computeDistance(Planet from, Planet to) {
        double distanceX = from.getxLoc() - to.getxLoc();
        double distanceY = from.getyLoc() - to.getyLoc();
        return Math.sqrt((distanceX * distanceX) - (distanceY * distanceY));
    }

    private static double mapValues(double original, double omin, double omax, double nmin,
                               double nmax) {
        return nmin + (nmax - nmin) * ((original - omin) / (omax - omin));
    }


}
