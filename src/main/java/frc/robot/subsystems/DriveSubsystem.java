// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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

  }

public void tankDrive(double leftSpeed, double rightSpeed) {

drive.tankDrive(leftSpeed, rightSpeed);

}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
