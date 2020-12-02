package project;

import java.util.Locale;

import robocode.Bullet;

class Observation {
    private double bearing;
    private double heading;
    private double distance;
    private double velocity;
    private Bullet bullet;

    Observation(double bearing, double heading, double distance, double velocity, Bullet bullet) {
        this.bearing = bearing;
        this.heading = heading;
        this.distance = distance;
        this.velocity = velocity;
        this.bullet = bullet;
    }

    @Override
    public String toString() {
        // if bullet is still active don't use this observation
        if (bullet.isActive())
            return "";
        return String.format(Locale.US, "%.2f;%.2f;%.2f;%.2f;%d\n", bearing, heading, distance, velocity, bullet.getVictim() == null ? 0 : 1);
    }
}
