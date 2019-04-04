package edu.gatech.cs2340.spacetraders.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;

import edu.gatech.cs2340.spacetraders.entities.GameLogistics;
import edu.gatech.cs2340.spacetraders.entities.TravelException;
import edu.gatech.cs2340.spacetraders.entities.TravelProcessor;
import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;
import edu.gatech.cs2340.spacetraders.views.RandomEventActivity;

/**
 * The type Travel view model.
 */
public class TravelViewModel extends AndroidViewModel {

    /**
     * The constant OMAX.
     */
    private static final int OMAX = 80;
    private Player player;
    private Ship playerShip;

    /**
     * Instantiates a new Travel view model.
     *
     * @param application the application
     */
    public TravelViewModel(@NonNull Application application) {
        super(application);
        try {
            player = DataStore.getCurrentPlayer(getApplication());
            playerShip = player.getShip();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets player location.
     *
     * @return the player location
     */
    public String getPlayerLocation() {
        return player.getLocationName();
    }

    /**
     * Gets ship fuel.
     *
     * @return the ship fuel
     */
    public int getShipFuel() {
        int actualFuel = playerShip.getFuelCapacity();
        return (int) GameLogistics.mapValues(actualFuel, 0, OMAX, 0, 100);
    }

    /**
     * Travel to.
     *
     * @param toTravelTo the to travel to
     */
    public void travelTo(Planet toTravelTo) {
        try {
            TravelProcessor.validateTraveling(player, toTravelTo);

        } catch (TravelException e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
            playerShip.setFuelTooLow(true);
        }
    }

    /**
     * Player attacked.
     */
    public void playerAttacked() {
        TravelProcessor.playerAttackedDuringTravel(player, RandomEventActivity.getAttacked());
    }

    /**
     * Gets player credits.
     *
     * @return the player credits
     */
    public double getPlayerCredits() {
        return player.getCredits();
    }

    /**
     * Is fuel too low boolean.
     *
     * @return the boolean
     */
    public boolean isFuelTooLow() {
        return playerShip.isFuelTooLow();
    }


    /**
     * Save player.
     */
    public void savePlayer() {
        try {
            DataStore.playerToJson(getApplication(), player);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
