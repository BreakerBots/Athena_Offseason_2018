package frc.team5104.subsystem.drive;

/*Breakerbots Robotics Team 2018*/
/**
 * A simple wrapper class for sending/saving signals to send to Drive.java (The Drive Train)
 */
public class RobotDriveSignal {
	/**
	 * Unit that the left & right speeds can be in.
	 * feetPerSecond: uses PID directly on the talons to achieve the specified velocity
	 * percentOutput: directly set the percent output on the talons [from -1 (full speed reverse) to 1 (full speed forward)]
	 */
	public static enum DriveUnit {
		feetPerSecond,
		percentOutput
	}

	// Robot Drive Signal Variables
	public double leftSpeed;
	public double rightSpeed;
	public DriveUnit unit;
	
	/**
	 * Creates a Robot Drive Signal with the specified speeds in percentOutput
	 * @param leftSpeed  Percent output (-1 to 1) for the left  motors of the drive train to run
	 * @param rightSpeed Percent output (-1 to 1) for the right motors of the drive train to run
	 */
	public RobotDriveSignal(double leftSpeed, double rightSpeed) {
		this(leftSpeed, rightSpeed, DriveUnit.percentOutput);
	}
	
	/**
	 * Creates a Robot Drive Signal with the specified speed in specified unit
	 * @param leftSpeed  Speed for the left  motors of the drive train to run
	 * @param rightSpeed Speed for the right motors of the drive train to run
	 * @param unit The unit for the left & right motor speeds to be in (percentOutput or feetPerSecond)
	 */
	public RobotDriveSignal(double leftSpeed, double rightSpeed, DriveUnit unit) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
		this.unit = unit;
	}
	
	public String toString() {
		return  "l: " + leftSpeed + ", " +
				"r: " + rightSpeed;
	}
}
