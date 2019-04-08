package edu.gatech.cs2340.spacetraders.model;

import processing.core.*;

//import gifAnimation.*;

import java.util.ArrayList;

/**
 * The type Asteroids.
 */
public class Asteroids extends PApplet {


    /**
     * The Asteroids.
     */
    private ArrayList<Asteroid> asteroids;
    /**
     * The Ship.
     */
    private Ship ship;
    /**
     * The Is shooting.
     */
    private boolean isShooting;
//    Gif explode;
//    ArrayList<Explosions> explosions;

    @Override
    public void setup() {

        //fullScreen();
        asteroids = new ArrayList<>();
//        explosions = new ArrayList<Explosions>();
        ship = new Ship();
        addAsteroids(1, "in");
//  explode = new Gif(this, "explosion.gif");
//  explode.play();
    }

    /**
     * Add asteroids.
     *
     * @param num   the num
     * @param inOut the in out
     */
    private void addAsteroids(int num, String inOut) {
        for (int n = 0; n < num; n++) {
            PVector loc;
            if ("in".equals(inOut)) {
                loc = new PVector(random(-100, width+100), random(-100, height+100));
            } else {
                loc = (new PVector(random(-120, 0), random(height, height+120)));
            }
            while (loc.dist(ship.pos) < 50) {
                loc = new PVector(random(width), random(height));
            }
            asteroids.add(new Asteroid(loc, 0));
        }
    }

