package frc.robot.core751.wrappers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.SpeedController;

public class WCANSparkMax implements SpeedController {
    private CANSparkMax mot;

    public WCANSparkMax(int deviceID, CANSparkMaxLowLevel.MotorType type){
        this.mot = new CANSparkMax(deviceID, type);
    }

    @Override
    public void pidWrite(double output) {
        mot.pidWrite(output);
    }

    @Override
    public void set(double speed) {
        mot.set(speed);
    }
   
    public CANEncoder getEncoder(){
        return mot.getEncoder();
    }


    public void setSoftLimit(CANSparkMax.SoftLimitDirection direction, float limit){
        mot.setSoftLimit(direction, limit);
    }

    @Override
    public double get() {
        return mot.get();
    }

    @Override
    public void setInverted(boolean isInverted) {
        mot.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return mot.getInverted();
    }

    @Override
    public void disable() {
        mot.disable();
    }

    @Override
    public void stopMotor() {
        mot.stopMotor();
    }

    public CANPIDController getPIDController(){
        return mot.getPIDController();
    }

}