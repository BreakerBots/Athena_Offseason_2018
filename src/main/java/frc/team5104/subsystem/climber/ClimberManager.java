package frc.team5104.subsystem.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class ClimberManager extends BreakerSubsystem.Manager {
	
	static double speed = 0;
	
	public void enabled(RobotMode mode) {
		
	}
	
	public void update() {
		if(ClimberSystems.solenoid.inWinch()) {
			Devices.Elevator.a.set(ControlMode.PercentOutput, speed);
		}
	}

	public void disabled() {
		Devices.Elevator.a.set(ControlMode.PercentOutput, 0);
	}
	
}
