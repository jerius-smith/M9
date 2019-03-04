package edu.gatech.cs2340.spacetraders.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.FileOutputStream;

import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Inventory;
import edu.gatech.cs2340.spacetraders.model.Market;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Skills;

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
//        player = DataStore.getCurrentPLayer();
//        market = player.getLocation().getPlanetsMarket();
    }

    public Inventory getMarketInventory() {
        return market.getMarketInventory();
    }

    public Inventory getPlayerInventory() {
        return player.getInventory();
    }

}