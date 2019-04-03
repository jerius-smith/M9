package edu.gatech.cs2340.spacetraders.entities;

import java.util.Objects;

import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;
import edu.gatech.cs2340.spacetraders.views.AttackActivity;

public class TravelProcessor {

    public static void validateTraveling(Player player, Planet travelTo) throws TravelException {
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
        double distance = computeDistance(player.getLocation(), travelTo);
        return (int) GameLogistics.mapValues(distance,
                               0,
                               GameLogistics.MAX_DISTANCE,
                               0,
                               Objects.requireNonNull(GameLogistics.MAX_CAPACITIES.get("Gnat"))[3]);
    }

    private static double computeDistance(Planet from, Planet to) {
        double distanceX = from.getxLoc() - to.getxLoc();
        double distanceY = from.getyLoc() - to.getyLoc();
        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }

    public static void playerAttackedDuringTravel(Player player, boolean attacked) {
        if (attacked) {
            double newCredits = player.getCredits() - AttackActivity.DECREASE_CREDITS_BY;
            if (newCredits < 0) {
                newCredits = 0;
            }
            player.setCredits(newCredits);
        }
    }




}
