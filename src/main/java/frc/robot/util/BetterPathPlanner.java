package frc.robot.util;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.DriveSubsystem;

public class BetterPathPlanner {

    private RobotConfig config;
    private DriveSubsystem driveSubsystem;
    private BetterPigeon2 pigeon; 

    public BetterPathPlanner(DriveSubsystem subsystem) {

        driveSubsystem = subsystem;
        pigeon = new BetterPigeon2();

        // Load the RobotConfig from the GUI settings. You should probably
        // store this in your Constants file
        config = null;
        try {
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        }

        // Configure AutoBuilder last
        AutoBuilder.configure(
                this::getPose, // Robot pose supplier
                this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
                this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT
                                                                      // RELATIVE ChassisSpeeds. Also optionally outputs
                                                                      // individual module feedforwards
                new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential
                                           // drive trains
                config, // The robot configuration
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red
                    // alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                driveSubsystem // Reference to this subsystem to set requirements
        );
    }

    /**
     * Returns the current Pose2d position from the gyroscope
     * 
     * @return Pose2d
     */
    public Pose2d getPose() {
        return pigeon.getPose();
    }

    /**
     * Resets the given Pose2d
     * 
     * @param pose
     */
    public void resetPose(Pose2d pose) {
        pigeon.resetPose(pose);
    }

    /**
     * Returns the current robot-relative ChassisSpeeds
     * 
     * @return
     */
    public ChassisSpeeds getRobotRelativeSpeeds() {
        return pigeon.getSpeedReletive();
    }

    /**
     * Outputs commands to the robot's drive motors given robot-relative
     * ChassisSpeeds
     * 
     * @param speeds
     */
    public void driveRobotRelative(ChassisSpeeds speeds) {

    }

}
