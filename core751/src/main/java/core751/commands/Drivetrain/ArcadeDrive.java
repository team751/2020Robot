package core751.commands.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import core751.subsystems.DifferentialDriveTrain;

public class ArcadeDrive extends CommandBase {

    private Joystick driveStick;
    private DifferentialDriveTrain differentialDriveTrain;

    public ArcadeDrive(Joystick driveStick, DifferentialDriveTrain differentialDriveTrain) {
        this.driveStick = driveStick;
        this.differentialDriveTrain = differentialDriveTrain;
        addRequirements(differentialDriveTrain);
    }

    @Override
    public void execute() {
        this.differentialDriveTrain.getDifferentialDrive().arcadeDrive(driveStick.getX(), driveStick.getY());
    }

}