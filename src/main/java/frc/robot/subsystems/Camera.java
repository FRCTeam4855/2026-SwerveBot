package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {
    private PhotonCamera camera;
    public boolean hasTargets;
    public double[]  targetCoords = {0,0,0}; //0 = X, 1 = Y, 2 = Z
    public int targetID;

    public Camera(String cameraName) { //Camera name in the PhotonClient
        camera = new PhotonCamera(cameraName);
    }

    @Override
    public void periodic() {
        gatherData();
    }
    
    private void gatherData() {
        var result = camera.getLatestResult();
        hasTargets = result.hasTargets();
        if (hasTargets) {
            var target = result.getBestTarget();
            var translation = target.getBestCameraToTarget().getTranslation();
            targetCoords[0] = Math.round(translation.getX() * 100d) / 100d;
            targetCoords[1] = Math.round(translation.getY() * 100d) / 100d;
            targetCoords[2] = Math.round(translation.getZ() * 100d) / 100d;
            targetID = target.getFiducialId();
        } else {
            targetCoords[0] = 0;
            targetCoords[1] = 0;
            targetCoords[2] = 0;
        }

        SmartDashboard.putBoolean(camera.getName() + "Has Target", hasTargets);
        SmartDashboard.putNumberArray(camera.getName() + "XYZ", targetCoords);
    }
}
