package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.ScannedRobotEvent;

public class Collector extends AdvancedRobot {

    private Observation currentObservation;
    private List<Observation> observations;

    public Collector() {
        currentObservation = null;
        observations = new ArrayList<>();
    }

    public void run() {
        setAdjustGunForRobotTurn(true);

        while (true) {
            setTurnGunLeft(360);
            execute();
        }
    }

    // here we can collect all information, when we scan some enemy tank
    public void onScannedRobot(ScannedRobotEvent e) {
        // fire only if no observation is stored
        if (currentObservation != null)
            return;

        // check if actually fired
        if (setFireBullet(1.0) != null)
            currentObservation = new Observation(e.getBearing(), e.getHeading(), e.getDistance(), e.getVelocity());
    }

    // when bullet hits enemy
    public void onBulletHit(BulletHitEvent event) {
        System.out.println("hit");
        setHit(true);
    }

    // when bullet hits other bullet
    public void onBulletHitBullet(BulletHitBulletEvent event) {
        System.out.println("missed");
        setHit(false);
    }

    // when bullet misses
    public void onBulletMissed(BulletMissedEvent event) {
        System.out.println("missed");
        setHit(false);
    }

    // save observations on end
    public void onBattleEnded(BattleEndedEvent event) {
        save();
    }

    // sets hit for current observation and adds it to all observations
    private void setHit(boolean hit) {
        currentObservation.setHit(true);
        observations.add(currentObservation);
        currentObservation = null;
    }

    //method for adding info to CSv file
    private void save() {
        try (FileWriter writer = new FileWriter("collected_data/observations.csv")) {
            for (Observation observation: observations) 
                writer.write(observation.toString());

            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    class Observation {
        private double bearing;
        private double heading;
        private double distance;
        private double velocity;
        private boolean hit;
        

        Observation(double bearing, double heading, double distance, double velocity) {
            this.bearing = bearing;
            this.heading = heading;
            this.distance = distance;
            this.velocity = velocity;
        }

        void setHit(boolean hit) { 
            this.hit = hit; 
        }

        @Override
        public String toString() {
            return String.format("%.2f;%.2f;%.2f;%.2f;%s\n", bearing, heading, distance, velocity, hit);
        }
    }
}