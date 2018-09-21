package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.main.subsystems.Drive.shifters.Gear;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.console;
import com.frc5104.utilities.controller.Control;
import com.frc5104.utilities.console.c;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*Breakerbots Robotics Team 2018*/
public class Drive extends BreakerSubsystem {
	private static Drive _inst = null; 
	public static Drive getInstance() { if (_inst == null) _inst = new Drive(); return _inst; }
	
	//Device References
	private static TalonSRX L1 = Devices.Drive.L1;
	private static TalonSRX L2 = Devices.Drive.L2;
	private static TalonSRX R1 = Devices.Drive.R1;
	private static TalonSRX R2 = Devices.Drive.R2;
	private static DoubleSolenoid gearShifters = Devices.Shifting.sol;

	/**
	 * Initialize Driving (Setup Talons)
	 */
	protected void init() {
		//Reset the encoders
		L1.setSelectedSensorPosition(0, 0, 10);
		R1.setSelectedSensorPosition(0, 0, 10);
		
		//Makes L2, R2 followers
		L2.set(ControlMode.Follower, L1.getDeviceID());
		R2.set(ControlMode.Follower, R1.getDeviceID());
		
		//Inverts the motors correctly so that when L1, R1 are both set to full the robot moves forward
		L1.set(ControlMode.PercentOutput, 0);
		L1.setInverted(false);
		L2.setInverted(false);
		R1.set(ControlMode.PercentOutput, 0);
		R1.setInverted(true);
		R2.setInverted(true);
	}
	
	/**
	 * Sets the speed of the motors
	 * @param l The left speed
	 * @param r The right speed
	 */
	public void set(double l, double r) {
		L1.set(ControlMode.PercentOutput, l);
		R1.set(ControlMode.PercentOutput, r);
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
		public static Gear get() {
			return inHighGear() ? Gear.High : Gear.Low;
		}
		
		public static boolean inHighGear() {
			return gearShifters.get() == DoubleSolenoid.Value.kForward;
		}
		
		/**
		 * @param high True: Set to High Gear, False: Set to Low Gear
		 */
		public static void set(boolean high) {
			if (high ? !inHighGear() : inHighGear()) {
				console.log(c.DRIVE, high ? "Shifting High" : "Shifting Low");
				gearShifters.set(high ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
			}
		}
		
		public static enum Gear { 
			Low, 
			High 
		}
		public static void set(Gear gear) {
			set(gear == Gear.Low);
		}
	}

	protected void teleopUpdate() {
		L1.set(ControlMode.PercentOutput, HMI.Drive.driveY() - controller.getAxis(HMI.Drive._driveX));
		R1.set(ControlMode.PercentOutput, HMI.Drive.driveY() + controller.getAxis(HMI.Drive._driveX));
	
		if (controller.getAxis(HMI.Drive._shift) > 0.6)
			shifters.set(true);
		else
			shifters.set(false);
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
		shifters.set(Gear.Low);
	}

	protected void autoInit() {
		shifters.set(Gear.Low);
	}
}
