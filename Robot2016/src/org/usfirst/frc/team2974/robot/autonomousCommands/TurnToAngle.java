package org.usfirst.frc.team2974.robot.autonomousCommands;

import org.usfirst.frc.team2974.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is used to turn the robot from its current angle (START_ANGLE) to
 * its GOAL_ANGLE it does this by turn form a point.
 */
public class TurnToAngle extends Command {

	double speed = .5;
	double goalAngle;
	double tolerance = 10;
	private final double PROPORTIONAL_ZONE = 30;

	public TurnToAngle(final double angle) {
		requires(Robot.getDriveTrain());
		requires(Robot.getCompass());
		goalAngle = angle;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.getDriveTrain().setSpeeds(0, 0);
	}

	private double errorAngle() {
		double deltaYaw = Robot.getCompass().getYaw() - goalAngle;
		if (deltaYaw > 180)
			deltaYaw -= 360;

		else if (deltaYaw < -180)
			deltaYaw += 360;

		return deltaYaw;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		final double deltaYaw = errorAngle();
		final double direction = Math.signum(deltaYaw);
		final double speedMag = Math.abs(deltaYaw) / PROPORTIONAL_ZONE;
		Robot.getDriveTrain().setSpeeds(speedMag * direction, -speedMag * direction);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(errorAngle()) <= tolerance;
	}
}
