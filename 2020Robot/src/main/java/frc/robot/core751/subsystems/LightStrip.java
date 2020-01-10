
package frc.robot.core751.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightStrip extends SubsystemBase {

    private AddressableLED LED;
    public int length;

    public LightStrip(int port, int length) {
        this.LED = new AddressableLED(port);
        this.LED.setLength(length);
        this.length = length;
    }

    public void start() {
        this.LED.start();
    }

    public void stop() {
        this.LED.stop();
    }

    public void setData(AddressableLEDBuffer b) {
        this.LED.setData(b);
    }


}