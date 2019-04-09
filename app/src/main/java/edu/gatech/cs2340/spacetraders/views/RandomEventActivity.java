package edu.gatech.cs2340.spacetraders.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.gatech.cs2340.spacetraders.R;

/**
 * The type Random event activity.
 */
public class RandomEventActivity extends AppCompatActivity {

    private static boolean attacked;

    /**
     * Gets attacked.
     *
     * @return the attacked
     */
    public static boolean getAttacked() {
        return attacked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_event);

        List<WheelItem> wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.LTGRAY, BitmapFactory
                .decodeResource(getResources(), R.drawable.travel_bttn)));

        wheelItems.add(new WheelItem(Color.BLUE, BitmapFactory
                .decodeResource(getResources(), R.drawable.solarsystem1)));

        wheelItems.add(new WheelItem(Color.BLACK, BitmapFactory
                .decodeResource(getResources(), R.drawable.red_planet)));

        LuckyWheel eventWheel = findViewById(R.id.eventwheel);
        eventWheel.addWheelItems(wheelItems);

        Random random = new Random();
        int randIndex = random.nextInt(wheelItems.size()) + 1;
        eventWheel.rotateWheelTo(randIndex);

        eventWheel.setLuckyWheelReachTheTarget(() -> {
            Intent intent = new Intent();
            if (randIndex == 1) {
                Toast.makeText(getApplicationContext(), "YOU LUCKED OUT", Toast.LENGTH_LONG).show();
                attacked = false;
            } else if (randIndex == 2) {
                Toast.makeText(getApplicationContext(), "GET READY TO BE ATTACKED",
                               Toast.LENGTH_LONG).show();
                startActivity(new Intent(RandomEventActivity.this, AttackActivity.class));
                attacked = true;
            } else {
                Toast.makeText(getApplicationContext(), "ADVENTURE TIME", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RandomEventActivity.this, MiniGameActivity.class));
                attacked = false;
            }
            finish();
        });

    }

}
