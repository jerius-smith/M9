package edu.gatech.cs2340.spacetraders.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.FileNotFoundException;

import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Skills;
import edu.gatech.cs2340.spacetraders.model.Universe;

/**
 * The type Configuration view model.
 */
public class ConfigurationViewModel extends AndroidViewModel {

    private final ModelFacade facade;

    /**
     * Instantiates a new Configuration view model.
     *
     * @param application the application
     */
    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        facade = ModelFacade.getInstance();
    }

    /**
     * Is valid player.
     *
     * @param name           the name
     * @param prefDifficulty the pref difficulty
     * @param skillPoints    the skill points
     * @return true if validPlayer else it returns false
     */
    public boolean isValidPlayer(String name, Difficulty prefDifficulty, Skills[] skillPoints) {
        if ((name == null) || name.isEmpty()) {
            showToast("Player cannot be configured. Please enter a name");
        } else if (prefDifficulty == null) {
            showToast("Player cannot be configured. Please select difficulty");
        } else if (Skills.totalPoints() != Skills.MAX_POINTS) {
            showToast("Player cannot be configured. Please allocate all the points: " + Skills
                    .totalPoints());
        } else {
            facade.createPlayer(name, prefDifficulty, skillPoints, getDefaultPlanet());
            Player player = facade.getPlayer();
            DataStore.createCurrentPlayerTxt(getApplication(), player);
            DataStore.newPlayerToJson(getApplication(), player);
            try {
                DataStore.universeToJson(getApplication(), facade.getUniverse());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private Planet getDefaultPlanet() {
        return Universe.getRandomPlanet();
    }


    private void showToast(CharSequence message) {
        Toast toast = Toast.makeText(getApplication(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
