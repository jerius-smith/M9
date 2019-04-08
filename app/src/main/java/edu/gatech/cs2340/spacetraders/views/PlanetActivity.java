package edu.gatech.cs2340.spacetraders.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.util.Map;
import java.util.Objects;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.SavedPlayer;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;
import edu.gatech.cs2340.spacetraders.model.Universe;
import edu.gatech.cs2340.spacetraders.viewmodel.MarketViewModel;
import edu.gatech.cs2340.spacetraders.viewmodel.TravelViewModel;

public class PlanetActivity extends AppCompatActivity {

//    public static final int INT = 60;
    private static final int MILLISECONDS = 800;
    private static final int MIN_FUEL_LEVEL = 60;
    private TextView playerCredits;
    private TextView location;
    private TextView fuel;
    private CircularFillableLoaders fuel_level;

    private TravelViewModel travelViewModel;

    public static final int PLANET_REQUEST = 1;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();

        ImageButton marketBttn = findViewById(R.id.market_button);
        ImageButton travelBtn = findViewById(R.id.travel_button);
        playerCredits = findViewById(R.id.credits_text);
        location = findViewById(R.id.location_text);
        fuel = findViewById(R.id.fuel_text);
        fuel_level = findViewById(R.id.fuel_level);
        ImageView shipImage = findViewById(R.id.shipImage);

        updatePlayerStatus();
        updateTravelStatus();

        marketBttn.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetActivity.this, MarketActivity.class);
            startActivity(intent);
        });

        travelBtn.setOnClickListener(view -> {
            startActivityForResult(new Intent(getApplicationContext(), TravelActivity.class),
                                   PLANET_REQUEST);
        });

        shipImage.setOnClickListener(view -> {
            try {
                Map<String, SavedPlayer> data = DataStore.getSavedPlayers(getApplicationContext());
                DatabaseReference myRef = database.getReference("players");
                for (String currentPlayer : data.keySet()) {
                    SavedPlayer player = data.get(currentPlayer);
                    myRef.child(Objects.requireNonNull(player).getName()).setValue(player);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void travelTo(String planet, String solarSystem) {
        SolarSystem solar = Universe.getSolarSystemByName(solarSystem);
        Planet toTravelTo = SolarSystem.getPlanetByName(solar,planet);
        travelViewModel.travelTo(toTravelTo);
        travelViewModel.playerAttacked();
        updateTravelStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PLANET_REQUEST) && (data != null)) {
            if (data.hasExtra(TravelActivity.CHOSEN_PLANET) && data
                    .hasExtra(TravelActivity.CHOSEN_SOLAR_SYSTEM)
            && data.hasExtra(TravelActivity.UPDATED_CREDITS)) {
                String selectedPlanet = data.getStringExtra(TravelActivity.CHOSEN_PLANET);
                String selectedSolarSystem =
                        data.getStringExtra(TravelActivity.CHOSEN_SOLAR_SYSTEM);
                travelTo(selectedPlanet, selectedSolarSystem);
            }
        }
    }


    private void updatePlayerStatus() {
        MarketViewModel viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);
        playerCredits.setText(String.format("Credits: %.2f", viewModel.getPlayerCredits()));
        viewModel.savePlayer();
    }

    private void updateTravelStatus() {
        travelViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);
        location.setText(String.format("Location: %s", travelViewModel.getPlayerLocation()));
        playerCredits.setText(String.format("Credits: %.2f", travelViewModel.getPlayerCredits()));
        int mappedFuelLevel = travelViewModel.getShipFuel();
        fuel.setText(String.valueOf(mappedFuelLevel));
        fuel_level.setProgress(100 - travelViewModel.getShipFuel());
        if (mappedFuelLevel >= MIN_FUEL_LEVEL) {
            fuel.setTextColor(Color.BLACK);
        } else if (travelViewModel.isFuelTooLow()) {
            fuel.setTextColor(Color.RED);
        } else {
            fuel.setTextColor(Color.YELLOW);
        }
        fuel_level.setProgress(100 - travelViewModel.getShipFuel(), MILLISECONDS);
        fuel.bringToFront();
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
