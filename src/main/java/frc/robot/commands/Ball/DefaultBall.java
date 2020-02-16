/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Ball;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Ball;

public class DefaultBall extends CommandBase {

    private int lBumper;
    private int rBumper;
    private int outputButton;
    private int outputReverseButton;
    private double polycordStartTime;

    private Ball ball;
    private Joystick controller;

    public DefaultBall(Ball ball, Joystick controller, int lBumper, int rBumper, 
                       int outputButton, int outputReverseButton){
        this.ball = ball;
        this.controller = controller;
        this.lBumper = lBumper;
        this.rBumper = rBumper;
        this.outputButton = outputButton;
        this.outputReverseButton = outputReverseButton;

        addRequirements(ball);
    }


    @Override
    public void execute() {
        double outSpeed = SmartDashboard.getNumber("Output speed", 0.5);
        double inSpeed = SmartDashboard.getNumber("Intake speed", 0.5); 
       
        
        if(controller.getRawButton(lBumper) || controller.getRawButton(rBumper)){
            polycordStartTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp(); //seconds
            ball.Intake(inSpeed);
        } else {
            if(edu.wpi.first.wpilibj.Timer.getFPGATimestamp() - polycordStartTime >= Constants.polycordGracePeriod) {
                ball.setPolycordMotor(0);
            }

            ball.setIntakeMotor(-inSpeed / 4);
        }

        if(controller.getRawButton(outputButton)){
            ball.setOutputMotor(outSpeed);
        } else if(controller.getRawButton(outputReverseButton)) {
            ball.setOutputMotor(-outSpeed);
        } else {
            ball.setOutputMotor(0);
        }
    }
}
