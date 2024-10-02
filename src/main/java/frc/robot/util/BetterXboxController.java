package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class BetterXboxController extends XboxController {

    public BetterXboxController(final int port) {
        super(port);
    }

    public boolean getDpadUp() {
        return (getPOV() == 0);
    }

    public boolean getDpadUpRight() {
        return (getPOV() == 45);
    }

    public boolean getDpadRight() {
        return (getPOV() == 90);
    }

    public boolean getDpadDownRight() {
        return (getPOV() == 135);
    }

    public boolean getDpadDown() {
        return (getPOV() == 180);
    }

    public boolean getDpadDownLeft() {
        return (getPOV() == 225);
    }

    public boolean getDpadLeft() {
        return (getPOV() == 270);
    }

    public boolean getDpadUpLeft() {
        return (getPOV() == 315);
    }

}
