package com.frc5104.math;

/*Breakerbots Robotics Team 2018*/
public class RobotDriveSignal {
	public double leftSpeed;
	public double rightSpeed;
	
	public RobotDriveSignal(double leftSpeed, double rightSpeed) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}
	
	public void change(double leftSpeed, double rightSpeed) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}
	
	public String toString() {
		return  "l: " + leftSpeed + ", " +
				"r: " + rightSpeed;
	}
}
