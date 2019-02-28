package edu.gatech.cs2340.spacetraders.views;

import processing.core.*;


public class UniverseSketch extends PApplet {


    public void settings() {
        size(500,500);
        PImage solar2 = loadImage("../res/drawable/SolarSystem2.png");
        PImage solar3 = loadImage("../res/drawable/SolarSystem3.png");
        PImage solar4 = loadImage("../res/drawable/SolarSystem3.png");


    }

    public void setup() {
    }

    public void draw() {
        background(0, 255, 0);
    }
}
