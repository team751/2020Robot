package frc.robot.core751.commands.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.DifferentialDriveTrain;

public class ArcadeDrive extends CommandBase {

    private Joystick driveStick;
    private DifferentialDriveTrain differentialDriveTrain;

    public void ArcadeDrive(int port, DifferentialDriveTrain differentialDriveTrain) {
        this.driveStick = new Joystick(port);
        this.differentialDriveTrain = differentialDriveTrain;
    }

    @Override
    public void execute() {
        this.differentialDriveTrain.getDifferentialDrive().arcadeDrive(driveStick.getX(), driveStick.getY());
    }

}