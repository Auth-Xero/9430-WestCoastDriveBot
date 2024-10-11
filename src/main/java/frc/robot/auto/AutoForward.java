package frc.robot.auto;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoForward extends Command {

    private DriveSubsystem driveSubsystem;
    private Timer timer;

    public AutoForward(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        timer = new Timer();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    public void moveForward(int miliseconds, double speed) {
        
    }

    @Override
    public void end(boolean interrupted) {

        driveSubsystem.tankDrive(0, 0);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

}