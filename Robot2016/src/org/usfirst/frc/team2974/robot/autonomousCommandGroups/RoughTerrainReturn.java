package org.usfirst.frc.team2974.robot.autonomousCommandGroups;

import org.usfirst.frc.team2974.robot.autonomousCommands.ArmDown;
import org.usfirst.frc.team2974.robot.autonomousCommands.DriveStraight;
import org.usfirst.frc.team2974.robot.autonomousCommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RoughTerrainReturn extends CommandGroup {
    
    public  RoughTerrainReturn() {
		addParallel(new ArmDown());
		addSequential(new DriveStraight(4.5, -.5));
		addSequential(new Wait(2));
		addSequential(new DriveStraight(3, .5));
    }
}