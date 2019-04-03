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

    public String getPlayerLocation() {
        Planet planet = player.getLocation();
        return planet.getName();
    }

    public int getShipFuel() {
        int actualFuel = playerShip.getFuelCapacity();
        @SuppressWarnings("MagicNumber") int mappedFuel = (int) GameLogistics.mapValues(actualFuel, 0, 80, 0, 100);
        return mappedFuel;
    }

    public void travelTo(Planet toTravelTo) {
        try {
            TravelProcessor.validateTraveling(player, toTravelTo);

        } catch (TravelException e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
            playerShip.setFuelTooLow(true);
        }
    }

    public void playerAttacked() {
        TravelProcessor.playerAttackedDuringTravel(player, RandomEventActivity.attacked);
    }

    public double getPlayerCredits() {
        return player.getCredits();
    }

    public boolean isFuelTooLow() {
        return playerShip.isFuelTooLow();
    }


    public void savePlayer() {
        try {
            DataStore.playerToJson(getApplication(), player);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
