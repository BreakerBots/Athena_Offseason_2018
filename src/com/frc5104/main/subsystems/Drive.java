package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.main.Units;
import com.frc5104.math.RobotDriveSignal;
import com.frc5104.math.RobotDriveSignal.DriveUnit;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.Deadband;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;
import edu.wpi.first.wpilibj.DoubleSolenoid;


/*Breakerbots Robotics Team 2018*/
public class Drive extends BreakerSubsystem {
	//Device References (d + Device)
	private static TalonSRX L1 = Devices.Drive.L1;
	private static TalonSRX L2 = Devices.Drive.L2;
	private static TalonSRX R1 = Devices.Drive.R1;
	private static TalonSRX R2 = Devices.Drive.R2;
	
	/**
	 * Setup/Configure Devices
	 */
	private void configDevices() {
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
		
		L1.configClosedloopRamp(Constants.Drive._rampSeconds, 10);
        L2.configClosedloopRamp(Constants.Drive._rampSeconds, 10);
		
        L1.configAllowableClosedloopError(0, Constants.Drive._highPidId, 10);
        L1.config_kF(Constants.Drive._highPidId, Constants.Drive.highDrivePidF, 10);
        L1.config_kP(Constants.Drive._highPidId, Constants.Drive.highDrivePidP, 10);
        L1.config_kI(Constants.Drive._highPidId, Constants.Drive.highDrivePidI, 10);
        L1.config_kD(Constants.Drive._highPidId, Constants.Drive.highDrivePidD, 10);
        
        L1.configAllowableClosedloopError(0, Constants.Drive._lowPidId, 10);
        L1.config_kF(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidF, 10);
        L1.config_kP(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidP, 10);
        L1.config_kI(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidI, 10);
        L1.config_kD(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidD, 10);
        
        //L1.configPeakCurrentLimit(Constants.Drive._currentLimitPeak, 10);
        //L1.configPeakCurrentDuration(Constants.Drive._currentLimitPeakTime, 10);
        //L1.configContinuousCurrentLimit(Constants.Drive._currentLimitSustained, 10);
        
        //L1.enableCurrentLimit(true);
        
        
        // Right Talons Config
        R2.set(ControlMode.Follower, R1.getDeviceID());
		R1.setInverted(true);
		R2.setInverted(true);
	
		R1.setNeutralMode(NeutralMode.Brake);
		R2.setNeutralMode(NeutralMode.Brake);
		
		R1.configClosedloopRamp(Constants.Drive._rampSeconds, 10);
        R2.configClosedloopRamp(Constants.Drive._rampSeconds, 10);
		
        R1.configAllowableClosedloopError(0, Constants.Drive._highPidId, 10);
        R1.config_kF(Constants.Drive._highPidId, Constants.Drive.highDrivePidF, 10);
        R1.config_kP(Constants.Drive._highPidId, Constants.Drive.highDrivePidP, 10);
        R1.config_kI(Constants.Drive._highPidId, Constants.Drive.highDrivePidI, 10);
        R1.config_kD(Constants.Drive._highPidId, Constants.Drive.highDrivePidD, 10);
        
        R1.configAllowableClosedloopError(0, Constants.Drive._lowPidId, 10);
        R1.config_kF(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidF, 10);
        R1.config_kP(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidP, 10);
        R1.config_kI(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidI, 10);
        R1.config_kD(Constants.Drive._lowPidId, Constants.Drive.lowDrivePidD, 10);
        
        //R1.configPeakCurrentLimit(Constants.Drive._currentLimitPeak, 10);
        //R1.configPeakCurrentDuration(Constants.Drive._currentLimitPeakTime, 10);
        //R1.configContinuousCurrentLimit(Constants.Drive._currentLimitSustained, 10);
        
        //R1.enableCurrentLimit(true);
		
		//Stop the motors
		set(new RobotDriveSignal(0, 0, DriveUnit.percentOutput));
		
		//Reset Gyro
		gyro.reset();
		
		//Reset Encoder
		encoders.reset(10);
		
		//Wait until Talons have Caught Up
		try {
			Thread.sleep(100);
		} catch (Exception e) { console.error(e); }
	}
	
	/**
	 * Sets the speed of the drive motors to the corresponding speeds specified in the Drive Signal
	 * @param signal
	 */
	public static void set(RobotDriveSignal signal) {
		signal.leftSpeed = signal.leftSpeed * Constants.Drive._leftAccount;
		signal.rightSpeed = signal.rightSpeed * Constants.Drive._rightAccount;
		switch (signal.unit) {
			case percentOutput: {
				L1.set(ControlMode.PercentOutput, signal.leftSpeed);
				R1.set(ControlMode.PercentOutput, signal.rightSpeed);
				break;
			}
			case feetPerSecond: {
				L1.set(ControlMode.Velocity, Units.feetPerSecondToTalonVel(signal.leftSpeed));
				R1.set(ControlMode.Velocity, Units.feetPerSecondToTalonVel(signal.rightSpeed));
				break;
			}
		}
	}
	
	/**
	 * Stops the drive motors
	 */
	public static void stop() {
		set(new RobotDriveSignal(0, 0, DriveUnit.percentOutput));
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
			a /= Math.cos(Units.degreesToRadians(Constants.Drive._gyroAngle));
			return a;
		}
		
		public static void calibrate() {
			Devices.Drive.Gyro.calibrate();
		}
		
		public static void reset() {
			Devices.Drive.Gyro.reset();
		}
	}
	
	
	
	
	// -- Subsystem Functions
	protected Drive() {
		
	}
	
	protected void teleopUpdate() {
		//Driving + Controll Processing
		double x = Deadband.get(HMI.Drive.driveX(), HMI.Drive._deadbandX);
		x = HMI.Drive._driveCurve.getPoint(x);
		double y = Deadband.get(HMI.Drive.driveY(), HMI.Drive._deadbandY);
		set(new RobotDriveSignal(y - x, y + x, DriveUnit.percentOutput));

		//Shifting
		if (controller.getPressed(HMI.Drive._shift))
			shifters.toggle();
	}

	protected void autoUpdate() {
		
	}

	protected void idleUpdate() {
		
	}

	protected void postToNetwork() {
		
	}

	protected void initNetworkPosting() {
		
	}

	protected void teleopInit() {
		shifters.set(false);
	}

	protected void autoInit() {
		shifters.set(false);
	}

	protected void init() {
		configDevices();
	}

	public static void debug() {
		console.log(
				c.DRIVE,
				"LE: " + String.format("%.2f", encoders.getLeft()) + "\t",
				"RE: " + String.format("%.2f", encoders.getRight()) + "\t",
				"An: " + String.format("%.2f", gyro.getAngle())
		);
	}

	protected void robotDisabled() {
		stop();
	}
}
