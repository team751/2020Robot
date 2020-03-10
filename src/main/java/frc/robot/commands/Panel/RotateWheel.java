package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.core751.subsystems.LightStrip.PostProccessingEffects;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.PositionState;
import frc.robot.subsystems.Panel.WheelColor;

public class RotateWheel extends CommandBase {

    public LightStrip[] lightStrips;
    public Panel panel;
    public WheelColor wheelColor;
    public int[] currentHSV;
    public WheelColor lastColor;
    public boolean finished;
    public float target;


    public RotateWheel(LightStrip[] lightStrips, Panel panel) {
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
        this.target = 2.4f;
        System.out.println("Initalized rotations");
     }

    @Override
    
    public void execute(){
        // I AM SPEED
        double rotations = this.panel.getRotations();
        double speed = Math.max(Math.log10(-(Math.abs(rotations) - Constants.autoSpeedDecreasePoint - 2)) + 
                                (1 - Math.log10(2)) - 0.6, 0.25);

        this.wheelColor = this.panel.getColor();

        if(rotations >= this.target) {
            this.finished = true;
        }

        SmartDashboard.putNumber("Target Rot", this.target);
        SmartDashboard.putNumber("Rotations", rotations);
        SmartDashboard.putString("Color", this.panel.getColor().name());
        SmartDashboard.putNumber("RotateWheel Speed", speed);

        for (LightStrip l : lightStrips) {
            l.fillHSV(wheelColor.HSV[0], wheelColor.HSV[0], wheelColor.HSV[0]);
            l.update();
        }
        if (this.wheelColor == WheelColor.BETWEEN) {
            this.panel.setSpinMotor(speed);
        }

        this.lastColor = this.wheelColor;
        SmartDashboard.putNumber("speed", speed);
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
