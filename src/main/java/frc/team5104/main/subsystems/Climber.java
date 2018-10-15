package frc.team5104.main.subsystems;

/*Breakerbots Robotics Team 2018*/
public class Climber extends BreakerSubsystem {
	
	// -- Subsystem Functions
	protected Climber() {
		
	}
	
	protected void init() {
		//hookHolder.setPosition(0.2);
	}

	protected void teleopUpdate() {
//		if (controller.getHeldEvent(HMI.kPtoHoldAndHookPressButton, 0.4)) { 
//			console.log(c.TELEOP, "Switching PTO");
//			Devices.Climbing.ptoSol.set(!ptoSol.get());
//			if (ptoSol.get())
//				controller.rumbleSoftFor(0.5, 0.2);
//			else
//				controller.rumbleHardFor(1, 0.2);
//		}
//		if (!ptoSol.get()) {
//			controller.rumbleHardFor(0.7, 0.2);
//		}
//		if (controller.getPressed(HMI.kOpenHookHolder)) {
//			Devices.Climbing.hookHolder.setPosition(1 - Devices.Climbing.hookHolder.getPosition());
//		}
	}

	protected void autoUpdate() {
		
	}

	protected void idleUpdate() {
		
	}

	protected void initNetworkPosting() {
		
	}
	
	protected void postToNetwork() {
		
	}

	protected void teleopInit() {
		
	}

	protected void autoInit() {
		
	}

	public static void debug() {
		
	}

	protected void robotDisabled() {
		
	}
}
