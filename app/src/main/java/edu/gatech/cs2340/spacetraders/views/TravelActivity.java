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

    private ImageButton solarSystem1;
    private ImageButton solarSystem2;
    private ImageButton solarSystem3;

    private TravelViewModel travelViewModel;

    public static final String CHOSEN_PLANET = "PLANET";
    public static final String CHOSEN_SOLAR_SYSTEM = "SOLAR_SYSTEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        solarSystem1 = findViewById(R.id.solarsystem1);
        solarSystem2 = findViewById(R.id.solarsystem2);
        solarSystem3 = findViewById(R.id.solarsystem3);

        solarSystem1.setOnClickListener(this::solarSystemClicked);
        solarSystem2.setOnClickListener(this::solarSystemClicked);
        solarSystem3.setOnClickListener(this::solarSystemClicked);
    }

    public void solarSystemClicked(View view) {
        switch (view.getId()) {
            case R.id.solarsystem1:
                showDestinations(Universe.getInstance().getSolarSystemByName(GameLogistics.SOLAR_SYSTEM_NAMES[0]));
                break;
            case R.id.solarsystem2:
                showDestinations(Universe.getInstance().getSolarSystemByName(GameLogistics.SOLAR_SYSTEM_NAMES[1]));
                break;
            case R.id.solarsystem3:
                showDestinations(Universe.getInstance().getSolarSystemByName(GameLogistics.SOLAR_SYSTEM_NAMES[2]));
                break;
            default:
                showDestinations(Universe.getInstance().getRandomSolarSystem());
                break;
        }
    }



    public void showDestinations(SolarSystem solarSystem) {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        Set<Planet> planets = solarSystem.getPlanets();
        String[] planetNames = planets.stream().map(Planet::getName).toArray(String[]::new);
        builder.setTitle("Select a planet from solar system: " + solarSystem.getName())
                .setItems(planetNames, (dialog, which) -> {
                    String selectedPlanet = planetNames[which];
                    Intent intent = new Intent();
                    intent.putExtra(CHOSEN_PLANET, selectedPlanet);
                    intent.putExtra(CHOSEN_SOLAR_SYSTEM, solarSystem.getName());
                    setResult(PlanetActivity.PLANET_REQUEST, intent);
                    dialog.dismiss();
                    finish();
                })
                .setCancelable(false);
        builder.show();
    }


}
