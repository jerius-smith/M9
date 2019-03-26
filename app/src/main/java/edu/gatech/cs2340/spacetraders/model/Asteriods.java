package edu.gatech.cs2340.spacetraders.model;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

//import gifAnimation.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Asteriods extends PApplet {



    ArrayList<Asteriod> asteriods;
    Ship ship;
    boolean isShooting;
//    Gif explode;
//    ArrayList<Explosions> explosions;

    public void setup() {

        //fullScreen();
        asteriods = new ArrayList<Asteriod>();
//        explosions = new ArrayList<Explosions>();
        ship = new Ship(null);
        addAsteriods(1, "in");
//  explode = new Gif(this, "explosion.gif");
//  explode.play();
    }

    public void addAsteriods(int num, String inOut) {
        for (int n = 0; n < num; n++) {
            PVector loc;
            if (inOut.equals("in"))
                loc = new PVector(random(-100, width+100), random(-100, height+100));
            else
                loc = (new PVector(random(-120, 0), random(height, height+120)));
            while (loc.dist(ship.pos) < 50) {
                loc = new PVector(random(width), random(height));
            }
            asteriods.add(new Asteriod(loc, 0));
        }
    }

    public void draw() {
        background(0);


        if (asteriodDeficiency()) addAsteriods(PApplet.parseInt(random(5)), "out");

        for (Asteriod curr : asteriods) {
            curr.edges();
            curr.update();
            curr.show();
        }

//  for (Explosions e : explosions) {
//    if (!e.exploded())
//      e.startExplosion();
//    e.showExplosion();
//  }
//
//  for (int i = 0; i < explosions.size(); i++) {
//    Explosions curr = explosions.get(i);
//    if (curr.hasTimeEnded()) {
//      explosions.remove(curr);
//      i--;
//    }
//  }

        shipStuff();
    }

    public boolean asteriodDeficiency() {
        return (asteriods.size() <= 1);
    }

    public void shipStuff() {
        ship.thrust();
        ship.slowDown();
        ship.edges();
        ship.update();
        ship.turn();
        ship.show();
    }

    public void keyPressed() {
        if (keyCode == UP) {
            ship.slowDown = false;
            ship.thrust = true;
        }
        if (keyCode == RIGHT) {
            ship.setTurnAngle(.08f);
        }
        if (keyCode == LEFT) {
            ship.setTurnAngle(-.08f);
        }
        if (key == ' ') {
            ship.addBullet();
            isShooting = true;
        }
    }

    public void keyReleased() {
        if (keyCode == UP) {
            ship.slowDown = true;
            ship.thrust = false;
        }

        if (key == ' ')
            isShooting = false;

        ship.setTurnAngle(0);
    }


    public void mousePressed() {
        // a.add(new Asteriod(new PVector(mouseX, mouseY)));
        // explode.noLoop();
    }
    class Asteriod {
        PVector pos, vel;
        int numVertices;
        float[] radii;
        PShape asteriod;
        float offset, inc, rotateAng, angVel, maxRadius, avgRadius, threshold;

        Asteriod(PVector loc, float r) {
            pos = loc;
            numVertices = PApplet.parseInt(random(5, 13));
            maxRadius = (r==0) ? (random(125, 175)) : r;
            radii = new float[numVertices];
            inc = random(5);
            angVel = random(-.05f, .05f);
            threshold = 20;

            if (maxRadius < 50) {
                vel = PVector.sub(ship.pos, pos);
                vel.normalize();
            } else {
                vel = PVector.random2D();
            }
            vel.mult(random(1.3f, 3.3f));

            for (int i = 0; i < radii.length; i++) {
                radii[i] = noise(offset)*maxRadius;
                offset+=inc;
                avgRadius+=radii[i];
            }
            avgRadius = avgRadius / radii.length;

            createAsteriod();
        }

        public void update() {
            pos.add(vel);
            createAsteriod();
            rotateAng+=angVel;
        }

        public void edges() {
            if (pos.x > width+avgRadius) pos.x = -avgRadius;
            if (pos.x < -avgRadius) pos.x = width+avgRadius;
            if (pos.y < -avgRadius) pos.y = height+avgRadius;
            if (pos.y > height+avgRadius) pos.y = -avgRadius;
        }

        public void show() {
            pushMatrix();
            translate(pos.x, pos.y);
            rotate(rotateAng);
            shape(asteriod);
            popMatrix();
        }

        public void createAsteriod() {
            pushStyle();
            asteriod = createShape();
            asteriod.beginShape();
            asteriod.noFill();
            asteriod.stroke(255);
            asteriod.strokeWeight(2);
            for (int i = 0; i < numVertices; i++) {
                float x, y, angle = map(i, 0, numVertices, 0, TWO_PI);
                x = radii[i] * cos(angle);
                y = radii[i] * sin(angle);
                asteriod.vertex(x, y);
            }
            asteriod.endShape(CLOSE);
            popStyle();
        }
    }
    class Bullet {
        PVector pos, vel;
        float size, angle;
        int col;
        Bullet(PVector loc, float ang) {
            pos = loc;
            vel = PVector.fromAngle(ang-PI/2).mult(18);
            size = ship.space/8;
            col = color(0, 255, 0);
            angle = ang;
        }

        public void setPos(PVector pos) {
            this.pos = pos;
        }

        public void edges() {
            if (pos.x > width+size) pos.x = -size;
            if (pos.x < -size) pos.x = width+size;
            if (pos.y < -size) pos.y = height+size;
            if (pos.y > height+size) pos.y = -size;
        }

        public boolean outOfBounds() {
            return (pos.x < 0 || pos.x > width || pos.y < 0 || pos.y > height);
        }

        public boolean collideWithAsteriod(Asteriod a) {
            return (dist(pos.x, pos.y, a.pos.x, a.pos.y) < a.avgRadius);
        }

        public void update() {
            pos.add(vel);
        }

        public void show() {
            pushMatrix();
            translate(pos.x, pos.y);
            rotate(angle);
            pushStyle();
            noStroke();
            fill(col);
            ellipseMode(RADIUS);
            ellipse(0, 0, size, size);
            popStyle();
            popMatrix();
        }
    }
