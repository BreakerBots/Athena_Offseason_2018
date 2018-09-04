package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.main.subsystems.Drive.shifters.Gear;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;
import com.frc5104.utilities.ControllerHandler.Control;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*Breakerbots Robotics Team 2018*/
public class Drive implements BreakerSubsystem {
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
	public void init() {
		L1.setSelectedSensorPosition(0, 0, 10);
		R1.setSelectedSensorPosition(0, 0, 10);
		L2.set(ControlMode.Follower, L1.getDeviceID());
		R2.set(ControlMode.Follower, R1.getDeviceID());
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
			console.log(high ? "Shifting High" : "Shifting Low", Type.DRIVE);
			gearShifters.set(high ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
		}
		
		public static enum Gear { 
			Low, 
			High 
		}
		public static void set(Gear gear) {
			set(gear == Gear.Low);
		}
	}

	public void teleopUpdate() {
		ControllerHandler controller = ControllerHandler.getInstance();
		L1.set(ControlMode.PercentOutput, controller.getAxis(Control.LY) - controller.getAxis(Control.LX));
		R1.set(ControlMode.PercentOutput, controller.getAxis(Control.LY) + controller.getAxis(Control.LX));
	
		if (ControllerHandler.getInstance().getAxis(HMI.kDriveShift) > 0.6)
			shifters.set(true);
		else
			shifters.set(false);
	}

	public void autoUpdate() {
		
	}

	public void idleUpdate() {
		
	}

	public void postToNetwork() {
		
	}

	public void initNetworkPosting() {
		
	}

	public void teleopInit() {
		shifters.set(Gear.Low);
	}

	public void autoInit() {
		shifters.set(Gear.Low);
	}
}
