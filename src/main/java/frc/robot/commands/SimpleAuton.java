package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.core751.subsystems.DifferentialDriveTrain;
import frc.robot.subsystems.Ball;
import frc.robot.core751.wrappers.WCANSparkMax;

public class SimpleAuton extends CommandBase {
    private DifferentialDriveTrain differentialDriveTrain;
    private Ball ball;
    private boolean finished = false;
    private double startTime = 0;
    private double startBallTime = 0;
    
    public SimpleAuton(DifferentialDriveTrain differentialDriveTrain, Ball ball) {
        this.differentialDriveTrain = differentialDriveTrain;
        this.ball = ball;
        addRequirements(differentialDriveTrain, ball);
    }
    
    @Override
    public void initialize() {
        startTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void execute() {
        if(edu.wpi.first.wpilibj.Timer.getFPGATimestamp() - startTime <= Constants.simpleDriveAutonLengthSeconds) {
            this.differentialDriveTrain.getDifferentialDrive().arcadeDrive(0, 0.5);
            startBallTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
        } else if(edu.wpi.first.wpilibj.Timer.getFPGATimestamp() - startBallTime <= Constants.simpleBallAutonLengthSeconds) {
            this.differentialDriveTrain.getDifferentialDrive().arcadeDrive(0, 0);
            ball.setOutputMotor(1.0);
        } else {
            this.differentialDriveTrain.getDifferentialDrive().arcadeDrive(0, 0);
            ball.setOutputMotor(0);
            finished = true;
        }
    }
    
}
