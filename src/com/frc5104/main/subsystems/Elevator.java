package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.calc.BreakerMath;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.Deadband;

/*Breakerbots Robotics Team 2018*/
public class Elevator extends BreakerSubsystem {
	private static Elevator _inst = null; 
	public static Elevator getInstance() { if (_inst == null) _inst = new Elevator(); return _inst; }
	
	/*
	 * Elevator Vocab:
	 *   - Word: Description/Definition
	 * 		- Subword [MODIFIER] (ABBRIVIATION): Description/Definition <Relative Reference>
	 * 
	 *   - Soft Stop: A fake limit switch based of a encoder position
	 *   
	 *   - Drivers: The motors in the PTO controlling the movement of the elevator
	 */
	
	
	
	
	
				// <---- Variables ---->	
	
	//State
	public static enum ElevatorState {
		calibrating, brake, //Auto States
		user 				//Teleop State
	}
	private static ElevatorState currentState = ElevatorState.user;
	
	//References
	private static final TalonSRX drivers = Devices.Elevator.a; /*Talon 31 + 32, 32 is a follower (see more in Devices.java)*/
	
				// <---- /Variables ---->
	
		
	
	
	
				// <---- Sensors ---->
	public static class sensors {
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
				drivers.configReverseSoftLimitThreshold(Constants.Elevator._softStopTop, 10);

				drivers.configForwardSoftLimitEnable(true, 10);
				drivers.configForwardSoftLimitThreshold(Constants.Elevator._softStopBottom, 10);
			}
			
			public static void disable() {
				drivers.configReverseSoftLimitEnable(false, 10);
				drivers.configReverseSoftLimitThreshold(Constants.Elevator._softStopTop, 10);

				drivers.configForwardSoftLimitEnable(false, 10);
				drivers.configForwardSoftLimitThreshold(Constants.Elevator._softStopBottom, 10);
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
				// <---- /Sensors ---->

	
	
	
	
	// <---- Actions ---->
	public static class actions {
		public static void setSpeed(double speed) {
			drivers.set(ControlMode.PercentOutput, speed);
		}
	}
	// <---- /Actions ---->
	
	
	
	
	
				// <---- Management ---->
	protected void init() {
		//Soft Limit Switches
		if (Constants.Elevator._softLimitSwitchesEnabled)
			sensors.softLimitSwitches.enable();
		else
			sensors.softLimitSwitches.disable();
		
		//Hard Limit Switches (HAL Sensors)
		if (Constants.Elevator._hardLimitSwitchesEnabled)
			sensors.hardLimitSwitches.enable();
		else
			sensors.hardLimitSwitches.disable();
	}

	protected void teleopInit() {
		currentState = ElevatorState.user;
	}

	protected static void update() {
		switch (currentState) {
			//Autonomous States
			case calibrating: {
				//actions.setSpeed(-Constants.Elevator._calibrateSpeed);
				//if (sensors.hardLimitSwitches.hitLower()) {
				//	currentState = ElevatorState.brake;
				//}
			}
			case brake: {
				actions.setSpeed(0);
			}
			
			//Teleop State
			case user: {
				double s = controller.getAxis(HMI.Elevator._drive);
				s = Deadband.get(s, 0.1);
				
				//Moving Down
				if (s < 0 && !sensors.hardLimitSwitches.hitLower())
					actions.setSpeed(Constants.Elevator._downScalar);
				
				//Moving Up
				else if (s > 0 && !sensors.hardLimitSwitches.hitUpper())
					actions.setSpeed(Constants.Elevator._upScalar);
				
				// Not Moving
				else
					actions.setSpeed(s);
			}
		}
	}
	
	protected void teleopUpdate() {
		//State Machine handles teleop control while in the "user" state
		update();
	}

	protected void autoInit() {
		currentState = /*ElevatorState.calibrating*/ElevatorState.brake;
	}

	protected void autoUpdate() {
		//Autonomous is either in calibrating or brake
		update();
	}

	protected void idleUpdate() {
		
	}

	protected void initNetworkPosting() {
		
	}

	protected void postToNetwork() {
		
	}
				// <---- /Management ---->
}