    @Override
    public void draw() {
        background(0);


        if (asteriodDeficiency()) {
            addAsteroids(PApplet.parseInt(random(5)), "out");
        }

        for (Asteroid curr : asteroids) {
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

    /**
     * Asteriod deficiency boolean.
     *
     * @return the boolean
     */
    private boolean asteriodDeficiency() {
        return (asteroids.size() <= 1);
    }

    /**
     * Ship stuff.
     */
    private void shipStuff() {
        ship.thrust();
        ship.slowDown();
        ship.edges();
        ship.update();
        ship.turn();
        ship.show();
    }

    @Override
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

    @Override
    public void keyReleased() {
        if (keyCode == UP) {
            ship.slowDown = true;
            ship.thrust = false;
        }

        if (key == ' ') {
            isShooting = false;
        }

        ship.setTurnAngle(0);
    }


    @Override
    public void mousePressed() {
        // a.add(new Asteroid(new PVector(mouseX, mouseY)));
        // explode.noLoop();
    }

    /**
     * The type Asteroid.
     */
    class Asteroid {
        /**
         * The Pos.
         */
        final PVector pos;
        /**
         * The Vel.
         */
        final PVector vel;
        /**
         * The Num vertices.
         */
        final int numVertices;
        /**
         * The Radii.
         */
        final float[] radii;
        /**
         * The Asteroid.
         */
        PShape asteroid;
        /**
         * The Offset.
         */
        float offset;
        /**
         * The Inc.
         */
        final float inc;
        /**
         * The Rotate ang.
         */
        float rotateAng;
        /**
         * The Ang vel.
         */
        final float angVel;
        /**
         * The Max radius.
         */
        final float maxRadius;
        /**
         * The Avg radius.
         */
        float avgRadius;
        /**
         * The Threshold.
         */
        final float threshold;

        /**
         * Instantiates a new Asteroid.
         *
         * @param loc the loc
         * @param r   the r
         */
        Asteroid(PVector loc, float r) {
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

            createAsteroid();
        }

        /**
         * Update.
         */
        void update() {
            pos.add(vel);
            createAsteroid();
            rotateAng+=angVel;
        }

        /**
         * Edges.
         */
        void edges() {
            if (pos.x > (width + avgRadius)) {
                pos.x = -avgRadius;
            }
            if (pos.x < -avgRadius) {
                pos.x = width+avgRadius;
            }
            if (pos.y < -avgRadius) {
                pos.y = height+avgRadius;
            }
            if (pos.y > (height + avgRadius)) {
                pos.y = -avgRadius;
            }
        }

        /**
         * Show.
         */
        void show() {
            pushMatrix();
            translate(pos.x, pos.y);
            rotate(rotateAng);
            shape(asteroid);
            popMatrix();
        }

        /**
         * Create asteroid.
         */
        void createAsteroid() {
            pushStyle();
            asteroid = createShape();
            asteroid.beginShape();
            asteroid.noFill();
            asteroid.stroke(255);
            asteroid.strokeWeight(2);
            for (int i = 0; i < numVertices; i++) {
                float x;
                float y;
                float angle = map(i, 0, numVertices, 0, TWO_PI);
                x = radii[i] * cos(angle);
                y = radii[i] * sin(angle);
                asteroid.vertex(x, y);
            }
            asteroid.endShape(CLOSE);
            popStyle();
        }
    }

    /**
     * The type Bullet.
     */
    class Bullet {
        /**
         * The Pos.
         */
        final PVector pos;
        /**
         * The Vel.
         */
        final PVector vel;
        /**
         * The Size.
         */
        final float size;
        /**
         * The Angle.
         */
        final float angle;
        /**
         * The Col.
         */
        final int col;

        /**
         * Instantiates a new Bullet.
         *
         * @param loc the loc
         * @param ang the ang
         */
        Bullet(PVector loc, float ang) {
            pos = loc;
            vel = PVector.fromAngle(ang - (PI / 2));
            vel.mult(18);
            size = ship.space/8;
            col = color(0, 255, 0);
            angle = ang;
        }

//        /**
//         * Sets pos.
//         *
//         * @param pos the pos
//         */
//        public void setPos(PVector pos) {
//            this.pos = pos;
//        }
//
//        /**
//         * Edges.
//         */
//        public void edges() {
//            if (pos.x > (width + size)) {
//                pos.x = -size;
//            }
//            if (pos.x < -size) {
//                pos.x = width+size;
//            }
//            if (pos.y < -size) {
//                pos.y = height+size;
//            }
//            if (pos.y > (height + size)) {
//                pos.y = -size;
//            }
//        }

        /**
         * Out of bounds boolean.
         *
         * @return the boolean
         */
        boolean outOfBounds() {
            return ((pos.x < 0) || (pos.x > width) || (pos.y < 0) || (pos.y > height));
        }

        /**
         * Collide with asteriod boolean.
         *
         * @param a the a
         * @return the boolean
         */
        boolean collideWithAsteriod(Asteroid a) {
            return (dist(pos.x, pos.y, a.pos.x, a.pos.y) < a.avgRadius);
        }

        /**
         * Update.
         */
        void update() {
            pos.add(vel);
        }

        /**
         * Show.
         */
        void show() {
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

    /**
     * The type Ship.
     */
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
        /**
         * The Pos.
         */
        PVector pos;
        /**
         * The Vel.
         */
        PVector vel;
        /**
         * The Acc.
         */
        PVector acc;
        /**
         * The Bullets.
         */
        ArrayList<Bullet> bullets;
        /**
         * The Space.
         */
        float space;
        /**
         * The Angle.
         */
        float angle;
        /**
         * The Turn angle.
         */
        float turnAngle;
        /**
         * The Radius.
         */
        float radius;
        /**
         * The Ship.
         */
        PShape ship;
        /**
         * The Head.
         */
        PShape head;
        /**
         * The Thrusters.
         */
        PShape[] thrusters;
        /**
         * The Slow down.
         */
        boolean slowDown;
        /**
         * The Thrust.
         */
        boolean thrust;

        /**
         * Instantiates a new Ship.
         *
         */
        Ship() {
            reset();
        }

        /**
         * Reset.
         *
         */
        void reset() {
            pos = new PVector(width / 2f, height / 2f);
            vel = new PVector(0, 0);
            acc = new PVector(0, 0);
            space = 25;
            radius = space*.85f;

            bullets = new ArrayList<>();
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

        /**
         * Apply force.
         *
         * @param force the force
         */
        void applyForce(PVector force) {
            acc.add(force);
            acc.mult(.8f);
        }

        /**
         * Update.
         */
        void update() {
            vel.add(acc);
            vel.limit(16);
            pos.add(vel);
            acc.mult(0);

            shipAsteriodCollision();
            bulletAsteriodCollision();
            bulletCheck();
        }

        /**
         * Ship asteriod collision.
         */
        void shipAsteriodCollision() {
            for (int a = 0; a < asteroids.size(); a++) {
                Asteroid curr = asteroids.get(a);
                if (hit(curr)) {
                    exit();
//                    asteroids.remove(curr);
//                    reset(null);
//                    break;
                }
            }
        }

        /**
         * Bullet asteriod collision.
         */
        void bulletAsteriodCollision() {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet curr = bullets.get(i);
                for (int j = 0; j < asteroids.size(); j++) {
                    Asteroid a = asteroids.get(j);
                    if (curr.collideWithAsteriod(a)) {
                        duplicateAsteroids(a);
                        asteroids.remove(a);
                        j--;
                        bullets.remove(curr);
                        i--;
                        break;
                    }
                }
            }
        }

        /**
         * Duplicate asteroids.
         *
         * @param parent the parent
         */
        void duplicateAsteroids(Asteroid parent) {
            if (parent.maxRadius > parent.threshold) {
                float per = random(.4f, .6f);
                float r1 = parent.maxRadius*per;
                float r2 = parent.maxRadius * (1 - per);
                if (r1 > parent.threshold) {
                    asteroids.add(new Asteroid(parent.pos.copy(), r1));
                }
                if (r2 > parent.threshold) {
                    asteroids.add(new Asteroid(parent.pos.copy(), r2));
                }
            }
        }

        /**
         * Bullet check.
         */
        void bulletCheck() {
            for (int i = 0; i < bullets.size(); i++) {
                Bullet curr = bullets.get(i);
                if (curr.outOfBounds()) {
                    bullets.remove(curr);
                    i--;
                }
            }
        }

        /**
         * Show.
         */
        void show() {
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

        /**
         * Hit boolean.
         *
         * @param a the a
         * @return the boolean
         */
        boolean hit(Asteroid a) {
            return (dist(pos.x, pos.y, a.pos.x, a.pos.y) <= (a.avgRadius + radius));
        }


        /**
         * Add bullet.
         */
        void addBullet() {
            if (!isShooting) {
                PVector position = new PVector(pos.x, pos.y);
                bullets.add(new Bullet(position, angle));
            }
        }

        /**
         * Thrust.
         */
        void thrust() {
            if (thrust) {
                applyForce(PVector.fromAngle(angle - (PI / 2)));
                thrusters[0].setFill(color(0, 255, 0, 180));
                thrusters[1].setFill(color(0, 255, 0, 180));
            } else {
                thrusters[0].setFill(0);
                thrusters[1].setFill(0);
            }
        }

        /**
         * Edges.
         */
        void edges() {
            if (pos.x > (width + space)) {
                pos.x = -space;
            }
            if (pos.x < -space) {
                pos.x = width+space;
            }
            if (pos.y < -space) {
                pos.y = height+space;
            }
            if (pos.y > (height + space)) {
                pos.y = -space;
            }
        }


        /**
         * Sets turn angle.
         *
         * @param a the a
         */
        void setTurnAngle(float a) {
            turnAngle = a;
        }

        /**
         * Turn.
         */
        void turn() {
            angle+=turnAngle;
        }

        /**
         * Slow down.
         */
        void slowDown() {
            if (slowDown) {
                vel.mult(.98f);
            }
        }
    }
    @Override
    public void settings() { fullScreen(); }
}
