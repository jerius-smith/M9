package edu.gatech.cs2340.spacetraders.model;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import edu.gatech.cs2340.spacetraders.viewmodel.ConfigurationViewModel;

/**
 * The type Model facade.
 */
public class ModelFacade {
    private static final ModelFacade ourInstance = new ModelFacade();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ModelFacade getInstance() {
        return ourInstance;
    }

    private Player player;

    private ModelFacade() {
    }

    /**
     * Create player.
     *
     * @param name           the name
     * @param prefDifficulty the pref difficulty
     * @param skillPoints    the skill points
     */
    public void createPlayer(String name, Difficulty prefDifficulty, Skills[] skillPoints) {
        if (player == null) {
            player = new Player(name, prefDifficulty, skillPoints);
        } else {
            player.setName(name);
            player.setPreferredDifficulty(prefDifficulty);
            player.setSkills(skillPoints);
        }
        Log.d("PLAYER", "\n" + player.toString());
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
}
