package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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

	TalonSRX talonL1 = new TalonSRX(11);
	TalonSRX talonL2 = new TalonSRX(12);
	TalonSRX talonR1 = new TalonSRX(13);
	TalonSRX talonR2 = new TalonSRX(14);
	
	public void init() {
		//Talon Setup
		talonL1.setSelectedSensorPosition(0, 0, 10);
		talonR1.setSelectedSensorPosition(0, 0, 10);
		talonL2.set(ControlMode.Follower, talonL1.getDeviceID());
		talonR2.set(ControlMode.Follower, talonR1.getDeviceID());
		talonL1.set(ControlMode.PercentOutput, 0);
		talonL1.setInverted(false);
		talonL2.setInverted(false);
		talonR1.set(ControlMode.PercentOutput, 0);
		talonR1.setInverted(true);
		talonR2.setInverted(true);
	}
	
	
	//Driving
	public void update() {
		ControllerHandler controller = ControllerHandler.getInstance();
		talonL1.set(ControlMode.PercentOutput, controller.getAxis(Control.LY) - controller.getAxis(Control.LX));
		talonR1.set(ControlMode.PercentOutput, controller.getAxis(Control.LY) + controller.getAxis(Control.LX));
	}
	
	public void set(double l, double r) {
		talonL1.set(ControlMode.PercentOutput, l);
		talonR1.set(ControlMode.PercentOutput, r);
	}
	
	
	//Encoders
	public void resetEncoders(int timeoutMs) {
		talonL1.setSelectedSensorPosition(0, 0, timeoutMs);
		talonR1.setSelectedSensorPosition(0, 0, timeoutMs);
	}
	
	public int getLeftEncoder() {
		return talonL1.getSelectedSensorPosition(0);
	}
	
	public int getRightEncoder() {
		return talonR1.getSelectedSensorPosition(0);
	}
}
