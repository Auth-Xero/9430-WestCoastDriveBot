package frc.robot.util;

import java.util.ArrayList;

import com.ctre.phoenix6.hardware.*;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;

public class BetterPigeon2 {

    private final Pigeon2 pigeon;
    private Pose2d currentPose;
    private ArrayList<Pose2d> previousPoses;
    private ArrayList<Double> previousTimes;
    private double lastTimestamp;
    private final DifferentialDriveOdometry odometry;

    public BetterPigeon2() {
        pigeon = new Pigeon2(Constants.pigeonCANId);
        // Initialize odometry with the starting pose and heading from the gyro
        odometry = new DifferentialDriveOdometry(getHeading(), 0, 0, currentPose);
        previousPoses = new ArrayList<Pose2d>();
        previousTimes = new ArrayList<Double>();
    }

    /**
     * Returns the Pigeon2 object.
     *
     * @return the Pigeon2 gyro sensor
     */
    public Pigeon2 getPigeon() {
        return pigeon;
    }

    /**
     * Returns the current heading of the robot as a Rotation2d.
     *
     * @return the current heading
     */
    public Rotation2d getHeading() {
        // getRotation2d() returns Rotation2d representing the robot's heading
        return pigeon.getRotation2d();
    }

    /**
     * Resets the robot's odometry to the specified pose.
     *
     * @param pose the pose to reset to
     */
    public void resetOdometry(Pose2d pose) {
        pigeon.reset();
        // odometry.resetPosition(pose, getHeading());
    }

    /**
     * Resets the robot's pose to the specified Pose2d.
     *
     * @param newPose the new pose to set
     */
    public void resetPose(Pose2d newPose) {
        currentPose = newPose;
        lastTimestamp = Timer.getFPGATimestamp();
        pigeon.reset(); // Resets the gyro heading to zero
    }

    /**
     * Returns the current pose of the robot.
     *
     * @return the current Pose2d
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Updates the robot's pose based on the provided chassis speeds.
     * This method should be called periodically, ideally in a scheduler or main
     * robot loop.
     *
     * @param chassisSpeeds the current chassis speeds (vx, vy in meters per second)
     */
    public void updatePose(ChassisSpeeds chassisSpeeds) {
        double currentTime = Timer.getFPGATimestamp();
        double deltaTime = currentTime - lastTimestamp;

        if (deltaTime <= 0) {
            return;
        }

        lastTimestamp = currentTime;

        // Get the robot's current heading from the gyro (convert to radians)
        double headingDegrees = pigeon.getAngle(); // getAngle() returns CCW positive in degrees
        Rotation2d heading = Rotation2d.fromDegrees(headingDegrees);

        // Compute the robot's movement over deltaTime
        double dx = chassisSpeeds.vxMetersPerSecond * deltaTime;
        double dy = chassisSpeeds.vyMetersPerSecond * deltaTime;

        // Convert robot-relative movement to field-relative movement
        Translation2d fieldRelativeTranslation = new Translation2d(dx, dy).rotateBy(heading);

        // Log Pose2d's and timestamps
        previousPoses.add(currentPose);
        if (previousPoses.size() > 10) {
            previousPoses.remove(0);
        }

        previousTimes.add(deltaTime);

        // Update the pose
        currentPose = new Pose2d(
                currentPose.getTranslation().plus(fieldRelativeTranslation),
                heading);
    }

    /**
     * Updates the robot's odometry.
     * This method should be called periodically, ideally in a scheduler or main
     * robot loop.
     *
     * @param leftDistanceMeters  The distance traveled by the left side of the
     *                            drivetrain since the last reset
     * @param rightDistanceMeters The distance traveled by the right side of the
     *                            drivetrain since the last reset
     */
    public void updateOdometry(double leftDistanceMeters, double rightDistanceMeters) {
        odometry.update(getHeading(), leftDistanceMeters, rightDistanceMeters);
    }

    /**
     * Returns the robot's angular velocity around the Z-axis in degrees per second.
     *
     * @return the angular velocity
     */
    public double getAngularVelocity() {
        return pigeon.getRate(); // getRate() returns angular velocity in degrees per second
    }

    /**
     * Return ChassisSpeeds representing the speed of the robot
     * 
     * @return
     */
    public ChassisSpeeds getSpeedReletive() {

        

        double dt = previousTimes.get(previousTimes.size() - 1);

        double dx = (previousPoses.get(previousPoses.size() - 1).getX() - currentPose.getX()) / dt;
        double dy = (previousPoses.get(previousPoses.size() - 1).getY() - currentPose.getY()) / dt;
        double da = (previousPoses.get(previousPoses.size() - 1).getRotation().getRadians()
                - currentPose.getRotation().getRadians()) / dt;

        ChassisSpeeds speeds = new ChassisSpeeds(dx, dy, da);
        return speeds;
    }

    public void driveRobotRelative() {
    }

}
