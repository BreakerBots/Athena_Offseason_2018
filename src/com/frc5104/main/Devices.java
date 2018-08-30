package com.frc5104.main;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.utilities.TalonFactory;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;

/*Breakerbots Robotics Team 2018*/
public class Devices {

	//Drive
	public static class Drive {
		public static TalonSRX L1 = TalonFactory.getTalon(11);
		public static TalonSRX L2 = TalonFactory.getTalon(12);
		public static TalonSRX R1 = TalonFactory.getTalon(13);
		public static TalonSRX R2 = TalonFactory.getTalon(14);
		public static ADXRS450_Gyro Gyro = new ADXRS450_Gyro();
	}
	
	//Squeezy
	public static class Squeezy {
		public static TalonSRX squeeze = TalonFactory.getTalon(21);
		public static TalonSRX leftSpin = TalonFactory.getTalon(22);
		public static TalonSRX rightSpin = TalonFactory.getTalon(23);
		public static DoubleSolenoid fold = new DoubleSolenoid(0, 1);
	}
	
	//Elevator
	public static class Elevator {
		public static TalonSRX a = TalonFactory.getTalon(31);
		public static TalonSRX b = TalonFactory.getTalonFollower(32, 31);
	}
	
	//Climbing
	public static class Climbing {
		public static Solenoid ptoSol = new Solenoid(4);
		public static Servo hookHolder = new Servo(0);
	}
	
	//Shifting
	public static class Shifting {
		public static DoubleSolenoid sol = new DoubleSolenoid(2, 3);
	}
}
