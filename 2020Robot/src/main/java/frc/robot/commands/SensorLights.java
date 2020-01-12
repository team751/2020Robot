package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.subsystems.Wheel;
import frc.robot.subsystems.Wheel.WheelColor;

public class SensorLights extends CommandBase {

    public LightStrip lightStrip;
    public Wheel wheel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;

    public SensorLights(LightStrip lightStrip, Wheel wheel) {
        this.lightStrip = lightStrip;
        this.wheel = wheel;
        addRequirements(wheel, lightStrip);
        this.lastColor = WheelColor.UNKNOWN;
        this.currentHSV = this.lastColor.HSV;
        this.wheelColor = WheelColor.UNKNOWN;
    }

    @Override
    public void initialize() {
        int[] c = wheel.getColor().HSV;
        double[] cd = new double[]{(double)c[0], (double)c[1], (double)c[2]};
        SmartDashboard.putString("color", wheel.getColor().name());
        SmartDashboard.putNumberArray("hsv", cd);
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 2, c);
        }
        this.lightStrip.update();
        this.lightStrip.start();
    }

    @Override
    
    public void execute(){
        WheelColor w = this.wheel.getColor();
        if (w != wheelColor) {
            this.lastColor = wheelColor;
            this.wheelColor = w;
        }
        if (Math.abs(this.currentHSV[0]-this.wheelColor.HSV[0]) < 6) {
            this.currentHSV[0] = this.wheelColor.HSV[0];
        }
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 1, this.currentHSV);
        }
        if (this.currentHSV[0] < this.wheelColor.HSV[0]) {
            this.currentHSV[0]+=6;
        }else if (this.currentHSV[0] > this.wheelColor.HSV[0]){
            this.currentHSV[0]-=6;
        }
        
        this.lightStrip.update();
    }


}