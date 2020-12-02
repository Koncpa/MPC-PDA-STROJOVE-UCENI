package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.Bullet;
import robocode.ScannedRobotEvent;

public class Collector extends AdvancedRobot { 
    
    
    
    private List<Observation> observations;
    public Collector() {
        observations = new ArrayList<>();
    }

    public void run() {
        // rotate radar, so it has offset to gun
        setTurnRadarLeft(30);

        setAdjustGunForRobotTurn(true);

        while (true) {
            setTurnGunLeft(360);
            execute();
        }
    }

    // here we can collect all information, when we scan some enemy tank
    public void onScannedRobot(ScannedRobotEvent e) {
        Bullet bullet = setFireBullet(1.0);
        // check if actually fired
        if (bullet != null)
            observations.add(new Observation(e.getBearing(), e.getHeading(), e.getDistance(), e.getVelocity(), bullet));
    }

    // save observations on end
    public void onBattleEnded(BattleEndedEvent event) {
        save();
    }

    // method for adding info to CSV file
    private void save() {
        // open observations file in append mode
        try (FileWriter writer = new FileWriter("observations.csv", true)) {
            for (Observation observation: observations) 
                writer.write(observation.toString());

            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}