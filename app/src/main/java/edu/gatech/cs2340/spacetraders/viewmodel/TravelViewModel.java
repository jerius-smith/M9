package edu.gatech.cs2340.spacetraders.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;

import edu.gatech.cs2340.spacetraders.entities.MarketActivityException;
import edu.gatech.cs2340.spacetraders.entities.TravelException;
import edu.gatech.cs2340.spacetraders.entities.TravelProcessor;
import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.model.Market;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.entities.TransactionProcessor;
import edu.gatech.cs2340.spacetraders.model.Ship;

/**
 * The type Travel view model.
 */
public class TravelViewModel extends AndroidViewModel {

    private Player player;
    private Market market;
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
            market = player.getLocation().getPlanetsMarket();
            playerShip = player.getShip();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerLocation() {
        return player.getLocation().getName();
    }

    public Ship getPlayerShip() {
        return playerShip;
    }

    public int getShipFuel() {
        return playerShip.getFuelCapacity();
    }

    public void travelTo(Planet toTravelTo) {
        try {
            TravelProcessor.validateTraveling(player, toTravelTo);
        } catch (TravelException e) {
            Log.d("TRAVEL", "Exception: " + e.getMessage());
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void savePlayer() {
        try {
            DataStore.playerToJson(getApplication(), player);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
