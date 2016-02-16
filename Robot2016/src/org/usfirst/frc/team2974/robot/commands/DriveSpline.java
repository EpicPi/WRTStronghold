package org.usfirst.frc.team2974.robot.commands;

import org.usfirst.frc.team2974.robot.Robot;
import org.usfirst.frc.team2974.robot.RobotMap;
import org.usfirst.frc.team2974.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import motionProfilling.Coordinate;
import motionProfilling.MotionControl;
import motionProfilling.Position;

/**
 *
 */
public class DriveSpline extends Command {
	MotionControl mc;
	double offsetTime;
	DriveTrain drive = Robot.driveTrain;
    public DriveSpline() {
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mc = new MotionControl(SmartDashboard.getString("Spline","0,0,0:1,1,90" ),0,0);
    	offsetTime = Timer.getFPGATimestamp();
    	takeSmartDashValues();
    	drive.resetEncoders();
    	drive.leftController.enable();
    	drive.rightController.enable();
    }
    private void takeSmartDashValues()
    {
    	drive.leftController.setkV(SmartDashboard.getNumber("kV"));
    	drive.leftController.setkA(SmartDashboard.getNumber("kA"));
    	drive.leftController.setPID(SmartDashboard.getNumber("P"), SmartDashboard.getNumber("I"), SmartDashboard.getNumber("D")); 
    	drive.rightController.setkV(SmartDashboard.getNumber("kV"));
    	drive.rightController.setkA(SmartDashboard.getNumber("kA"));
    	drive.rightController.setPID(SmartDashboard.getNumber("P"), SmartDashboard.getNumber("I"), SmartDashboard.getNumber("D")); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	dumpSmartDashBoardValuse();
    	Robot.driveTrain.setSetPoint(mc,Math.min(Timer.getFPGATimestamp()-offsetTime, mc.getMaxTime()) );
    	
    	
    }
    public void dumpSmartDashBoardValuse()
    {
    	Position pos = mc.getPosition(Math.min(mc.getMaxTime(), Timer.getFPGATimestamp()-offsetTime));
    	SmartDashboard.putNumber("DistanceLeft", pos.totalDistanceLeft);
    	SmartDashboard.putNumber("DistanceRight", pos.totalDistanceRight);
    	SmartDashboard.putNumber("ErrorLeft", pos.totalDistanceLeft-RobotMap.encoderLeft.getDistance());
    	SmartDashboard.putNumber("ErrorRight", pos.totalDistanceRight-RobotMap.encoderRight.getDistance());
    	 //System.out.println(Timer.getFPGATimestamp()-offsetTime);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // System.out.println(mc.getMaxTime());
        return Timer.getFPGATimestamp()-offsetTime>=mc.getMaxTime()+5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.leftController.disable();
    	drive.rightController.disable();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
