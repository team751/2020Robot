package core751.wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;

public class WVictorSPX {

    private VictorSPX motor;

    public WVictorSPX (int devNum) {
        this.motor = new VictorSPX(devNum);
    }

    public void set(double speed) {
        this.motor.set(ControlMode.PercentOutput, speed);
    }

    public void stopMotor() {
        this.set(0);
    }

}