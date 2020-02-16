package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.PositionState;
import frc.robot.subsystems.Panel.WheelColor;

public class GoToColor extends CommandBase {

    public LightStrip lightStrip;
    public Panel panel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;
    public WheelColor targetColor;
    public boolean finished;


    public GoToColor(LightStrip lightStrip, Panel panel) {
        this.lightStrip = lightStrip;
        this.panel = panel;
        addRequirements(panel, lightStrip);
        this.lastColor = WheelColor.UNKNOWN;
        this.currentHSV = new int[]{300, 255, 255};
        this.wheelColor = WheelColor.UNKNOWN;
    }

    @Override
    public void initialize() {
        if (this.panel.positionState !=PositionState.UP) {
            this.finished = true;
            return;
        }else {
            this.panel.setPositionMotor(this.panel.getPassiveUpSpeed());
        }
        this.finished = false;
        int[] c = panel.getColor().HSV;
        for (int i = 0; i < lightStrip.length; i++) {
            this.lightStrip.setHSVWave(i, 2, c);
        }
        this.lightStrip.update();
        this.lightStrip.start();
        this.panel.clearRotations();
        this.targetColor = WheelColor.getTargetColor();
    }

    @Override
    
    public void execute(){
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
        if (this.wheelColor == WheelColor.BETWEEN) {
            this.panel.setSpinMotor(0.5);
        }
        if (this.wheelColor == WheelColor.UNKNOWN) {
            this.panel.stopSpinMotor();
            this.finished = true;
        }

        int dist = this.wheelColor.colorDist(this.targetColor);
        if (dist == 0) {
            this.panel.stopSpinMotor();
            this.finished = true;
        }
        if (dist == 2) {
            this.panel.setSpinMotor(1);
        }else {
            this.panel.setSpinMotor(dist*0.5);
        }
    
    }

    @Override
    public boolean isFinished(){
        return this.finished;
    }


}