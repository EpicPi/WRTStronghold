package org.usfirst.frc.team2974.diagnostics;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DataCollector extends DataGatherer{
	public void updateDashboard(String message){
		SmartDashboard.putString("message:", message);
	}
}
