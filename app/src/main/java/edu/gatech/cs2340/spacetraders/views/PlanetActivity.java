package edu.gatech.cs2340.spacetraders.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.entities.TravelException;
import edu.gatech.cs2340.spacetraders.entities.TravelProcessor;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;
import edu.gatech.cs2340.spacetraders.model.Universe;
import edu.gatech.cs2340.spacetraders.viewmodel.MarketViewModel;

public class PlanetActivity extends AppCompatActivity {

    private ImageButton marketBttn;
    private ImageButton travelBtn;
    private TextView playerCredits;
    private TextView location;

    private MarketViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        marketBttn = findViewById(R.id.market_button);
        travelBtn = findViewById(R.id.travel_button);
        playerCredits = findViewById(R.id.credits_text);
        location = findViewById(R.id.location_text);

        updatePlayerStatus();

        marketBttn.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetActivity.this, MarketActivity.class);
            startActivity(intent);
        });

        travelBtn.setOnClickListener(view -> {
            testTraveling();
            updatePlayerStatus();
        });
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
                break;
            }
        }
        Log.d(travelTag, String.format("Traveling to %s from %s", destination.getName(),
                                       currPlayer.getLocation().getName()));
        try {
            Log.d(travelTag, "Before traveling: " + currPlayer.getShip().toString());
            TravelProcessor.validateTraveling(currPlayer, destination);
        } catch (TravelException e) {
            Log.d(travelTag, e.getMessage());
        } finally {
            Ship ship = currPlayer.getShip();
            System.out.println(ship);
            Log.d(travelTag, "After attempted traveling: " + currPlayer.getShip().toString());
        }
    }

    private void updatePlayerStatus() {
        viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);

        playerCredits.setText(String.format("Credits: %.2f", viewModel.getPlayerCredits()));
        location.setText(String.format("Location: %s", viewModel.getPlayerLocation()));
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        updatePlayerStatus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
