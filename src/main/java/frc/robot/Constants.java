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

    public static int driverstickPort = 0;
    public static int leftTrigger = 2;
    public static int rightTrigger = 3;

    public static int LEDPort = 0;
    public static int LEDLength = 10;

    public static Port firstColorSensorPort = Port.kOnboard;
    public static Port secondColorSensorPort = Port.kMXP;
    public static float proximityThreshhold = 200;
    public static int panelSpinMotorID = 1;
    public static int panelPositionMotorID = 2;
    public static int panelTopLimitPin = 0;
    public static int panelBottomLimitPin = 1;
}
