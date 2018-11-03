package frc.team5104.subsystem.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.util.Units;
import frc.team5104.util.console;
import frc.team5104.util.controller;
import frc.team5104.util.console.c;

public class DriveSystems extends BreakerSubsystem.Systems {
	//Device References (d + Device)
	static TalonSRX L1 = Devices.Drive.L1;
	static TalonSRX L2 = Devices.Drive.L2;
	static TalonSRX R1 = Devices.Drive.R1;
	static TalonSRX R2 = Devices.Drive.R2;
	
	//Motors
	static class motors {
		public static void set(double leftSpeed, double rightSpeed, ControlMode mode) {
			L1.set(mode, leftSpeed);
			R1.set(mode, rightSpeed);
		}
	}
	
	//Encoders
	public static class encoders {
		/**
		 * Resets both the encoders to zero
		 */
		public static void reset(int timeoutMs) {
			L1.setSelectedSensorPosition(0, 0, timeoutMs);
			R1.setSelectedSensorPosition(0, 0, timeoutMs);
		}
		
		/**
		 * @return The Left encoder's value
		 */
		public static int getLeft() {
			return L1.getSelectedSensorPosition(0);
		}
		
		/**
		 * @return The Right encoder's value
		 */
		public static int getRight() {
			return R1.getSelectedSensorPosition(0);
		}
		
		public static String getString() {
			try {
				return "L: " + getLeft() + 
						", R: " + getRight();
			} catch (Exception e) { e.printStackTrace(); return ""; }
		}
	}
	
	//Shifters
	public static class shifters {
		public static boolean inHighGear() {
			return Devices.Drive.shift.get() == DoubleSolenoid.Value.kForward;
		}
		
		/**
		 * @param high True: Set to High Gear, False: Set to Low Gear
		 */
		public static void set(boolean high) {
			if (high ? !inHighGear() : inHighGear()) {
				console.log(c.DRIVE, high ? "Shifting High" : "Shifting Low");
				Devices.Drive.shift.set(high ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
				controller.rumbleSoftFor(high ? 0.75 : 0.25, 0.2);
			}
		}
		
		public static void toggle() {
			set(!inHighGear());
		}
		
	}

	//Gyro
	public static class gyro {
		public static double getRawAngle() {
			return Devices.Drive.Gyro.getAngle();
		}
		
		public static double getAngle() {
			double a = -Devices.Drive.Gyro.getAngle();
			a /= Math.cos(Units.degreesToRadians(_DriveConstants._gyroAngle));
			return a;
		}
		
		public static void calibrate() {
			Devices.Drive.Gyro.calibrate();
		}
		
		public static void reset() {
			Devices.Drive.Gyro.reset();
		}
	}
	
	//Setup
	static void setup() {
		//Wait until Talons are Ready to Recieve
		try {
			Thread.sleep(10);
		} catch (Exception e) { console.error(e); }
		
		// Left Talons Config
		L2.set(ControlMode.Follower, L1.getDeviceID());
		L1.setInverted(false);
		L2.setInverted(false);
	
		L1.setNeutralMode(NeutralMode.Brake);
		L2.setNeutralMode(NeutralMode.Brake);
		
		L1.configClosedloopRamp(_DriveConstants._rampSeconds, 10);
        L2.configClosedloopRamp(_DriveConstants._rampSeconds, 10);
		
        L1.configAllowableClosedloopError(0, _DriveConstants._pidId, 10);
        L1.config_kF(_DriveConstants._pidId, _DriveConstants._pidF, 10);
        L1.config_kP(_DriveConstants._pidId, _DriveConstants._pidP, 10);
        L1.config_kI(_DriveConstants._pidId, _DriveConstants._pidI, 10);
        L1.config_kD(_DriveConstants._pidId, _DriveConstants._pidD, 10);
        
        //L1.configPeakCurrentLimit(DriveConstants._currentLimitPeak, 10);
        //L1.configPeakCurrentDuration(DriveConstants._currentLimitPeakTime, 10);
        //L1.configContinuousCurrentLimit(DriveConstants._currentLimitSustained, 10);
        
        //L1.enableCurrentLimit(true);
        
        
        // Right Talons Config
        R2.set(ControlMode.Follower, R1.getDeviceID());
		R1.setInverted(true);
		R2.setInverted(true);
	
		R1.setNeutralMode(NeutralMode.Brake);
		R2.setNeutralMode(NeutralMode.Brake);
		
		R1.configClosedloopRamp(_DriveConstants._rampSeconds, 10);
        R2.configClosedloopRamp(_DriveConstants._rampSeconds, 10);
		
        R1.configAllowableClosedloopError(0, _DriveConstants._pidId, 10);
        R1.config_kF(_DriveConstants._pidId, _DriveConstants._pidF, 10);
        R1.config_kP(_DriveConstants._pidId, _DriveConstants._pidP, 10);
        R1.config_kI(_DriveConstants._pidId, _DriveConstants._pidI, 10);
        R1.config_kD(_DriveConstants._pidId, _DriveConstants._pidD, 10);
        
        //R1.configPeakCurrentLimit(DriveConstants._currentLimitPeak, 10);
        //R1.configPeakCurrentDuration(DriveConstants._currentLimitPeakTime, 10);
        //R1.configContinuousCurrentLimit(DriveConstants._currentLimitSustained, 10);
        
        //R1.enableCurrentLimit(true);
		
		//Stop the motors
		DriveActions.stop();
		
		//Reset Gyro
		gyro.reset();
		
		//Reset Encoder
		encoders.reset(10);
		
		//Wait until Talons have Caught Up
		try {
			Thread.sleep(100);
		} catch (Exception e) { console.error(e); }
	}
}