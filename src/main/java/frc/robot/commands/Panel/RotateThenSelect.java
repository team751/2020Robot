package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.subsystems.Panel;

public class RotateThenSelect extends SequentialCommandGroup {

    public RotateThenSelect (Panel panel, LightStrip lightStrip) {
        addCommands(new RotateWheel(lightStrip, panel));
        addCommands(new GoToColor(lightStrip, panel));
    }
    
}