package edu.gatech.cs2340.spacetraders.entities;

import java.util.Objects;

import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.views.AttackActivity;

/**
 * The type Travel processor.
 */
public class TravelProcessor {

    /**
     * Validate traveling.
     *
     * @param player   the player
     * @param travelTo the travel to
     * @throws TravelException the travel exception
     */
    public static void validateTraveling(Player player, Planet travelTo) throws TravelException {
        int remainingFuel = getRemainingFuel(player, travelTo);
        if (remainingFuel < 0) {
            throw new TravelException("Not enough fuel");
        } else {
            player.setShipFuelCapacity(remainingFuel);
            player.setLocation(travelTo);
        }
    }

    private static int getRemainingFuel(Player player, Planet travelTo) {
        return player.getShipFuelCapacity() - computeFuelCost(player, travelTo);
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

    /**
     * Player attacked during travel.
     *
     * @param player   the player
     * @param attacked the attacked
     */
    public static void playerAttackedDuringTravel(Player player, boolean attacked) {
        if (attacked) {
            double newCredits = player.getCredits() - AttackActivity.getDecreaseCredits();
            if (newCredits < 0) {
                newCredits = 0;
            }
            player.setCredits(newCredits);
        }
    }




}
