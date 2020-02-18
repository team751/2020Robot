package frc.robot.commands.Panel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Panel;
import frc.robot.subsystems.Panel.PositionState;

public class TogglePanelPosition extends CommandBase {

    private Panel panel;
    private boolean finished;
    private PositionState target;

    public TogglePanelPosition (Panel panel) {
        this.panel = panel;
        addRequirements(this.panel);
    }
    
    @Override
    public void initialize(){
        this.finished = false;
        this.target = this.panel.positionState;
        if (this.target == PositionState.UNKNOWN) {
            this.target = this.panel.lastPositionState;
        }

        //This is a convaluted way to do this but we need it fast
        if (this.target == PositionState.UP) {
            this.target = PositionState.DOWN;
        }else {
            this.target = PositionState.UP;
        }

        if (this.target == PositionState.UP) {
            this.panel.setPositionMotor(1);
        }
        if (this.target == PositionState.DOWN) {
            this.panel.setPositionMotor(-1);
        }
    }

    @Override
    public void execute () {
        if (this.panel.updateState() == this.target) {
            this.panel.setPositionMotor(0);
            this.finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

}