package org.usfirst.frc.team2974.robot.autonomousCommands;

import org.usfirst.frc.team2974.robot.Robot;
import org.usfirst.frc.team2974.robot.subsystems.Compass;

import edu.wpi.first.wpilibj.command.Command;

public class MoveToObstacle extends Command {
	private double threshold;
	private double direction;
	private Compass thisCompass;

	public MoveToObstacle(double direction) {
		this.direction = direction;
	}

	@Override
	protected void initialize() {
		Robot.getDriveTrain().setSpeeds(1 * direction, 1 * direction);// Magic
																		// number
																		// -
																		// change
																		// this
																		// after
																		// testing
																		// with
																		// actual
																		// robot
		threshold = 7 * direction; // another magic number to test
	}

	@Override
	protected void execute() {
		// Create an if statement which calls is finished
		if (isFinished())
			end();
	}

	@Override
	protected boolean isFinished() {
		// Create an if statement which will call end
		if (Robot.getCompass().getPitch() > threshold) {
			return true;
		}
		return false;
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	public Compass getThisCompass() {
		return thisCompass;
	}

	public void setThisCompass(Compass thisCompass) {
		this.thisCompass = thisCompass;
	}

}
