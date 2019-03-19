package edu.gatech.cs2340.spacetraders.views;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.entities.GameLogistics;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;
import edu.gatech.cs2340.spacetraders.model.Universe;

/**
 * The type Welcome activity.
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * The Stars animation.
     */
   // AnimationDrawable starsAnimation;
    /**
     * The Welcome text.
     */
    TextView welcomeText;
    ImageButton continueBttn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);

        String playerName = getIntent().getStringExtra("PLAYER_NAME");

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content_welcome);

        //starsAnimation = (AnimationDrawable) relativeLayout.getBackground();
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        continueBttn = findViewById(R.id.continue_button);

        String welcomeMessage = String.format(
                "\"Welcome %s! \n We're excited you have " + "decided to begin your \n"
                + " journey through the Space " + "Trader Universe!\"", playerName);
        welcomeText.setText(welcomeMessage);

        //starsAnimation.start();

        continueBttn.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeActivity.this, PlanetActivity.class);
            startActivity(intent);
        });
       // testTraveling();
    }

    public void testTraveling() {
        String travelTag = "TRAVEL";
        Player currPlayer = ModelFacade.getInstance().getPlayer();
        //Log.d(travelTag, currPlayer.toString());
        Universe universe = Universe.getInstance();

        String solarSystemCurrentlyIn = currPlayer.getLocation().getSolarSystemCurrentlyIn();
        Log.d(travelTag, currPlayer.getLocation().toString());
        SolarSystem playerIn =
                universe.getSolarSystemByName(currPlayer.getLocation().getSolarSystemCurrentlyIn());
        Planet destination = null;
        for (Planet random : playerIn.getPlanets()) {
            if (!random.equals(currPlayer.getLocation())) {
                destination = random;
            }
        }
        Log.d(travelTag, String.format("Traveling to %s from %s", destination.getName(),
                                       currPlayer.getLocation().getName()));

    }
}
