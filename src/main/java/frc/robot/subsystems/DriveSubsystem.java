// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import com.revrobotics.*;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {

  private CANSparkMax motorFrontLeft = new CANSparkMax(Constants.OperatorConstants.motorFrontLeft,
      MotorType.kBrushless);
  private CANSparkMax motorFrontRight = new CANSparkMax(Constants.OperatorConstants.motorFrontRight,
      MotorType.kBrushless);
  private CANSparkMax motorBackLeft = new CANSparkMax(Constants.OperatorConstants.motorBackLeft, MotorType.kBrushless);
  private CANSparkMax motorBackRight = new CANSparkMax(Constants.OperatorConstants.motorBackRight,
      MotorType.kBrushless);

  private MotorControllerGroup left = new MotorControllerGroup(motorFrontLeft);
  private MotorControllerGroup right = new MotorControllerGroup(motorFrontRight);

  private DifferentialDrive drive = new DifferentialDrive(left, right);

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {

    motorFrontLeft.restoreFactoryDefaults();
    motorFrontRight.restoreFactoryDefaults();
    motorBackLeft.restoreFactoryDefaults();
    motorBackRight.restoreFactoryDefaults();

    motorFrontLeft.setInverted(false);
    motorFrontRight.setInverted(true);

    motorBackLeft.follow(motorFrontLeft);
    motorBackRight.follow(motorFrontRight);


    // Load the RobotConfig from the GUI settings. You should probably
    // store this in your Constants file
    RobotConfig config = null;
    try{
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
            (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential drive trains
            config, // The robot configuration
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );

  }

  public void tankDrive(double leftSpeed, double rightSpeed) {

    drive.tankDrive(leftSpeed, rightSpeed);

  }

  public void arcade(double speed, double rotation) {

    drive.arcadeDrive(speed, rotation);

  }

  public Pose2d getPose() {
    return null;
  }

  public void resetPose(Pose2d pose) {

  }

  public ChassisSpeeds getRobotRelativeSpeeds() {
    return null;
  }

  public void driveRobotRelative(ChassisSpeeds speeds) {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
