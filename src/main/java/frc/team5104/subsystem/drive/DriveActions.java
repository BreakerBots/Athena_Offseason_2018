package frc.team5104.subsystem.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.util.Units;

public class DriveActions extends BreakerSubsystem.Actions {
	/**
	 * Sets the speed of the drive motors to the corresponding speeds specified in the Drive Signal
	 * @param signal
	 */
	public static void set(RobotDriveSignal signal, boolean account) {
		if (account) {
			signal.leftSpeed = signal.leftSpeed * _DriveConstants._leftAccount;
			signal.rightSpeed = signal.rightSpeed * _DriveConstants._rightAccount;
		}
		switch (signal.unit) {
			case percentOutput: {
				DriveSystems.motors.set(
						signal.leftSpeed, 
						signal.rightSpeed, 
						ControlMode.PercentOutput
					);
				break;
			}
			case feetPerSecond: {
				DriveSystems.motors.set(
						Units.feetPerSecondToTalonVel(signal.leftSpeed, _DriveConstants._ticksPerRevolution, _DriveConstants._wheelDiameter), 
						Units.feetPerSecondToTalonVel(signal.rightSpeed, _DriveConstants._ticksPerRevolution, _DriveConstants._wheelDiameter), 
						ControlMode.Velocity
					);
				break;
			}
		}
	}
	
	/**
	 * Stops the drive motors
	 */
	public static void stop() {
		set(new RobotDriveSignal(0, 0, DriveUnit.percentOutput), false);
	}
	
	
}
