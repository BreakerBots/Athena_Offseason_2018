package frc.team5104.subsystem.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.main.Devices;
import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;

public class ElevatorManager extends BreakerSubsystem.Manager {
	public static enum ElevatorState {
		calibrating, brake, controlled
	}

	private static ElevatorState currentState = ElevatorState.controlled;
	static double speed = 0;

	public void enabled(RobotMode mode) {
		if (mode == RobotMode.Teleop) {
			currentState = ElevatorState.controlled;
		}
		if (mode == RobotMode.Auto) {
			currentState = /* ElevatorState.calibrating */ElevatorState.brake;
		}
	}

	public void update() {
		if (ElevatorSystems.solenoid.inElevator()) {
			switch (currentState) {
			// Autonomous States
			case calibrating: 
				// ElevatorActions.setSpeed(-ElevatorConstants._calibrateSpeed);
				// if (sensors.hardLimitSwitches.hitLower()) {
				// currentState = ElevatorState.brake;
				// }
				break;
			
			case brake:
				ElevatorActions.setSpeed(0);
				break;

			// Teleop State
			case controlled: 
				// Moving Down
				if (speed > 0 && !ElevatorSystems.hardLimitSwitches.hitLower())
					Devices.Elevator.a.set(ControlMode.PercentOutput, speed * _ElevatorConstants._downScalar);

				// Moving Up
				else if (speed < 0 && !ElevatorSystems.hardLimitSwitches.hitUpper())
					Devices.Elevator.a.set(ControlMode.PercentOutput, speed * _ElevatorConstants._upScalar);

				// Not Moving
				else
					Devices.Elevator.a.set(ControlMode.PercentOutput, 0);
				break;
			}
		}
	}

	public void disabled() {

	}

	public ElevatorManager() {
		// Soft Limit Switches
		if (_ElevatorConstants._softLimitSwitchesEnabled)
			ElevatorSystems.softLimitSwitches.enable();
		else
			ElevatorSystems.softLimitSwitches.disable();

		// Hard Limit Switches (HAL Sensors)
		if (_ElevatorConstants._hardLimitSwitchesEnabled)
			ElevatorSystems.hardLimitSwitches.enable();
		else
			ElevatorSystems.hardLimitSwitches.disable();
	}
}
