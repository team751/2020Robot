/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static int LEDPort = 0;
    public static int LEDLength = 10;

    public static int multiplexerI2CREVColorDeviceId = 0;
    public static int multiplexerI2CBNO055DeviceId = 1;

    public static Port I2CMultiplexerPort = Port.kOnboard;
    public static Port colorSensorPort = Port.kOnboard;
    public static float proximityThreshhold = 200;

    // Robot-specific PIDTrajectory constants 
    public static double trackWidthMeters = 0.0762; // horizontal distance between the wheels 
                                                    // (2019 robot = 0.0762 meters (3 inches))
    public static double maxPIDTrajectoryDriveVolts;
    public static double maxPIDTrajectoryDriveAcceleration; // m/sec^2
    public static double ksVolts;
    public static double kvVoltSecondsPerMeter;
    public static double kaVoltSecondsSquaredPerMeter;
    public static double kPDriveVel;

    // Universal PIDTrajectory constants
    public static double ramseteB = 2;
    public static double ramseteZeta = 0.7;
}
