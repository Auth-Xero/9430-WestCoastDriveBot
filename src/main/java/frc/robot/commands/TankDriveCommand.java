// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.util.BetterXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDriveCommand extends Command {
  private BetterXboxController controller;
  private DriveSubsystem driveSubsystem;
  private static double multiplier = Constants.DriveConstants.DTSpeedmultiplier;
  private boolean pressed;

  /** Creates a new TankDriveCommand. */
  public TankDriveCommand(BetterXboxController controller, DriveSubsystem driveSubsystem) {
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

    // When D-pad is not pressed, sets boolean pressed to false
    if(controller.getPOV() == -1){ 
      pressed = false;
    }

    // When D-pad up (0 degrees) is pressed, increase the multiplier value by 10% if it is not above 100%
    if(controller.getPOV() == 0 && multiplier < 1.0 && !pressed){ 
      multiplier += 0.1; 
      pressed = true;
    }

    // When D-pad dow (180 degrees) is pressed, decrease the multiplier value by 10% if it is not below 0%
    if(controller.getPOV() == 180 && multiplier > 0.0 && !pressed){ 
      multiplier -= 0.1;
      pressed = true; 
    }

    // When D-pad right (90 degrees) is pressed, sets multiplier to 100%
    if(controller.getPOV() == 90 && !pressed){ 
      multiplier = 1.0; 
      pressed = true;
    }

    // When D-pad left (270 degrees) is pressed, sets multiplier to 50%
    if(controller.getPOV() == 270 && !pressed){ 
      multiplier = 0.5; 
      pressed = true;
    }

    double leftSpeed = controller.getLeftY() * multiplier;
    double rightSpeed = controller.getRightY() * multiplier;
    driveSubsystem.tankDrive(leftSpeed, rightSpeed);

    // Log Variables
    SmartDashboard.putString("Current Multiplier: ",""+multiplier);
    SmartDashboard.putString("POV: ",""+controller.getPOV());
    SmartDashboard.putString("L-Stick: ",""+controller.getLeftY());
    SmartDashboard.putString("R-Stick: ",""+controller.getRightY());

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
