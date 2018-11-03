package frc.team5104.subsystem.elevator;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

class ElevatorSystems extends BreakerSubsystem.Systems {
	static Solenoid ptoSol = Devices.Climbing.ptoSol;
	
	public static class hardLimitSwitches {
		public static boolean hitLower() {
			return Devices.Elevator.a.getSensorCollection().isFwdLimitSwitchClosed();
		}
		
		public static boolean hitUpper() {
			return Devices.Elevator.a.getSensorCollection().isRevLimitSwitchClosed();
		}
		
		public static void enable() {
			Devices.Elevator.a.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
			Devices.Elevator.a.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
		}
	
		public static void disable() {
			Devices.Elevator.a.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen, 10);
			Devices.Elevator.a.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen, 10);
		}
	}
	
	public static class softLimitSwitches {
		public static void enable() {
			Devices.Elevator.a.configReverseSoftLimitEnable(true, 10);
			Devices.Elevator.a.configReverseSoftLimitThreshold(_ElevatorConstants._softStopTop, 10);

			Devices.Elevator.a.configForwardSoftLimitEnable(true, 10);
			Devices.Elevator.a.configForwardSoftLimitThreshold(_ElevatorConstants._softStopBottom, 10);
		}
		
		public static void disable() {
			Devices.Elevator.a.configReverseSoftLimitEnable(false, 10);
			Devices.Elevator.a.configReverseSoftLimitThreshold(_ElevatorConstants._softStopTop, 10);

			Devices.Elevator.a.configForwardSoftLimitEnable(false, 10);
			Devices.Elevator.a.configForwardSoftLimitThreshold(_ElevatorConstants._softStopBottom, 10);
		}
	}
	
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
		//remember to change in climber too
		public static boolean inElevator() {
			return !ptoSol.get();
		}
		
		public static boolean inWinch() {
			return ptoSol.get();
		}
	}
}