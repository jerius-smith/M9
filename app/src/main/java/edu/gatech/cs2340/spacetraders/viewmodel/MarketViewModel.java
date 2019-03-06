package edu.gatech.cs2340.spacetraders.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.model.Market;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Skills;
import edu.gatech.cs2340.spacetraders.model.TransactionProcessor;
import processing.core.PApplet;

/**
 * The type Configuration view model.
 */
public class MarketViewModel extends AndroidViewModel {

    private Player player;
    private Market market;

    /**
     * Instantiates a new Configuration view model.
     *
     * @param application the application
     */
    public MarketViewModel(@NonNull Application application) {
        super(application);
        try {
            player = DataStore.getCurrentPlayer(getApplication());
            market = player.getLocation().getPlanetsMarket();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Inventory getMarketInventory() {
        return market.getMarketInventory();
    }

    public Inventory getPlayerInventory() {
        return player.getInventory();
    }

    public double sellItem(Good good) {
        TransactionProcessor.sellItem(player, good, market);
        return player.getCredits();
    }

    public double buyItem(Good good) {
        TransactionProcessor.buyItem(player, good, market);
        return player.getCredits();
    }

    public double getPlayerCredits() { return player.getCredits(); }

    public void savePlayer() {
        try {
            DataStore.playerToJson(getApplication(), player);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}