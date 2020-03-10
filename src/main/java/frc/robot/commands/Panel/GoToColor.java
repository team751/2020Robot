package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.core751.subsystems.LightStrip.PostProccessingEffects;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.PositionState;
import frc.robot.subsystems.Panel.WheelColor;

public class GoToColor extends CommandBase {

    public LightStrip[] lightStrips;
    public Panel panel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;
    public WheelColor targetColor;
    public boolean finished;


    public GoToColor(LightStrip[] lightStrips, Panel panel) {
        this.lightStrips = lightStrips;
        this.panel = panel;
        for (LightStrip l : lightStrips) {
            addRequirements(l);
        }
        addRequirements(panel);
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
        for (LightStrip l : lightStrips) {
            l.clearEffects();
            l.setEffect(PostProccessingEffects.WAVE);
            l.setEffect(PostProccessingEffects.HUE_SHIFT);
            l.update();
        }
        this.panel.clearRotations();
        this.targetColor = WheelColor.getTargetColor();
        System.out.println("INITIALIZED");
    }

    @Override
    
    public void execute(){
        this.wheelColor = this.panel.getColor();
        

        if(this.targetColor == null) {
            this.targetColor = WheelColor.getTargetColor();
        }

        SmartDashboard.putNumber("Rotations", this.panel.getRotations());
        SmartDashboard.putString("Color", this.wheelColor.name());
        for (LightStrip l : lightStrips) {
            l.fillHSV(wheelColor.HSV[0], wheelColor.HSV[0], wheelColor.HSV[0]);
            l.update();
        }
        this.lastColor = this.wheelColor;
        if (this.wheelColor == WheelColor.BETWEEN) {
            this.panel.setSpinMotor(0.17);
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
            this.panel.setSpinMotor(0.17);
        }else {
            this.panel.setSpinMotor(dist*0.17);
        }
    
    }

    @Override
    public boolean isFinished(){
        return this.finished;
    }


}