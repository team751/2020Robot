package frc.robot.core751.commands.camera;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.Camera;

public class SwitchCameraCommand extends CommandBase {
    private int m_device;
    private Camera m_cameraSubsystem;
    private boolean m_finished = false;

    public SwitchCameraCommand(Camera cameraSubsystem, int initialDevice) {
        m_device = initialDevice;
        m_cameraSubsystem = cameraSubsystem;
        addRequirements(cameraSubsystem);

        SmartDashboard.putData(this);
    }

    public void initialize() {
        m_cameraSubsystem.switchCamera(m_device);

        SmartDashboard.putNumber("Camera Device Number", m_device);

        m_finished = true;
    }

    public boolean isFinished() {
        return m_finished;
    }

    public int getDeviceNumber() {
        return m_device;
    }
}
