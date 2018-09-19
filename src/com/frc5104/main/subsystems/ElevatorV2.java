package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.main.subsystems.Drive.shifters.Gear;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.Deadband;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/*Breakerbots Robotics Team 2018*/
public class ElevatorV2 extends BreakerSubsystem {
	private static ElevatorV2 _inst = null; 
	public static ElevatorV2 getInstance() { if (_inst == null) _inst = new ElevatorV2(); return _inst; }
	
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
	
	//Constants References
	private static final double _driveSpeed      = 0.6; //Teleop Drive Speed
	private static final double _calibrateSpeed  = 0.1; //Calbrating Drive Speed
	private static final int    _softStopBottom  = Constants.Elevator.SOFT_STOP_BOTTOM; //Soft limit switch value for bottom
	private static final int    _softStopTop     = Constants.Elevator.SOFT_STOP_TOP; //Soft limit switch value for top
	private static final boolean _hardLimitSwitchesEnabled = true; //Should use hard limit switches
	private static final boolean _softLimitSwitchesEnabled = false; //Should use soft limit switches (encoder values)
	
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
				drivers.configReverseSoftLimitThreshold(_softStopTop, 10);

				drivers.configForwardSoftLimitEnable(true, 10);
				drivers.configForwardSoftLimitThreshold(_softStopBottom, 10);
			}
			
			public static void disable() {
				drivers.configReverseSoftLimitEnable(false, 10);
				drivers.configReverseSoftLimitThreshold(_softStopTop, 10);

				drivers.configForwardSoftLimitEnable(false, 10);
				drivers.configForwardSoftLimitThreshold(_softStopBottom, 10);
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
		if (_softLimitSwitchesEnabled)
			sensors.softLimitSwitches.enable();
		else
			sensors.softLimitSwitches.disable();
		
		//Hard Limit Switches (HAL Sensors)
		if (_hardLimitSwitchesEnabled)
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
				actions.setSpeed(-_calibrateSpeed);
				if (sensors.hardLimitSwitches.hitLower()) {
					currentState = ElevatorState.brake;
				}
			}
			case brake: {
				actions.setSpeed(0);
			}
			
			//Teleop State
			case user: {
				double s = ControllerHandler.getInstance().getAxis(HMI.kElevatorUpDown);
				s = Deadband.get(s * _driveSpeed, 0.1);
				
				//Moving Down
				if (s < 0 && !sensors.hardLimitSwitches.hitLower())
					actions.setSpeed(s);
				
				//Moving Up
				else if (s > 0 && !sensors.hardLimitSwitches.hitUpper())
					actions.setSpeed(s);
				
				// Not Moving
				else if (s == 0)
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
