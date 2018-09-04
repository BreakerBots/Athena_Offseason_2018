package com.frc5104.main.subsystems;

import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;

/*Breakerbots Robotics Team 2018*/
public class Climber implements BreakerSubsystem {
	private static Climber _inst = null; 
	public static Climber getInstance() { if (_inst == null) _inst = new Climber(); return _inst; }
	
	//References
	private Solenoid ptoSol = Devices.Climbing.ptoSol;
	private Servo hookHolder = Devices.Climbing.hookHolder;
	private ControllerHandler controller = ControllerHandler.getInstance();
	
	//Init
	public void init() {
		hookHolder.setPosition(0.2);
	}

	//Teleop
	public void teleopUpdate() {
		if (controller.getHeldEvent(HMI.kPtoHoldAndHookPressButton, 0.4)) { 
			console.log("Switching PTO", Type.TELEOP);
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

	public void autoUpdate() {
		
	}

	public void idleUpdate() {
		
	}

	public void initNetworkPosting() {
		
	}
	
	public void postToNetwork() {
		
	}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoInit() {
		// TODO Auto-generated method stub
		
	}
}
