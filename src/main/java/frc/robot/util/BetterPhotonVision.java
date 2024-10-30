package frc.robot.util;

import org.photonvision.PhotonCamera;

public class BetterPhotonVision {

    PhotonCamera arducam1;
    PhotonCamera arducam2;

    public BetterPhotonVision() {

        arducam1 = new PhotonCamera("Arducam_1");
        arducam2 = new PhotonCamera("Arducam_2");
    }

}
