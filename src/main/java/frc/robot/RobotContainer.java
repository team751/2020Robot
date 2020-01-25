/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.SensorLights;
import frc.robot.core751.commands.lightstrip.TeamColorLights;
import frc.robot.core751.subsystems.DifferentialDriveTrain;
import frc.robot.core751.subsystems.I2CMultiplexer;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.core751.wrappers.BNO055;
import frc.robot.subsystems.Wheel;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public final BNO055 bno055 = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,
                                                  BNO055.vector_type_t.VECTOR_EULER);

  private final LightStrip lightStrip = new LightStrip(Constants.LEDPort, Constants.LEDLength);
  private final I2CMultiplexer i2cMultiplexer = new I2CMultiplexer(Constants.I2CMultiplexerPort);
  private final Wheel wheel = new Wheel(Constants.colorSensorPort);

  private final TeamColorLights teamColorLights = new TeamColorLights(i2cMultiplexer, 
                                                                      Constants.multiplexerI2CREVColorDeviceId, 
                                                                      lightStrip);
  private final SensorLights sensorLights = new SensorLights(i2cMultiplexer,
                                                             Constants.multiplexerI2CBNO055DeviceId,
                                                             lightStrip, wheel);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    lightStrip.setDefaultCommand(sensorLights);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //TODO: Add auto command
    return null;
  }
}
