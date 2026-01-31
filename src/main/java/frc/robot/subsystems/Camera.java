package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase{
    PhotonCamera m_arducam;
    public boolean hasTargets;
    public double yaw;
    public double pitch;
    public double area;
    public double skew;
    public int targetID;

    public Camera() {
        m_arducam = new PhotonCamera("Arducam_OV9281_USB_Camera");
    }

    @Override
    public void periodic() {
        gatherData();
    }
    
    private void gatherData() {
        var result = m_arducam.getLatestResult();
        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();
            pitch = target.getPitch();
            yaw = target.getYaw();
            area = target.getArea();
            skew = target.getSkew();
            targetID = target.getFiducialId();
        } else {
            hasTargets = false;
        }
    }
}
