package edu.gatech.cs2340.spacetraders.views;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.crowdfire.cfalertdialog.views.CFPushButton;

import java.util.Arrays;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Good;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Skills;
import edu.gatech.cs2340.spacetraders.entities.TransactionProcessor;
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

    private Button load;

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

        load = findViewById(R.id.load_game);

        skills = Skills.values();


        ArrayAdapter<Difficulty> adapter =
                new ArrayAdapter<Difficulty>(this, android.R.layout.simple_spinner_item,
                                             Difficulty.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        difficultySpinner.setSelection(0);


        skillsArr[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updatePoints();
                }
            }
        });

        skillsArr[1].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updatePoints();
                }
            }
        });

        skillsArr[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updatePoints();
                }
            }
        });

        skillsArr[3].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
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

            if (isValid) {
                launchWelcomeScreen(name);
            }

        });

        load.setOnClickListener(view -> {
            loadGameDialog(getApplicationContext());
        });
    }

    private void launchWelcomeScreen(String name) {
        Intent intent = new Intent(ConfigurationActivity.this, WelcomeActivity.class);
        intent.putExtra("PLAYER_NAME", name);
        startActivity(intent);
    }

    private void loadGameDialog(Context context) {
        String[] selectFrom = DataStore.getSavedPlayerNames(context);
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        if (selectFrom.length > 0) {
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                    .setTitle("Load Game")
                    .setMessage("Select the game state").setItems(selectFrom, (dialog, index) -> {
                    dialog.dismiss();
                    DataStore.setCurrentPlayerText(context, selectFrom[index]);
                    launchWelcomeScreen(selectFrom[index]);
                    }).setTextGravity(Gravity.CENTER_HORIZONTAL).setIcon(R.drawable.gnat)
                    .setCancelable(false)
                    .addButton("Cancel", Color.WHITE, Color.RED,
                               CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                               CFAlertDialog.CFAlertActionAlignment.CENTER,
                               (dialog, which) -> dialog.dismiss())
                    .addButton("Delete All", Color.WHITE, Color.RED,
                               CFAlertDialog.CFAlertActionStyle.POSITIVE,
                               CFAlertDialog.CFAlertActionAlignment.CENTER,
                               (dialog, which) -> {
                                DataStore.deletePlayerAndUniverse(getApplicationContext());
                                dialog.dismiss();
                               });
        } else {
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                    .setTitle("Error Loading Save Data")
                    .setMessage("No previous game saves to load.")
                    .addButton("OK", Color.WHITE,
                               ContextCompat.getColor(context, R.color.light_turq),
                               CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                               CFAlertDialog.CFAlertActionAlignment.CENTER,
                               (dialog, index) -> dialog.dismiss())
                    .setCancelable(true)
                    .setAutoDismissAfter(5000);
        }
        builder.show();
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
