package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Devices;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.ControllerHandler.Control;

/*Breakerbots Robotics Team 2018*/
public class Drive {

	static Drive m_instance = null;
	
	public static Drive getInstance() {
		if (m_instance == null) {
			m_instance = new Drive();
		}
		return m_instance;
	}

	TalonSRX L1 = Devices.Drive.L1;
	TalonSRX L2 = Devices.Drive.L2;
	TalonSRX R1 = Devices.Drive.R1;
	TalonSRX R2 = Devices.Drive.R2;
	
	public void init() {
		// Setup
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
	
	
	//Driving
	public void update() {
		ControllerHandler controller = ControllerHandler.getInstance();
		L1.set(ControlMode.PercentOutput, controller.getAxis(Control.LY) - controller.getAxis(Control.LX));
		R1.set(ControlMode.PercentOutput, controller.getAxis(Control.LY) + controller.getAxis(Control.LX));
	}
	
	public void set(double l, double r) {
		L1.set(ControlMode.PercentOutput, l);
		R1.set(ControlMode.PercentOutput, r);
	}
	
	
	//Encoders
	public void resetEncoders(int timeoutMs) {
		L1.setSelectedSensorPosition(0, 0, timeoutMs);
		R1.setSelectedSensorPosition(0, 0, timeoutMs);
	}
	
	public int getLeftEncoder() {
		return L1.getSelectedSensorPosition(0);
	}
	
	public int getRightEncoder() {
		return R1.getSelectedSensorPosition(0);
	}
}
