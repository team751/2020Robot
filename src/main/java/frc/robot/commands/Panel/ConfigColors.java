package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Panel;

public class ConfigColors extends CommandBase {

    private Panel panel;

    public ConfigColors (Panel panel) {
        this.panel = panel;
        addRequirements(panel);
    }

    @Override
    public void execute() {
        double[] c = this.panel.getAverageColors();
        SmartDashboard.putNumberArray("Color", c);
    }
    
}