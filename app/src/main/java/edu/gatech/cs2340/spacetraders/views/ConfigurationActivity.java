package edu.gatech.cs2340.spacetraders.views;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.DataStore;
import edu.gatech.cs2340.spacetraders.model.Difficulty;
import edu.gatech.cs2340.spacetraders.model.Skills;
import edu.gatech.cs2340.spacetraders.viewmodel.ConfigurationViewModel;

/**
 * The type Configuration activity.
 */
public class ConfigurationActivity extends AppCompatActivity {


    private static final int DURATION = 5000;
    private TextView points;
    private EditText nameInput;

    private Spinner difficultySpinner;
    private final EditText[] skillsArr = new EditText[4];
    private Skills[] skills;

    /**
     * The View model.
     */
    private ConfigurationViewModel viewModel;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        points = findViewById(R.id.skill_points);
        nameInput = findViewById(R.id.name_input);

        difficultySpinner = findViewById(R.id.difficulty_spinner);
        ImageButton setupPlayer = findViewById(R.id.start_bttn);

        skillsArr[0] = findViewById(R.id.pilot_skills);
        skillsArr[1] = findViewById(R.id.fighter_skills);
        skillsArr[2] = findViewById(R.id.trader_skills);
        skillsArr[3] = findViewById(R.id.engineer_skills);

        Button load = findViewById(R.id.load_game);

        skills = Skills.values();


        ArrayAdapter<Difficulty> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Difficulty.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        difficultySpinner.setSelection(0);


        skillsArr[0].setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updatePoints();
            }
        });

        skillsArr[1].setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updatePoints();
            }
        });

        skillsArr[2].setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updatePoints();
            }
        });

        skillsArr[3].setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                updatePoints();
            }
        });


        setupPlayer.setOnClickListener(view -> {
            updatePoints();
            ViewModelProvider vmProvide = ViewModelProviders.of(this);
            viewModel = vmProvide.get(ConfigurationViewModel.class);
            CharSequence nameText = nameInput.getText();
            String name = nameText.toString();
            Difficulty difficulty = (Difficulty) difficultySpinner.getSelectedItem();
            boolean isValid = viewModel.isValidPlayer(name, difficulty, skills);

            if (isValid) {
                launchWelcomeScreen(name);
            }

        });

        load.setOnClickListener(view -> loadGameDialog(getApplicationContext()));
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
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTitle("Load Game");
            builder.setMessage("Select the game state");
            builder.setItems(selectFrom, (dialog, index) -> {
                    dialog.dismiss();
                    DataStore.setCurrentPlayerText(context, selectFrom[index]);
                    launchWelcomeScreen(selectFrom[index]);
                    });
            builder.setTextGravity(Gravity.CENTER_HORIZONTAL);
            builder.setIcon(R.drawable.gnat);
            builder.setCancelable(false);
            builder.addButton("Cancel", Color.WHITE, Color.RED,
                               CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                               CFAlertDialog.CFAlertActionAlignment.CENTER,
                               (dialog, which) -> dialog.dismiss());
            builder.addButton("Delete All", Color.WHITE, Color.RED,
                               CFAlertDialog.CFAlertActionStyle.POSITIVE,
                               CFAlertDialog.CFAlertActionAlignment.CENTER,
                               (dialog, which) -> {
                                DataStore.deletePlayerAndUniverse(getApplicationContext());
                                dialog.dismiss();
                               });
        } else {
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTitle("Error Loading Save Data");
            builder.setMessage("No previous game saves to load.");
            builder.addButton("OK", Color.WHITE,
                               ContextCompat.getColor(context, R.color.light_turq),
                               CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                               CFAlertDialog.CFAlertActionAlignment.CENTER,
                               (dialog, index) -> dialog.dismiss());
            builder.setCancelable(true);
            builder.setAutoDismissAfter(DURATION);
        }
        builder.show();
    }

    private void updatePoints() {

        int sum = 0;

        for (int i = 0; i < skills.length; i++) {
            CharSequence skillText =skillsArr[i].getText();
            String currentPoints = skillText.toString();
            if (currentPoints.isEmpty()) {
                skillsArr[i].setText("0");
                skillText =skillsArr[i].getText();
                currentPoints = skillText.toString();
            }
            skills[i].setPoints(Integer.parseInt(currentPoints));
            sum += skills[i].getPoints();
        }

        if (sum <= Skills.MAX_POINTS) {
            points.setText(Integer.toString(Skills.MAX_POINTS - sum));
        } else {
            points.setText(Integer.toString(Skills.MAX_POINTS - sum));
            Toast toast = Toast.makeText(getApplicationContext(),
                                         "You've used more points than available.",
                           Toast.LENGTH_LONG);
            toast.show();
        }

    }


}
