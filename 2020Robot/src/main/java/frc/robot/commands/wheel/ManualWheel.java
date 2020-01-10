package frc.robot.commands.wheel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Wheel;

public class ManualWheel extends CommandBase {

    private Wheel wheel;

    public ManualWheel(Wheel wheel) {
        this.wheel = wheel;
        addRequirements(wheel);
    }

    @Override
    public void execute() {
        SmartDashboard.putString("Color", wheel.getColor().name());
    }
}