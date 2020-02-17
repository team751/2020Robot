package dc;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class ArduinoGyro implements Gyro {
    SerialPort serial;

    private Double oneSecondAgoHeading;

    private double heading;
    private double rate;
    private double pastTime;
    String bufferStr = "";

    public ArduinoGyro() {
        serial = new SerialPort(9600, SerialPort.Port.kUSB);
        serial.setReadBufferSize(1);
        serial.setTimeout(0.001);
    }

    @Override
    public void close() throws Exception {
        serial.close();
    }

    @Override
    public void calibrate() {
        serial.writeString("c");
    }

    @Override
    public void reset() {
        calibrate();
    }

    @Override
    public double getAngle() {
        update();
        return heading;
    }

    @Override
    public double getRate() {
        update();
        return rate;
    }

    public void update() {
        if(serial.getBytesReceived() > 0) {
            bufferStr += serial.readString();

            if(bufferStr.endsWith("e")) {
                
                bufferStr = bufferStr.split("e")[0];

                try {
                    heading = Double.valueOf(bufferStr);
                    System.out.println("heading set to " + heading);
                } catch(NumberFormatException e) {
                    System.out.println("ERROR = " + bufferStr);
                }

                bufferStr = "";
            }
        }

        double currentTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp(); //seconds

        if(oneSecondAgoHeading == null) {
            oneSecondAgoHeading = heading;
            pastTime = currentTime;
        } else if(currentTime - pastTime >= 1) {
            rate = oneSecondAgoHeading - heading;
            oneSecondAgoHeading = heading;
        }
    }
}