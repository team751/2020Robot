package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.WheelColor;

public class RotateWheel extends CommandBase {

    public LightStrip lightStrip;
    public Panel panel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;
    public boolean finished;
    public float target;


    public RotateWheel(LightStrip lightStrip, Panel panel) {
        this.lightStrip = lightStrip;
        this.panel = panel;
        addRequirements(panel, lightStrip);
        this.lastColor = WheelColor.UNKNOWN;
        this.currentHSV = new int[]{300, 255, 255};
        this.wheelColor = WheelColor.UNKNOWN;
    }

    @Override
    public void initialize() {
        this.finished = false;
        int[] c = panel.getColor().HSV;
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 2, c);
        }
        this.lightStrip.update();
        this.lightStrip.start();
        this.panel.clearRotations();

        WheelColor targetColor = WheelColor.getTargetColor();
    //     if (targetColor == WheelColor.UNKNOWN) {
    //         this.target = 3.5f;
    //     } else {
    //         int dist = this.wheelColor.colorDist(targetColor);
    //         if (dist < 0) {
    //             this.target = -3.125f + (dist*0.125f);
    //         } else {
    //             this.target = -3.125f + (dist*0.125f);
    //         }
    //     }
        this.target = 3.5f;
     }

    @Override
    
    public void execute(){
        this.wheelColor = this.panel.getColor();
        SmartDashboard.putNumber("Target Rot", this.target);
        SmartDashboard.putNumber("Rotations", this.panel.getRotations());
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
        if (this.wheelColor == WheelColor.BETWEEN) {
            this.panel.setSpinMotor(1);
        }
        double speed = 0.5;
        if (Math.abs(Math.abs(this.target) - Math.abs(this.panel.getRotations()))<=0.125f) {
            speed = 1;
        }
        SmartDashboard.putNumber("speed", speed);
        //double speed = 1;
        if (this.panel.getRotations() < this.target) {
            this.panel.setSpinMotor(-speed);
        }else if (this.panel.getRotations() > this.target) {
            this.panel.setSpinMotor(speed);
        }else {
            this.panel.stopSpinMotor();
            this.finished = true;
        }
    
    }

    @Override
    public boolean isFinished(){
        return this.finished;
    }
}
