package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.core751.subsystems.LightStrip.PostProccessingEffects;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.WheelColor;

public class SensorLights extends CommandBase {

    public LightStrip[] lightStrips;
    public Panel panel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;

    public SensorLights(LightStrip[] lightStrips, Panel panel) {
        this.lightStrips = lightStrips;
        this.panel = panel;
        addRequirements(panel);
        for (LightStrip l : lightStrips) {
            addRequirements(l);
        }
        this.lastColor = WheelColor.UNKNOWN;
        this.currentHSV = new int[]{300, 255, 255};
        this.wheelColor = WheelColor.UNKNOWN;
    }

    @Override
    public void initialize() {
        int[] c = panel.getColor().HSV;
        for (LightStrip l : lightStrips) {
            l.clearEffects();
            l.setEffect(PostProccessingEffects.WAVE);
            l.setEffect(PostProccessingEffects.HUE_SHIFT);
            l.update();
        }
        this.panel.clearRotations();
    }

    @Override
    
    public void execute(){
        this.panel.setSpinMotor(0);
        this.panel.stopSpinMotor();
        this.wheelColor = this.panel.getColor();
        
        SmartDashboard.putNumber("Rotations", this.panel.getRotations());
        SmartDashboard.putString("Color", this.wheelColor.name());
        for (LightStrip l : lightStrips) {
            l.fillHSV(wheelColor.HSV[0], wheelColor.HSV[0], wheelColor.HSV[0]);
            l.update();
        }
        this.lastColor = this.wheelColor;
        
    }


}