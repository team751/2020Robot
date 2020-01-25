
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.core751.wrappers.WVictorSPX;

public class Ball extends SubsystemBase {
    private WVictorSPX intakeMotor;
    private WVictorSPX polycordMotor;
    private WVictorSPX outputMotor;

    /**
     * @param ports
     * ports[0] intake,
     * ports[1] polycord,
     * ports[2] outtake
     */
    public Ball(int[] ports){
        intakeMotor = new WVictorSPX(ports[0]);
        polycordMotor = new WVictorSPX(ports[1]);
        outputMotor = new WVictorSPX(ports[2]);
    }
    public void Intake(double speed){
        setPolycordMotor(speed);
        setIntakeMotor(-speed);
    }

    public void setOutputMotor(double speed){
        outputMotor.set(speed);
    }
    public void setIntakeMotor(double speed){
        intakeMotor.set(speed);
    }
    public void setPolycordMotor(double speed){
        polycordMotor.set(speed);
    }

}


