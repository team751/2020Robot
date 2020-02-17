
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.core751.wrappers.WVictorSPX;

public class Ball extends SubsystemBase {
    private WVictorSPX intakeMotor;
    private WVictorSPX polycordMotor;
    private WVictorSPX outputMotor;

    
    public Ball(int intakeMotorPort, int polycordMotorPort, int outputMotorPort){
        intakeMotor = new WVictorSPX(intakeMotorPort);
        polycordMotor = new WVictorSPX(polycordMotorPort);
        outputMotor = new WVictorSPX(outputMotorPort);
    }

    public void Intake(double speed){
        setIntakeMotor(speed);
        setPolycordMotor(-speed);
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


