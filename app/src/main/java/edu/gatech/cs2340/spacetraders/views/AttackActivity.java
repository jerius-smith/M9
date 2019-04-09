package edu.gatech.cs2340.spacetraders.views;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import edu.gatech.cs2340.spacetraders.R;

/**
 * The type Attack activity.
 */
public class AttackActivity extends AppCompatActivity {

    /**
     * Decrease credits.
     *
     * @return the random int to decrease credits in attack
     */
    public static int getDecreaseCredits() {
        return (int) (Math.random() * 100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);

        VideoView videoView = findViewById(R.id.attack_video);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.attack_scene;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        videoView.setMediaController(null);
        videoView.setOnCompletionListener(mp -> finish());
    }
}
