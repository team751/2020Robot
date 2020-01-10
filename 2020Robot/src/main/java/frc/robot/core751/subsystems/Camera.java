package frc.robot.core751.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {
    public Camera(int device) {
        CameraServer.getInstance().startAutomaticCapture(device);
    }

    public void switchCamera(int toDeviceNum) {
        CameraServer.getInstance().startAutomaticCapture(toDeviceNum);
    }
}