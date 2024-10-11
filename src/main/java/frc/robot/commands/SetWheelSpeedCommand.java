// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class SetWheelSpeedCommand extends Command {

  private DriveSubsystem driveSubsystem;
  private double leftSpeed;
  private double rightSpeed;

  /** Creates a new SetWheelSpeedCommand. */
  public SetWheelSpeedCommand(DriveSubsystem driveSubsystem, double leftSpeed, double rightSpeed) {
    this.driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.driveSubsystem);
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    driveSubsystem.setWheelSpeed(leftSpeed, rightSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
