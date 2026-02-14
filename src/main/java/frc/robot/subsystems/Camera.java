package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {
    private PhotonCamera camera;
    public boolean hasTargets;
    public double[] targetCoords = { 0, 0, 0 }; // 0 = X, 1 = Y, 2 = Z
    private int targetID;
    private boolean aprilTagCam;
    private boolean driverModeSet = false;
    public static final AprilTagFieldLayout kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
    public static final Transform3d kRobotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5),
            new Rotation3d(0, 0, 0)); // TODO find real values
    PhotonPoseEstimator photonEstimator = new PhotonPoseEstimator(kTagLayout, kRobotToCam);
    public Pose3d robotPose;

    public Camera(String cameraName, Boolean aprilTagCam) { // Camera name in the PhotonClient
        camera = new PhotonCamera(cameraName);
        this.aprilTagCam = aprilTagCam;
    }

    @Override
    public void periodic() {
        gatherData();
    }

    private void gatherData() {
        if (aprilTagCam) {
            var result = camera.getLatestResult();
            hasTargets = result.hasTargets();
            var target = result.getBestTarget();
            if (hasTargets && target != null) {
                var translation = target.getBestCameraToTarget().getTranslation();
                targetCoords[0] = Math.round(translation.getX() * 100d) / 100d;
                targetCoords[1] = Math.round(translation.getY() * 100d) / 100d;
                targetCoords[2] = Math.round(translation.getZ() * 100d) / 100d;
                targetID = target.getFiducialId();

                int fiducialId = target.getFiducialId();
                if (kTagLayout.getTagPose(fiducialId).isPresent()) {
                    robotPose = PhotonUtils.estimateFieldToRobotAprilTag(target.getBestCameraToTarget(),
                            kTagLayout.getTagPose(fiducialId).get(),
                            photonEstimator.getRobotToCameraTransform());
                    SmartDashboard.putNumber("Robot X", robotPose.getX());
                    SmartDashboard.putNumber("Robot Y", robotPose.getY());
                }
            } else {
                resetTargetCoords();
            }

            SmartDashboard.putBoolean(camera.getName() + "Has Target", hasTargets);
            SmartDashboard.putNumberArray(camera.getName() + "XYZ", targetCoords);
        } else {
            if (!driverModeSet) {
                camera.setDriverMode(true);
                driverModeSet = true;
            }
        }
    }

    private void resetTargetCoords() {
        targetCoords[0] = 0;
        targetCoords[1] = 0;
        targetCoords[2] = 0;
    }

}