//    class Explosions {
//        PVector loc;
//        float millis;
//        float radius;
//        boolean exploded;
//        Explosions(PVector pos, float r) {
//            loc = pos;
//            millis = 1000000;
//            radius = r;
//        }
//
//        public boolean exploded() {
//            return exploded;
//        }
//
//        public void startExplosion() {
//            millis = millis();
//            exploded = true;
//        }
//
//        public void showExplosion() {
//            imageMode(CENTER);
//            image(explode, loc.x, loc.y, radius, radius);
//        }
//
//        public boolean hasTimeEnded() {
//            return (millis()-millis > 200);
//        }
//    }
    class Ship {
        PVector pos, vel, acc;
        ArrayList<Bullet> bullets;
        float space, angle, turnAngle, radius;
        PShape ship, head;
        PShape[] thrusters;
        boolean slowDown, thrust;

        Ship(PVector loc) {
            reset(loc);
        }

        public void reset(PVector loc) {
            pos = (loc==null) ? new PVector(width/2, height/2) : loc;
            vel = new PVector(0, 0);
            acc = new PVector(0, 0);
            space = 25;
            radius = space*.85f;

            bullets = new ArrayList<Bullet>();
            ship = createShape(GROUP);
            head = createShape(TRIANGLE, -space, space*.75f, space, space*.75f, 0, -space*1.2f);
            thrusters = new PShape[2];
            thrusters[0] = createShape(RECT, -space*.75f, space*.75f, 10, 20);
            thrusters[1] = createShape(RECT, space*.45f, space*.75f, 10, 20);
            head.setFill(0);
            head.setStroke(color(200, 0, 255));
            head.setStrokeWeight(2);
            for (PShape thruster : thrusters) {
                thruster.setFill(0);
                thruster.setStroke(0);
                thruster.setStrokeWeight(1.5f);
            }
            ship.addChild(head);
            ship.addChild(thrusters[0]);
            ship.addChild(thrusters[1]);
        }

        public void applyForce(PVector force) {
            acc.add(force);
            acc.mult(.8f);
        }

        public void update() {
            vel.add(acc);
            vel.limit(16);
            pos.add(vel);
            acc.mult(0);

            shipAsteriodCollision();
            bulletAsteriodCollision();
            bulletCheck();
        }

        public void shipAsteriodCollision() {
            for (int a = 0; a < asteriods.size(); a++) {
                Asteriod curr = asteriods.get(a);
                if (hit(curr)) {
                    asteriods.remove(curr);
                    reset(null);
                    break;
                }
            }
        }

        public void bulletAsteriodCollision() {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet curr = bullets.get(i);
                for (int j = 0; j < asteriods.size(); j++) {
                    Asteriod a = asteriods.get(j);
                    if (curr.collideWithAsteriod(a)) {
                        duplicateAsteroids(a);
                        asteriods.remove(a);
                        j--;
                        bullets.remove(curr);
                        i--;
//                        explosions.add(new Explosions(new PVector(a.pos.x, a.pos.y), a.maxRadius));
                        break;
                    }
                }
            }
        }

        public void duplicateAsteroids(Asteriod parent) {
            if (parent.maxRadius > parent.threshold) {
                float per = random(.4f, .6f);
                float r1 = parent.maxRadius*per, r2 = parent.maxRadius*(1-per);
                if (r1 > parent.threshold)  asteriods.add(new Asteriod(parent.pos.copy(), r1));
                if (r2 > parent.threshold)  asteriods.add(new Asteriod(parent.pos.copy(), r2));
            }
        }

        public void bulletCheck() {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet curr = bullets.get(i);
                if (curr.outOfBounds()) {
                    bullets.remove(curr);
                    i--;
                }
            }
        }

        public void show() {
            for (Bullet b : bullets) {
                // b.edges();
                b.update();
                b.show();
            }
            pushMatrix();
            translate(pos.x, pos.y);
            rotate(angle);
            shape(ship);
            popMatrix();
        }

        public boolean hit(Asteriod a) {
            return (dist(pos.x, pos.y, a.pos.x, a.pos.y) <= a.avgRadius+radius);
        }



        public void addBullet() {
            if (!isShooting) {
                PVector position = new PVector(pos.x, pos.y);
                bullets.add(new Bullet(position, angle));
            }
        }

        public void thrust() {
            if (thrust) {
                applyForce(PVector.fromAngle(angle-PI/2));
                thrusters[0].setFill(color(0, 255, 0, 180));
                thrusters[1].setFill(color(0, 255, 0, 180));
            } else {
                thrusters[0].setFill(0);
                thrusters[1].setFill(0);
            }
        }

        public void edges() {
            if (pos.x > width+space) pos.x = -space;
            if (pos.x < -space) pos.x = width+space;
            if (pos.y < -space) pos.y = height+space;
            if (pos.y > height+space) pos.y = -space;
        }


        public void setTurnAngle(float a) {
            turnAngle = a;
        }

        public void turn() {
            angle+=turnAngle;
        }

        public void slowDown() {
            if (slowDown) {
                vel.mult(.98f);
            }
        }
    }
    public void settings() { fullScreen(); }
}
