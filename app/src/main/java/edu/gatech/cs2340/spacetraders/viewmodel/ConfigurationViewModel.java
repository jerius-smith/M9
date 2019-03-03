package edu.gatech.cs2340.spacetraders.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.Set;

import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Skills;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;

/**
 * The type Configuration view model.
 */
public class ConfigurationViewModel extends AndroidViewModel {

    private ModelFacade facade;

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
        if (name == null || name.isEmpty()) {
            showToast("Player cannot be configured. Please enter a name", Toast.LENGTH_LONG);
        } else if (prefDifficulty == null) {
            showToast("Player cannot be configured. Please select difficulty", Toast.LENGTH_LONG);
        } else if (Skills.totalPoints() != Skills.MAX_POINTS) {
            showToast("Player cannot be configured. Please allocate all the points: " + Skills.totalPoints(), Toast.LENGTH_LONG);
        } else {
            facade.createPlayer(name, prefDifficulty, skillPoints, getDefaultPlanet());
            showToast(facade.getPlayer().toString(), 5000);
            return true;
        }
        return false;
    }

    private Planet getDefaultPlanet() {
        Set<SolarSystem> solarSystems = facade.getUniverse().getSolarSystems();
        Set<Planet> planets;
        for (SolarSystem system : solarSystems) {
            planets = system.getPlanets();
            for (Planet planet : planets) {
                String name = planet.getName();

                if (name.equals("Vandor")) {
                    return planet;
                }
            }
        }

        return new Planet("Vandor");
    }


    public void jsonifyUniverse(Context context) {
        String name = facade.getPlayer().getName().toLowerCase();
        String filename = name + "_universe.json";
        String fileContents = facade.getUniverse().toJSONString();
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void jsonifyPlayer(Context context) {
        String name = facade.getPlayer().getName().toLowerCase();
        String filename = name + "_player.json";
        String fileContents = facade.getPlayer().toJSONString();
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message, int duration) {
        Toast.makeText(getApplication(), message, duration).show();
    }
}
