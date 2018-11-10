package frc.team5104.subsystem.climber;

import edu.wpi.first.wpilibj.Solenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

public class ClimberSystems extends BreakerSubsystem.Systems {
	static Solenoid ptoSol = Devices.Climbing.ptoSol;
	
	public static class encoders {
		public static int getPosition() {
			return Devices.Elevator.a.getSelectedSensorPosition(0);
		}
		
		public static int getVelocity() {
			return Devices.Elevator.a.getSelectedSensorVelocity(0);
		}
		
		public static void reset() {
			Devices.Elevator.a.setSelectedSensorPosition(0, 0, 10);
		}
	}
	
	public static class solenoid {
		// remmber
		public static boolean inElevator() {
			return !ptoSol.get();
		}
		
		public static boolean inWinch() {
			return ptoSol.get();
		}
	}
}