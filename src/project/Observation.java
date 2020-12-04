package project;

import java.util.Locale;

import robocode.Bullet;

class Observation {
    private double bearing;
    private double heading;
    private double distance;
    private double velocity;
    private double robotHeading;
    private Bullet bullet;

    Observation(double bearing, double heading, double distance, double velocity, double robotHeading, Bullet bullet) {
        this.bearing = bearing;
        this.heading = heading;
        this.distance = distance;
        this.velocity = velocity;
        this.robotHeading = robotHeading;
        this.bullet = bullet;
    }

    @Override
    public String toString() {
        // if bullet is still active don't use this observation
        if (bullet.isActive())
            return "";
        
        double relativeHeading = toRelative(heading - robotHeading);
        double relativeBulletHeading = toRelative(bullet.getHeading() - robotHeading);
        double gunToTurn = toRelative(bearing - relativeBulletHeading);
        
        return String.format(Locale.US, "%.2f;%.2f;%.2f;%.2f;%d\n", gunToTurn, relativeHeading, distance, velocity, (bullet.getVictim() == null) ? 0 : 1);
    }
    
    private double toRelative(double input) {
    	return (input >= 180 ) ? -(360 - input) : (input < -180) ? (360 + input) : input;
    }
}
