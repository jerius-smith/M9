package edu.gatech.cs2340.spacetraders.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.io.FileNotFoundException;
import java.util.Set;

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
    private TextView fuel;
    private CircularFillableLoaders fuel_level;

    private MarketViewModel viewModel;
    private TravelViewModel travelViewModel;

    public static final int PLANET_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        marketBttn = findViewById(R.id.market_button);
        travelBtn = findViewById(R.id.travel_button);
        playerCredits = findViewById(R.id.credits_text);
        location = findViewById(R.id.location_text);
        fuel = findViewById(R.id.fuel_text);
        fuel_level = findViewById(R.id.fuel_level);

        updatePlayerStatus();
        updateTravelStatus();

        marketBttn.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetActivity.this, MarketActivity.class);
            startActivity(intent);
        });

        travelBtn.setOnClickListener(view -> {
            startActivityForResult(new Intent(getApplicationContext(), TravelActivity.class), PLANET_REQUEST);
            //testTraveling();
        });
    }

    public void travelTo(String planet, String solarSystem) {
        String travelTag = "TRAVEL";
//        Universe universe = Universe.getInstance();
//        SolarSystem rand = universe.getRandomSolarSystem();
        Planet toTravelTo = Universe.getInstance().getSolarSystemByName(solarSystem).getPlanetByName(planet);
        travelViewModel.travelTo(toTravelTo);
        updateTravelStatus();
        Log.d(travelTag, "Traveling to: " + travelViewModel.getPlayerLocation());
        Log.d(travelTag, "Ship fuel: " + travelViewModel.getShipFuel());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLANET_REQUEST) {
            String selectedPlanet = data.getStringExtra(TravelActivity.CHOSEN_PLANET);
            String selectedSolarSystem = data.getStringExtra(TravelActivity.CHOSEN_SOLAR_SYSTEM);
            travelTo(selectedPlanet, selectedSolarSystem);
        }
    }



    private void updatePlayerStatus() {
        viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);
        playerCredits.setText(String.format("Credits: %.2f", viewModel.getPlayerCredits()));
        viewModel.savePlayer();
    }

    private void updateTravelStatus() {
        travelViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);
        location.setText(String.format("Location: %s", travelViewModel.getPlayerLocation()));
        int mappedFuelLevel = travelViewModel.getShipFuel();
        fuel.setText(String.valueOf(mappedFuelLevel));
        fuel_level.setProgress(100 - travelViewModel.getShipFuel());
        if (mappedFuelLevel >= 60) {
            fuel.setTextColor(Color.GREEN);
        } else if (travelViewModel.isFuelTooLow()) {
            fuel.setTextColor(Color.RED);
            fuel.bringToFront();
        } else {
            fuel.setTextColor(Color.YELLOW);
        }
        Log.d("TRAVEL", String.valueOf(100 - travelViewModel.getShipFuel()));
        fuel_level.setProgress(100 - travelViewModel.getShipFuel(), 800);
        travelViewModel.savePlayer();
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        updatePlayerStatus();
        updateTravelStatus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
