/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.core751.subsystems.DifferentialDriveTrain;
import frc.robot.core751.subsystems.DifferentialDriveTrain.driveMotor;

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

    public enum Controller { // Button mappings for the XBOX One controller
        A(1), B(2), X(3), Y(4), LB(5), RB(6), LT(2), // Must use .getRawAxis()
        RT(3), // Must use .getRaxAxis()
        BACK(7), START(8), LEFT_AXIS_PRESS(9), // X-Axis: -1.000 to 1.000 (stick.GetX())
                                               // Y-Axis: -1.000 to 1.000 (stick.GetY())
        RIGHT_AXIS_PRESS(10);

        private int buttonNum;

        private Controller(int value) {
            this.buttonNum = value;
        }

        public int getButtonMapping() {
            return this.buttonNum;
        }

    }
    
    public static Joystick driverStick = new Joystick(Constants.driveStickPort);

      /*================================/ 
     /===========Drive Train===========/
    /================================*/
    public static int leftDrivetrainIDs[] = new int[] { 1, 2, 3 };
    public static int rightDrivetrainIDs[] = new int[] { 4, 5, 6 };
    public static DifferentialDriveTrain.driveMotor driveTrainMotorType = driveMotor.kSparkMaxBrushless;

    public static int driveStickPort = 0;

      /*================================/ 
     /===========Ball==================/
    /================================*/

      /*================================/ 
     /===========Panel=================/
    /================================*/
    public static int panelRotateID = 10;
    public static int panelSpinID = 11;

    public static int panelBottomLimitPort = 0;
    public static int panelTopLimitPort = 1;

    public static Port leftColorsensorPort = Port.kOnboard;
    public static Port rightColorsensorPort = Port.kMXP;

    public static float proximityThreshhold = 200;

    public static int leftTrigger = 2;
    public static int rightTrigger = 3;

    public static Button panelToggleButton = new JoystickButton(driverStick, Controller.Y.buttonNum);

     /*======================================/
     /===========Lightstrip=================/
    /=====================================*/
    public static int LEDPort = 0;
    public static int LEDLength = 10;


    // Robot-specific PIDTrajectory constants 
    public static double trackWidthMeters = 0.586486; // horizontal distance between the wheels 
                                                    // (2019 robot = 0.19431 meters (.6375 inches))
    public static double maxPIDTrajectoryDriveNeomVelocity = 1.5;//3.9624;
    public static double maxPIDTrajectoryDriveAcceleration = 0.75;//2.4384; // m/sec^2
    public static double ksVolts = 0.116; //update 
    public static double kvVoltSecondsPerMeter = 0.0401; //update
    public static double kaVoltSecondsSquaredPerMeter = 0.00971; //update
    public static double kPDriveVel = 0.466; //update

    public static String pathWeaverJSONPath = "paths/Test.wpilib.json";

    // Universal PIDTrajectory constants
    public static double ramseteB = 2;
    public static double ramseteZeta = 0.7;
}
