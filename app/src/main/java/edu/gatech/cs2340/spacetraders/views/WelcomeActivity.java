package edu.gatech.cs2340.spacetraders.views;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.gatech.cs2340.spacetraders.R;

public class WelcomeActivity extends AppCompatActivity {

    AnimationDrawable starsAnimation;
    TextView welcomeText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content_welcome);

        starsAnimation = (AnimationDrawable) relativeLayout.getBackground();
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome Commander! \n We're excited you have decided to begin your " +
                "journey through the Space Tader Universe!");

        starsAnimation.start();
    }
}
