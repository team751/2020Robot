package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.PositionState;

public class ManualPanel extends CommandBase {

    private Panel panel;
    private Joystick stick;
    private int rightTrigger;
    private int leftTrigger;

    public ManualPanel (Panel panel, Joystick stick, int rightTrigger, int leftTrigger) {
        this.panel = panel;
        this.stick = stick;
        this.rightTrigger = rightTrigger;
        this.leftTrigger = leftTrigger;
        addRequirements(panel);
    }

    @Override
    public void execute() {
        int pov = this.stick.getPOV();
        this.panel.updateState();
        if ((pov == 0||pov == 45||pov == 315) && this.panel.positionState != PositionState.UP) {
            this.panel.setPositionMotor(1);
            if (this.panel.updateState() == PositionState.UP) {
                this.panel.setPositionMotor(0);
            } 
        }else if ((pov == 135||pov==180||pov==225) && this.panel.positionState != PositionState.DOWN) {
            this.panel.setPositionMotor(-1);
            if (this.panel.updateState() == PositionState.DOWN) {
                this.panel.setPositionMotor(0);
            } 
        } else if (this.panel.positionState == PositionState.UP){
            this.panel.setPositionMotor(this.panel.getPassiveUpSpeed());
        } else if (this.panel.positionState == PositionState.DOWN){
            this.panel.setPositionMotor(-this.panel.getPassiveUpSpeed());
        }else {
            this.panel.setPositionMotor(0);
        }

        this.panel.setSpinMotor(this.stick.getRawAxis(rightTrigger)-this.stick.getRawAxis(leftTrigger));
        
    }
}