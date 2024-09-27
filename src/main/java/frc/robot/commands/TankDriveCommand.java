// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class TankDriveCommand extends Command {
  private XboxController controller;
  private DriveSubsystem driveSubsystem;
  private double multiplier = Constants.DriveConstants.DTSpeedmultiplier;

  /** Creates a new TankDriveCommand. */
  public TankDriveCommand(XboxController controller, DriveSubsystem driveSubsystem) {
    this.controller = controller;
    this.driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(controller.getPOV()==0 && multiplier<1.0){
      multiplier += 0.1;
    }

    if(controller.getPOV()==180 && multiplier>0.0){
      multiplier -= 0.1;
    }

    if(controller.getPOV()==90 ){
      multiplier = 1.0;
    }

    if(controller.getPOV()==270 ){
      multiplier = 0.5;
    }

    double leftSpeed = controller.getLeftY() * multiplier;
    double rightSpeed = controller.getRightY() * multiplier;
    driveSubsystem.tankDrive(leftSpeed, rightSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    driveSubsystem.tankDrive(0, 0);
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
