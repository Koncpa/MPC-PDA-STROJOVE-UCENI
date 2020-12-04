package project;

import java.util.Locale;

import robocode.Bullet;

class Observation {
    private double distance;
    private double velocity;
    private Bullet bullet;
    private double relativeHeading;
    private double relativeBulletHeading;
    private double gunToTurn;
    
    Observation(double bearing, double heading, double distance, double velocity, double robotHeading, Bullet bullet) {
        this.distance = distance;
        this.velocity = velocity;
        this.bullet = bullet;
        
        this.relativeHeading = toRelative(heading - robotHeading);
        this.relativeBulletHeading = toRelative(bullet.getHeading() - robotHeading);
        this.gunToTurn = toRelative(bearing - relativeBulletHeading);
    }

    @Override
    public String toString() {
        // if bullet is still active don't use this observation
        if (bullet.isActive())
            return "";
        
        return String.format(Locale.US, "%.2f;%.2f;%.2f;%.2f;%d\n", gunToTurn, relativeHeading, distance, velocity, (bullet.getVictim() == null) ? 0 : 1);
    }
    
    private double toRelative(double input) {
    	return (input >= 180 ) ? -(360 - input) : (input < -180) ? (360 + input) : input;
    }
}
