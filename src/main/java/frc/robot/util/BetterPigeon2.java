package frc.robot.util;

import java.util.ArrayList;

import com.ctre.phoenix6.hardware.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class BetterPigeon2 {

    private Pigeon2 pigeon;
    private double translationX;
    private double translationY;
    private double rotationZ;
    private Pose2d prevPose2d;
    private Pose2d curPose2d;
    private double startTime;
    private double prevTimeStamp;
    private double curTimeStamp;

    public BetterPigeon2() {
        pigeon = new Pigeon2(Constants.pigeonCANId); // Initialize with the CAN ID
        startTime = Timer.getFPGATimestamp();
    }

    public Pigeon2 getPigeon() {
        return pigeon;
    }

    /**
     * Returns the current position of the robot as a Pose2d
     * 
     * @return Pose2d of the current position
     */
    public void updatePose() {
        prevPose2d = curPose2d;
        prevTimeStamp = curTimeStamp;
        Pose2d pose = new Pose2d(new Translation2d(translationX, translationY), new Rotation2d(rotationZ));
        curPose2d = pose;
        curTimeStamp = Timer.getFPGATimestamp();
    }

    public Pose2d getPose() {
        updatePose();
        return curPose2d;
    }

    public void resetPose() {
        translationX = 0;
        translationY = 0;
        rotationZ = 0;
    }

    public double getSpeedReletive() {
        updatePose();
        double relativeChangeX = Math.pow(curPose2d.getX() - prevPose2d.getX(), 2);
        double relativeChangeY = Math.pow(curPose2d.getY() - prevPose2d.getY(), 2);
        double relativeDistance = Math.sqrt(relativeChangeX + relativeChangeY);
        double relativeTime = curTimeStamp - prevTimeStamp;
        return relativeDistance / relativeTime;
    }

    public void driveRobotRelative() {
    }

}
