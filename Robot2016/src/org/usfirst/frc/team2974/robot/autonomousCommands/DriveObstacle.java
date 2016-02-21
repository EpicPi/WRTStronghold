package org.usfirst.frc.team2974.robot.autonomousCommands;

import org.usfirst.frc.team2974.robot.Robot;
import org.usfirst.frc.team2974.robot.subsystems.Compass;
import org.usfirst.frc.team2974.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveObstacle extends Command {
	private double yaw;
	private double speed  =.8;
	private final double multiplierConstatnt = 1.5;
	Compass compass = Robot.compass;
	DriveTrain driveTrain = Robot.driveTrain;

    public DriveObstacle() {
    	requires(driveTrain);
    	requires(compass);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	yaw = compass.getYaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speedLeft = speed;
    	double speedRight = speed;
    	if(compass.getYaw()>yaw)
    	{
    		//turning left
    		speedLeft +=(compass.getYaw()-yaw)* multiplierConstatnt;
    		speedRight -= (compass.getYaw()-yaw)* multiplierConstatnt;
    	}
    	else
    	{
    		//turning right
    		speedLeft -= (yaw - compass.getYaw())* multiplierConstatnt;
    		speedRight += (yaw - compass.getYaw())* multiplierConstatnt;
    	}
    	driveTrain.setSpeeds(speedLeft, speedRight);    	

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
