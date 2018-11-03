package frc.team5104.subsystem.squeezy;

import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.squeezy.SqueezyManager.SqueezyEjectSpeed;
import frc.team5104.subsystem.squeezy.SqueezyManager.SqueezyState;
import frc.team5104.subsystem.squeezy.SqueezySystems.fold;
import frc.team5104.teleop.HMI;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

public class SqueezyActions extends BreakerSubsystem.Actions {
	public static void foldSet(boolean up) {
		console.log(c.SQUEEZY, "Folding " + (up ? "Up" : "Down"));
		if (up) {
			fold.up();
			
			//Folding Up => Hold State
			hold();
		}
		else {
			fold.down();
		}
	}
	
	public static void eject() {
		console.log(c.SQUEEZY, "Ejecting at High speed");
		SqueezyManager.vWheelEjectSpeed = SqueezyEjectSpeed.High.wheelSpeed;
		SqueezyManager.vArmsEjectSpeed = SqueezyEjectSpeed.High.armsSpeed;
		
		if (fold.isUp()) {
			fold.down();
			SqueezyManager.vEjectTime = System.currentTimeMillis() + _SqueezyConstants._foldUpEjectDelay;
			SqueezyManager.setStateDelayed(SqueezyState.eject, _SqueezyConstants._foldUpEjectDelay);
		}
		else {
			SqueezyManager.vEjectTime = System.currentTimeMillis();
			SqueezyManager.setState(SqueezyState.eject);
		}
	}
	
	public static void eject(SqueezyEjectSpeed speed) {
		console.log(c.SQUEEZY, "Ejecting at " + speed.name() + " speed");
		SqueezyManager.vWheelEjectSpeed = speed.wheelSpeed;
		SqueezyManager.vArmsEjectSpeed = speed.armsSpeed;
		
		if (fold.isUp()) {
			fold.down();
			SqueezyManager.vEjectTime = System.currentTimeMillis() + _SqueezyConstants._foldUpEjectDelay;
			SqueezyManager.setStateDelayed(SqueezyState.eject, _SqueezyConstants._foldUpEjectDelay);
		}
		else {
			SqueezyManager.vEjectTime = System.currentTimeMillis();
			SqueezyManager.setState(SqueezyState.eject);
		}
	}
	
	public static void intake() {
		console.log(c.SQUEEZY, "Intaking");
		SqueezyManager.setState(SqueezyState.intake);
	}
	
	public static void hold() {
		console.log(c.SQUEEZY, "Holding");
		//SqueezyManager.vHasCube = false;
		SqueezyManager.setState(SqueezyState.hold);
	}
	
	public static void idle() {
		console.log(c.SQUEEZY, "Idling");
		SqueezyManager.setState(SqueezyState.idle);
	}
	
}
