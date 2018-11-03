package frc.team5104.subsystem.climber;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.climber.ClimberSystems.solenoid;
import frc.team5104.util.console;

public class ClimberActions extends BreakerSubsystem.Actions {
	public static void switchPto() {
		console.log("Switching PTO to ", solenoid.inElevator() ? "Winch" : "Elevator");
		ClimberSystems.ptoSol.set(solenoid.inElevator());
		ClimberManager.speed = 0;
	}
	
	public static void setSpeed(double speed) {
		ClimberManager.speed = speed;
	}
}
