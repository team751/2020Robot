package core751.commands.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import core751.subsystems.DifferentialDriveTrain;

public class ReversableArcadeDrive extends CommandBase {

    private Joystick driveStick;
    private DifferentialDriveTrain differentialDriveTrain;

    public ReversableArcadeDrive(Joystick driveStick, DifferentialDriveTrain differentialDriveTrain) {
        this.driveStick = driveStick;
        this.differentialDriveTrain = differentialDriveTrain;
        addRequirements(differentialDriveTrain);
    }

    @Override
    public void execute() {
        int mod = differentialDriveTrain.getDirection().getMod();
        this.differentialDriveTrain.getDifferentialDrive().arcadeDrive(driveStick.getX()*mod, driveStick.getY()*mod);
    }

}