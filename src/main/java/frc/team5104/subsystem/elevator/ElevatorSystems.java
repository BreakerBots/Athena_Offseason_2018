package frc.team5104.subsystem.elevator;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

class ElevatorSystems extends BreakerSubsystem.Systems {
	static TalonSRX drivers = Devices.Elevator.a;
	
	public static class hardLimitSwitches {
		public static boolean hitLower() {
			return drivers.getSensorCollection().isFwdLimitSwitchClosed();
		}
		
		public static boolean hitUpper() {
			return drivers.getSensorCollection().isRevLimitSwitchClosed();
		}
		
		public static void enable() {
			drivers.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
			drivers.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
		}
	
		public static void disable() {
			drivers.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen, 10);
			drivers.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen, 10);
		}
	}
	
	public static class softLimitSwitches {
		public static void enable() {
			drivers.configReverseSoftLimitEnable(true, 10);
			drivers.configReverseSoftLimitThreshold(_ElevatorConstants._softStopTop, 10);

			drivers.configForwardSoftLimitEnable(true, 10);
			drivers.configForwardSoftLimitThreshold(_ElevatorConstants._softStopBottom, 10);
		}
		
		public static void disable() {
			drivers.configReverseSoftLimitEnable(false, 10);
			drivers.configReverseSoftLimitThreshold(_ElevatorConstants._softStopTop, 10);

			drivers.configForwardSoftLimitEnable(false, 10);
			drivers.configForwardSoftLimitThreshold(_ElevatorConstants._softStopBottom, 10);
		}
	}
	
	public static class encoders {
		public static int getPosition() {
			return drivers.getSelectedSensorPosition(0);
		}
		
		public static int getVelocity() {
			return drivers.getSelectedSensorVelocity(0);
		}
		
		public static void reset() {
			drivers.setSelectedSensorPosition(0, 0, 10);
		}
	}
}