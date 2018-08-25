package com.frc5104.main.subsystems;

import com.frc5104.main.Devices;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.HMI;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*Breakerbots Robotics Team 2018*/
public class Shifters {

	static Shifters instance = null;
	
	public static Shifters getInstance() {
		if (instance == null) {
			instance = new Shifters();
		}
		return instance;
	}//getInstance
	
	DoubleSolenoid gearShifters = Devices.Shifting.sol;

	public boolean inHighGear() {
		return gearShifters.get() == DoubleSolenoid.Value.kForward;
	}//inHighGear
	
	public boolean inLowGear() {
		return !inHighGear();
	}//inLowGear
	
	public void shiftHigh() {
		gearShifters.set(DoubleSolenoid.Value.kForward);
	}//shiftHigh
	
	public void shiftLow() {
		gearShifters.set(DoubleSolenoid.Value.kReverse);
	}//setLow
	
	public void toggle() {
		if (inHighGear())
			shiftLow();
		else
			shiftHigh();
	}//toggle
		
	public void shiftHigh(boolean high) {
		if (high)
			shiftHigh();
		else
			shiftLow();
	}//shiftHigh
	
	public void teleUpdate() {
		if (ControllerHandler.getInstance().getAxis(HMI.kDriveShift) > 0.6)
			shiftHigh();
		else
			shiftLow();
	}
}//Shifters
