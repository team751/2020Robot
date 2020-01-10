/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.core751.commands.camera.AssignCameraCommand;
import frc.robot.core751.commands.camera.SwitchCameraCommand;
import frc.robot.core751.commands.lightstrip.TeamColorLights;
import frc.robot.core751.subsystems.Camera;
import frc.robot.core751.subsystems.LightStrip;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final LightStrip lightStrip = new LightStrip(Constants.LEDPort, Constants.LEDLength);
  private final Camera camera = new Camera(Constants.mainCameraDeviceNum);

  private final SwitchCameraCommand m_switchCameraCommand = new SwitchCameraCommand(camera, 
                                                                                    Constants.mainCameraDeviceNum);
  private final AssignCameraCommand m_assignCameraCommand = new AssignCameraCommand(camera, 
                                                                                    Constants.mainCameraDeviceNum);

  private final TeamColorLights teamColorLights = new TeamColorLights(lightStrip);


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
    lightStrip.setDefaultCommand(teamColorLights);
    camera.setDefaultCommand(m_assignCameraCommand);
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
