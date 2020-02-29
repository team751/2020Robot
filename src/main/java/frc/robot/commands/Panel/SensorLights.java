package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import core751.subsystems.LightStrip;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.WheelColor;

public class SensorLights extends CommandBase {

    public LightStrip lightStrip;
    public Panel panel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;

    public SensorLights(LightStrip lightStrip, Panel panel) {
        this.lightStrip = lightStrip;
        this.panel = panel;
        addRequirements(panel, lightStrip);
        this.lastColor = WheelColor.UNKNOWN;
        this.currentHSV = new int[]{300, 255, 255};
        this.wheelColor = WheelColor.UNKNOWN;
    }

    @Override
    public void initialize() {
        int[] c = panel.getColor().HSV;
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 2, c);
        }
        this.lightStrip.update();
        this.lightStrip.start();
        this.panel.clearRotations();
    }

    @Override
    
    public void execute(){
        this.panel.setSpinMotor(0);
        this.panel.stopSpinMotor();
        this.wheelColor = this.panel.getColor();
        
        SmartDashboard.putNumber("Rotations", this.panel.getRotations());
        SmartDashboard.putString("Color", this.wheelColor.name());
        if (Math.abs(this.currentHSV[0]-this.wheelColor.HSV[0]) < 8) {
            this.currentHSV[0] = this.wheelColor.HSV[0];
        }
        
        if (this.currentHSV[0] < this.wheelColor.HSV[0]) {
            this.currentHSV[0]+=8;
        }else if (this.currentHSV[0] > this.wheelColor.HSV[0]){
            this.currentHSV[0]-=8;
        }
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 1, this.currentHSV);
        }
        this.lastColor = this.wheelColor;
        this.lightStrip.update();
        
    }


}