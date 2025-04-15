package gui;

import java.awt.*;
import java.util.Observable;

public class RobotModel extends Observable {
    private volatile double robotX = 100;
    private volatile double robotY = 100;
    private volatile double direction = 0;
    private volatile int targetX = 150;
    private volatile int targetY = 100;


    private static final double CRUISE_VELOCITY = 2.0;
    private static final double PRECISION_VELOCITY = 0.6;
    private static final double MAX_ANGULAR_VELOCITY = 0.05;
    private static final double STOP_THRESHOLD = 1.0;
    private static final double INSTANT_TURN_ZONE = 8.0;
    private static final double PRECISION_ZONE = 20.0;
    private static final double MIN_TARGET_DISTANCE = 5.0;

    public void update(Dimension bounds) {
        double dx = targetX - robotX;
        double dy = targetY - robotY;
        double distance = Math.hypot(dx, dy);


        if (distance <= STOP_THRESHOLD) {
            robotX = targetX;
            robotY = targetY;
            setChanged();
            notifyObservers();
            return;
        }

        double velocity = distance > PRECISION_ZONE ?
                CRUISE_VELOCITY : PRECISION_VELOCITY;

        double targetAngle = Math.atan2(dy, dx);
        if (distance < INSTANT_TURN_ZONE) {
            direction = targetAngle;
        } else {
            double angleDiff = normalizeAngle(targetAngle - direction);
            double angularVelocity = Math.signum(angleDiff) *
                    Math.min(Math.abs(angleDiff)/2, MAX_ANGULAR_VELOCITY);
            direction = normalizeAngle(direction + angularVelocity);
        }


        robotX += velocity * Math.cos(direction);
        robotY += velocity * Math.sin(direction);

        //границы
        robotX = Math.max(15, Math.min(robotX, bounds.width - 15));
        robotY = Math.max(15, Math.min(robotY, bounds.height - 15));

        setChanged();
        notifyObservers();
    }

    public void setTargetPosition(Point p, Dimension bounds) {
        double newDist = Math.hypot(p.x - robotX, p.y - robotY);
        if (newDist < MIN_TARGET_DISTANCE) return;
        //границы
        targetX = Math.max(1, Math.min(p.x, bounds.width - 1));
        targetY = Math.max(1, Math.min(p.y, bounds.height - 1));
        setChanged();
        notifyObservers();
    }

    private double normalizeAngle(double angle) {
        while (angle > Math.PI) angle -= 2*Math.PI;
        while (angle < -Math.PI) angle += 2*Math.PI;
        return angle;
    }

    public double getRobotX() { return robotX; }
    public double getRobotY() { return robotY; }
    public double getDirection() { return direction; }
    public int getTargetX() { return targetX; }
    public int getTargetY() { return targetY; }
}