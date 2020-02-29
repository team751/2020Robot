
package core751.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightStrip extends SubsystemBase {

    private AddressableLED LED;
    public int length;
    public AddressableLEDBuffer buffer;
    public int tic;

    public LightStrip(int port, int length) {
        this.LED = new AddressableLED(port);
        this.LED.setLength(length);
        this.length = length;
        this.buffer = new AddressableLEDBuffer(length);
        this.tic = 0;
    }

    public void start() {
        this.LED.start();
    }

    public void stop() {
        this.LED.stop();
    }

    public void advanceTic(){
        this.tic++;
        this.tic%=2048;
    }

    public void update() {
        this.LED.setData(this.buffer);
        this.advanceTic();
    }

    public void setHSV(int i, int h, int s, int v) {
        this.buffer.setHSV(i, h/2, s, v);
    }

    public void setHSVWave(int i, int cycleCount, int h, int s, int v) {
        int ni = i + tic/5;
        ni %= this.length;
        
        v = (int) (v *   (  (Math.cos( (((double)(ni))/this.length)*cycleCount*2  * Math.PI )  + 2 )  /3)    );
        
        this.setHSV(i, h, s, v);
    }

    public void setHSVWave(int i, int cycleCount, int[] c) {
        this.setHSVWave(i, cycleCount, c[0], c[1], c[2]);
    }


}