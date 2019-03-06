package edu.gatech.cs2340.spacetraders.views;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Skills;
import edu.gatech.cs2340.spacetraders.model.TransactionProcessor;
import edu.gatech.cs2340.spacetraders.viewmodel.ConfigurationViewModel;

/**
 * The type Configuration activity.
 */
public class ConfigurationActivity extends AppCompatActivity {


    private TextView points;
    private EditText nameInput;

    private Spinner difficultySpinner;
    private ImageButton setupPlayer;
    private EditText[] skillsArr = new EditText[4];
    private Skills[] skills;

    /**
     * The View model.
     */
    ConfigurationViewModel viewModel;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        points = (TextView) findViewById(R.id.skill_points);
        nameInput = (EditText) findViewById(R.id.name_input);

        difficultySpinner = (Spinner) findViewById(R.id.difficulty_spinner);
        setupPlayer = findViewById(R.id.start_bttn);

        skillsArr[0] = findViewById(R.id.pilot_skills);
        skillsArr[1] = findViewById(R.id.fighter_skills);
        skillsArr[2] = findViewById(R.id.trader_skills);
        skillsArr[3] = findViewById(R.id.engineer_skills);

        skills = Skills.values();


        ArrayAdapter<Difficulty> adapter = new ArrayAdapter<Difficulty>(this,
                                                           android.R.layout.simple_spinner_item,
                                                           Difficulty.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        difficultySpinner.setSelection(0);


        skillsArr[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    updatePoints();
                }
            }
        });

        skillsArr[1].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    updatePoints();
                }
            }
        });

        skillsArr[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    updatePoints();
                }
            }
        });

        skillsArr[3].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    updatePoints();
                }
            }
        });


        setupPlayer.setOnClickListener(view -> {
            updatePoints();
            viewModel = ViewModelProviders.of(this).get(ConfigurationViewModel.class);
            String name = nameInput.getText().toString();
            Difficulty difficulty = (Difficulty) difficultySpinner.getSelectedItem();
            boolean isValid = viewModel.isValidPlayer(name, difficulty, skills);

            if(isValid) {
                Intent intent = new Intent(ConfigurationActivity.this, WelcomeActivity.class);
                intent.putExtra("PLAYER_NAME", name);
                startActivity(intent);
                simulateMarketActivity();
            }

        });
    }

    private void simulateMarketActivity() {
        Player player = ModelFacade.getInstance().getPlayer();
        Log.d("PLAYER_INVENTORY", player.getInventory().toString());
        Log.d("MARKET_INVENTORY",
              player.getLocation().getPlanetsMarket().getMarketInventory().toString());
        Log.d("TRANSACTIONS", "Player's Inventory: " + player.getInventory().toString());
        Log.d("TRANSACTIONS",
              "Market Inventory: " + player.getLocation().getPlanetsMarket().getMarketInventory().toString());
        TransactionProcessor.buyItem(player, Good.WATER, player.getLocation().getPlanetsMarket());
        Log.d("TRANSACTIONS", "Player's Inventory: " + player.getInventory().toString());
        Log.d("TRANSACTIONS", "Player's credits: " + player.getCredits());
        Log.d("TRANSACTIONS",
              "Market Inventory: " + player.getLocation().getPlanetsMarket().getMarketInventory().toString());

    }

    private void updatePoints() {

        int sum = 0;

        for (int i = 0; i < skills.length; i++) {
            String currentPoints = skillsArr[i].getText().toString();
            if (currentPoints.isEmpty()) {
                skillsArr[i].setText("0");
                currentPoints = skillsArr[i].getText().toString();
            }
            skills[i].setPoints(Integer.parseInt(currentPoints));
            sum += skills[i].getPoints();
        }

        if (sum <= 16) {
            points.setText(Integer.toString(16 - sum));
        } else {
            points.setText(Integer.toString(16 - sum));
            Toast.makeText(getApplicationContext(), "You've used more points than available.",
                    Toast.LENGTH_LONG).show();
        }

    }


}
