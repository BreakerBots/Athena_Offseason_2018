package frc.team5104.subsystem.elevator;

import frc.team5104.subsystem.BreakerSubsystem;

public class ElevatorActions extends BreakerSubsystem.Actions {
	public static void setSpeed(double speed) {
		ElevatorManager.speed = speed;
	}
}
