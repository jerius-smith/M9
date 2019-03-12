package edu.gatech.cs2340.spacetraders.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileNotFoundException;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.entities.TravelException;
import edu.gatech.cs2340.spacetraders.entities.TravelProcessor;
import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Ship;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;
import edu.gatech.cs2340.spacetraders.model.Universe;
import edu.gatech.cs2340.spacetraders.viewmodel.MarketViewModel;
import edu.gatech.cs2340.spacetraders.viewmodel.TravelViewModel;

public class PlanetActivity extends AppCompatActivity {

    private ImageButton marketBttn;
    private ImageButton travelBtn;
    private TextView playerCredits;
    private TextView location;

    private MarketViewModel viewModel;
    private TravelViewModel travelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        marketBttn = findViewById(R.id.market_button);
        travelBtn = findViewById(R.id.travel_button);
        playerCredits = findViewById(R.id.credits_text);
        location = findViewById(R.id.location_text);

        updatePlayerStatus();
        updateTravelStatus();

        marketBttn.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetActivity.this, MarketActivity.class);
            startActivity(intent);
        });

        travelBtn.setOnClickListener(view -> {
            testTraveling();
        });
    }

    public void testTraveling() {
        String travelTag = "TRAVEL";
        Universe universe = Universe.getInstance();
        SolarSystem rand = universe.getRandomSolarSystem();
        Planet toTravelTo = rand.getRandomPlanet();
        travelViewModel.travelTo(toTravelTo);
        updateTravelStatus();
        Log.d(travelTag, "Traveling to: " + travelViewModel.getPlayerLocation());
        Log.d(travelTag, "Ship fuel: " + travelViewModel.getShipFuel());
    }

    private void updatePlayerStatus() {
        viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);
        playerCredits.setText(String.format("Credits: %.2f", viewModel.getPlayerCredits()));
        viewModel.savePlayer();
    }

    private void updateTravelStatus() {
        travelViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);
        location.setText(String.format("Location: %s", travelViewModel.getPlayerLocation()));
        travelViewModel.savePlayer();
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
