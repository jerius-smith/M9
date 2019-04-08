package edu.gatech.cs2340.spacetraders.model;

import processing.core.*;

//import gifAnimation.*;

import java.util.ArrayList;

/**
 * The type Asteroids.
 */
@SuppressWarnings("ALL")
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
        int high = 0;
        for (int n = high; n < num; n++) {
            PVector loc;
            if ("in".equals(inOut)) {
                int i = 100;
                int low = -i;
                loc = new PVector(random(low, width + i), random(low, height + i));
            } else {
                int i = 120;
                loc = (new PVector(random(-i
                        , high), random(height, height + i)));
            }
            int i = 50;
            while (loc.dist(ship.pos) < i) {
                loc = new PVector(random(width), random(height));
            }
            asteroids.add(new Asteroid(loc, high));
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
        float a = .08f;
        if (keyCode == RIGHT) {
            ship.setTurnAngle(a);
        }
        if (keyCode == LEFT) {
            ship.setTurnAngle(-a);
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

            int i1 = 50;
            if (maxRadius < i1) {
                vel = PVector.sub(ship.pos, pos);
                vel.normalize();
            } else {
                vel = PVector.random2D();
            }
            float low = 1.3f;
            float high = 3.3f;
            vel.mult(random(low, high));

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
            int rgb = 255;
            asteroid.stroke(rgb);
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
            int n = 18;
            vel.mult(n);
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
    @SuppressWarnings("MagicNumber")
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
            float v = 2f;
            pos = new PVector(width / v, height / v);
            vel = new PVector(0, 0);
            acc = new PVector(0, 0);
            space = 25;
            float v1 = .85f;
            radius = space * v1;

            bullets = new ArrayList<>();
            ship = createShape(GROUP);
            float v2 = .75f;
            float v3 = 1.2f;
            head = createShape(TRIANGLE, -space, space * v2, space, space * v2, 0, -space * v3);
            thrusters = new PShape[2];
            int i = 20;
            thrusters[0] = createShape(RECT, -space * v2, space * v2, 10, i);
            float v4 = .45f;
            thrusters[1] = createShape(RECT, space * v4, space * v2, 10, i);
            head.setFill(0);
            int x = 200;
            int z = 255;
            head.setStroke(color(x, 0, z));
            head.setStrokeWeight(2);
            for (PShape thruster : thrusters) {
                thruster.setFill(0);
                thruster.setStroke(0);
                float weight = 1.5f;
                thruster.setStrokeWeight(weight);
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
            float n = .8f;
            acc.mult(n);
        }

        /**
         * Update.
         */
        void update() {
            vel.add(acc);
            int max = 16;
            vel.limit(max);
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
                float low = .4f;
                float high = .6f;
                float per = random(low, high);
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
                int y = 255;
                int a = 180;
                thrusters[0].setFill(color(0, y, 0, a));
                thrusters[1].setFill(color(0, y, 0, a));
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
                float n = .98f;
                vel.mult(n);
            }
        }
    }
    @Override
    public void settings() { fullScreen(); }
}
