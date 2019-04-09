package edu.gatech.cs2340.spacetraders.views;

import android.os.Bundle;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.support.v7.app.AppCompatActivity;

import edu.gatech.cs2340.spacetraders.model.Asteroids;
import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;

/**
 * The type Mini game activity.
 */
public class MiniGameActivity extends AppCompatActivity {
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                         ViewGroup.LayoutParams.MATCH_PARENT));
        sketch = new Asteroids();
        sketch.setExternal(true);
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (sketch != null) {
            sketch.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (sketch != null) {
            sketch.onBackPressed();
        }
    }
}
