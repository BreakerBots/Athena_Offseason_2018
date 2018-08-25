package com.frc5104.main.subsystems;

import com.frc5104.main.Devices;
import com.frc5104.utilities.ControllerHandler;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import com.frc5104.utilities.HMI;

/*Breakerbots Robotics Team 2018*/
public class Climber {

	static Climber m_instance = null;
	
	public static Climber getInstance() {
		if (m_instance == null) {
			m_instance = new Climber();
		}
		return m_instance;
	}

	Solenoid ptoSol = Devices.Climbing.ptoSol;
	Servo hookHolder = Devices.Climbing.hookHolder;
	ControllerHandler controller = ControllerHandler.getInstance();
	
	public void init() {
		hookHolder.setPosition(0.2);
	}
	
	
	//Driving
	public void update() {
		if (controller.getHeldEvent(HMI.kPtoHoldAndHookPressButton, 0.4)) { 
			System.out.println("TELE: Switching PTO!");
			Devices.Climbing.ptoSol.set(!ptoSol.get());
			if (ptoSol.get())
				controller.rumbleSoftFor(0.5, 0.2);
			else
				controller.rumbleHardFor(1, 0.2);
		}
		if (!ptoSol.get()) {
			controller.rumbleHardFor(0.7, 0.2);
		}
		if (controller.getPressed(HMI.kOpenHookHolder)) {
			Devices.Climbing.hookHolder.setPosition(1 - Devices.Climbing.hookHolder.getPosition());
		}
	}
}
