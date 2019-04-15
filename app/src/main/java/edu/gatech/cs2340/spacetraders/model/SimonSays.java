package edu.gatech.cs2340.spacetraders.model;

import android.util.Log;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;
import processing.*;

import processing.sound.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class SimonSays extends PApplet {

    Button[] buttons;
    SimonToneGenerator tones;
    float space, pause;
    float[] btnFreq = {209, 252, 310, 415, 60};
    int[] sequence;
    boolean wrong, userTurn, startGame;
    int duration, seqCount, numSequence;

    public void setup() {

        space = width / 2;
        duration = 420;
        pause = 50;
        buttons = new Button[4];
        buttons[0] = new Button(0, 0, space, color(0, 255, 0), btnFreq[0], 0);
        buttons[1] = new Button(space, 0, space, color(255, 0, 0), btnFreq[1], 1);
        buttons[2] = new Button(space, space, space, color(255, 255, 0), btnFreq[2], 2);
        buttons[3] = new Button(0, space, space, color(0, 0, 255), btnFreq[3], 3);
        tones = new SimonToneGenerator(this, duration);
        numSequence = 1;
        randSequence();
        wrong = false;
    }

    public void randSequence() {
        sequence = new int[numSequence];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = PApplet.parseInt(random(4));
        }
    }

    public void addSequence() {
        sequence = append(sequence, PApplet.parseInt(random(4)));
        tones.setDuration(tones.maxDuration - 25 * sequence.length);
    }

    public void draw() {
        background(0);

        tones.checkMillis();
        if (startGame) {
            simonsTurn();
        }

        display();
    }

    public void simonsTurn() {
        if (!userTurn && seqCount < sequence.length) {
            if (millis() - tones.playMillis >= tones.maxDuration + pause) {
                Button curr = buttons[sequence[seqCount]];
                curr.setLight(true);
                tones.playTone(curr.freq);
                seqCount++;
                pause = 50;
            }
        } else if (!userTurn) {
            seqCount = 0;
            userTurn = true;
            tones.setDuration(duration);
        }
    }

    public boolean isWrong(int quadID) {
        int correctNum = sequence[seqCount];
        if (quadID == correctNum) {
            seqCount++;
            return false;
        } else {
            seqCount = sequence.length * 100;
            return true;
        }
    }

    public void display() {
        for (Button b : buttons) {
            b.display();
        }
    }

    public void reset(boolean wrong_) {
        seqCount = 0;
        if (!wrong_) {
            addSequence();
            pause = duration;
        } else {
            randSequence();
            pause = duration * 2;
        }
        userTurn = false;
    }

    public void mousePressed() {
        tones.stopTone();
        for (Button b : buttons) {
            if (b.clickedOn()) {
                b.setLight(true);
                wrong = (seqCount < sequence.length) ? isWrong(b.quadID) : true;
                if (seqCount == sequence.length) {
                    wrong = false;
                    reset(wrong);
                }
                if (wrong) {
                    tones.playTone(btnFreq[4]);
                    tones.stopTone();
                    exit();
                    reset(wrong);
                } else {
                    tones.playTone(b.freq);
                }
            }
        }
    }

    public void mouseReleased() {
        for (Button b : buttons) {
            if (b.clickedOn()) {
                b.setLight(false);
            }
        }
    }

    public void keyPressed() {

        startGame = true;
        Log.d("GAME", "started");

    }

    class Button {
        float x, y, size, freq;
        int dark, light;
        int quadID;
        boolean isLightOn = false;

        Button(float x_, float y_, float size_, int light_, float freq_, int quadID_) {
            x = x_;
            y = y_;
            size = size_;
            light = light_;
            freq = freq_;
            quadID = quadID_;
            dark = lerpColor(dark, light, .7f);
        }

        public void display() {
            stroke(0);
            strokeWeight(4);
            if (isLightOn) {
                fill(light);
            } else {
                fill(dark);
            }
            rectMode(CORNER);
            rect(x, y, size, size, 15);
        }

        public boolean isLightOn() {
            return isLightOn;
        }

        public void setLight(boolean change) {
            isLightOn = change;
        }

        public boolean clickedOn() {
            return (mouseX > x && mouseX < x + size && mouseY > y && mouseY < size + y);
        }
    }

    class SimonToneGenerator {

        SqrOsc tone; //'Saw' is a square wave oscillator: check on procesing, sound reference
        int maxDuration;
        float playMillis; //playMillis is the time when play button was pressed
        boolean isPlaying;

        //'p' refers to 'this' instance. Since the tone generator is used in an object class,
        // a reference of the main class: Simon_Says must be passed as a PApplet
        SimonToneGenerator(PApplet p, int maxDuration_) {
            tone = new SqrOsc(p);
            maxDuration = maxDuration_;
            isPlaying = false;
        }

        public void playTone(float frequency) {
            tone.amp(1);
            tone.freq(frequency);
            tone.play();
            isPlaying = true;

            playMillis = millis();
        }


        public void checkMillis() {
            int currMillis = millis();
            if (isPlaying && currMillis - playMillis > maxDuration) {
                stopTone();
                for (Button b : buttons) {
                    b.setLight(false);
                }
                isPlaying = false;
            }
        }

        public void stopTone() {
            tone.stop();
            // playMillis = 0;
        }

        public void setDuration(int duration_) {
            maxDuration = duration_;
        }
    }

    /*
    --> Sounds:
    Blue – 415 Hz – G#4 (true pitch 415.305 Hz)
    Yellow – 310 Hz – D#4 (true pitch 311.127 Hz)
    Red – 252 Hz ‐ B3 (true pitch 247.942 Hz)
    Green – 209 Hz – G#3 (true pitch 207.652 Hz)
    Wrong - 42 Hz

    Sequence length: 1‐5, tone duration 0.42 seconds, pause between tones 0.05 seconds
    Sequence length: 6‐13, tone duration 0.32 seconds, pause between tones 0.05 seconds
    Sequence length: 14‐31, tone duration 0.22 seconds, pause between tones 0.05 seconds

    --> Mechanics:
      -> Four buttons, each different color (green red yellow blue)
      -> Press each button and it lights up and play a sound for a certain duration
      -> When CPU plays a sequence, the buttons light up in the sequence and play a sound
      -> Sequence duration decrease after right answers
      -> If wrong, play a sound
      -> After each press, check if wrong

    */
    public void settings() {
        size(displayWidth, displayHeight);
    }
}
