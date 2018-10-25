package frc.team5104.subsystem.drive;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class DriveManager extends BreakerSubsystem.Manager {
	
	public void enabled(RobotMode mode) {
		
	}
	
	public void update() {
		
	}

	public void disabled() {
		
	}
	
	public DriveManager() {
		DriveSystems.setup();
	}
}
