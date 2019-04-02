package edu.gatech.cs2340.spacetraders.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.crowdfire.cfalertdialog.CFAlertDialog;

import java.util.Set;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.entities.GameLogistics;
import edu.gatech.cs2340.spacetraders.model.Planet;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;
import edu.gatech.cs2340.spacetraders.model.Universe;
import edu.gatech.cs2340.spacetraders.viewmodel.TravelViewModel;

public class TravelActivity extends AppCompatActivity {

    private TravelViewModel travelViewModel;

    public static final String CHOSEN_PLANET = "PLANET";
    public static final String CHOSEN_SOLAR_SYSTEM = "SOLAR_SYSTEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        ImageButton solarSystem1 = findViewById(R.id.solarsystem1);
        ImageButton solarSystem2 = findViewById(R.id.solarsystem2);
        ImageButton solarSystem3 = findViewById(R.id.solarsystem3);

        solarSystem1.setOnClickListener(this::solarSystemClicked);
        solarSystem2.setOnClickListener(this::solarSystemClicked);
        solarSystem3.setOnClickListener(this::solarSystemClicked);
    }

    private void solarSystemClicked(View view) {
        Universe universe = Universe.getInstance();
        switch (view.getId()) {
            case R.id.solarsystem1:
                showDestinations(universe.getSolarSystemByName(
                        GameLogistics.SOLAR_SYSTEM_NAMES[0]));
                break;
            case R.id.solarsystem2:
                showDestinations(universe.getSolarSystemByName(
                        GameLogistics.SOLAR_SYSTEM_NAMES[1]));
                break;
            case R.id.solarsystem3:
                showDestinations(universe.getSolarSystemByName(
                        GameLogistics.SOLAR_SYSTEM_NAMES[2]));
                break;
            default:
                showDestinations(universe.getRandomSolarSystem());
                break;
        }
    }


    private void showDestinations(SolarSystem solarSystem) {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        Set<Planet> planets = solarSystem.getPlanets();
        String[] planetNames = planets.stream().map(Planet::getName).toArray(String[]::new);
        String solarSystemName = solarSystem.getName();
        builder.setTitle("Select a planet from solar system: " + solarSystemName)
                .setItems(planetNames, (dialog, which) -> {
                    String selectedPlanet = planetNames[which];
                    Intent intent = new Intent();
                    intent.putExtra(CHOSEN_PLANET, selectedPlanet);
                    intent.putExtra(CHOSEN_SOLAR_SYSTEM, solarSystemName);
                    setResult(PlanetActivity.PLANET_REQUEST, intent);
                    dialog.dismiss();
                    finish();
                }).setCancelable(false);
        builder.show();
    }


}
