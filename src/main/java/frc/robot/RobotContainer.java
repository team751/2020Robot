/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
/*import frc.robot.commands.Panel.GoToColor;
import frc.robot.commands.Panel.ManualPanel;
import frc.robot.commands.Panel.RotateThenSelect;
import frc.robot.commands.Panel.RotateWheel;
import frc.robot.commands.Panel.TogglePanelPosition;*/
import frc.robot.core751.commands.Drivetrain.ArcadeDrive;
import frc.robot.core751.commands.Drivetrain.PIDTrajectoryDrive;
import frc.robot.core751.commands.lightstrip.TeamColorLights;
import frc.robot.core751.subsystems.DifferentialDriveTrain;
import frc.robot.core751.subsystems.LightStrip;
import frc.robot.core751.wrappers.ArduinoGyro;
//import frc.robot.subsystems.Panel;

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

  

  private final DifferentialDriveTrain differentialDriveTrain = new DifferentialDriveTrain(Constants.leftDrivetrainIDs, Constants.rightDrivetrainIDs, Constants.driveTrainMotorType);
  private final ArcadeDrive arcadeDrive = new ArcadeDrive(Constants.driverStick, differentialDriveTrain);
  private final PowerDistributionPanel pdp = new PowerDistributionPanel();

  public final ArduinoGyro arduinoGyro = new ArduinoGyro();

  //private final LightStrip lightStrip = new LightStrip(Constants.LEDPort, Constants.LEDLength);
  //private final TeamColorLights teamColorLights = new TeamColorLights(lightStrip);
  /*
  private final Panel panel = new Panel(Constants.leftColorsensorPort, Constants.rightColorsensorPort, Constants.panelSpinID, Constants.panelRotateID, Constants.panelTopLimitPort, Constants.panelBottomLimitPort);
  private final GoToColor goToColor = new GoToColor(lightStrip, panel);
  private final RotateWheel rotateWheel = new RotateWheel(lightStrip, panel);
  private final ManualPanel manualPanel = new ManualPanel(panel, Constants.driverStick, Constants.rightTrigger, Constants.leftTrigger);
  private final RotateThenSelect rotateThenSelect = new RotateThenSelect(panel, lightStrip);
  private final TogglePanelPosition togglePanelPosition = new TogglePanelPosition(panel);
 */
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
    differentialDriveTrain.setDefaultCommand(arcadeDrive);
    SmartDashboard.putData(pdp);
    // lightStrip.setDefaultCommand(teamColorLights);


    // panel.setDefaultCommand(manualPanel);
    // Constants.panelToggleButton.whenPressed(togglePanelPosition);
    // SmartDashboard.putData(togglePanelPosition);
    // SmartDashboard.putData(goToColor);
    // SmartDashboard.putData(rotateWheel);
    // SmartDashboard.putData(rotateThenSelect);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new PIDTrajectoryDrive(differentialDriveTrain, 
                                  Constants.pathWeaverJSONPath);
  }
}
