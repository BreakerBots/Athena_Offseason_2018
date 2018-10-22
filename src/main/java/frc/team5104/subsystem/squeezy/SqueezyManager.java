package frc.team5104.subsystem.squeezy;

import frc.team5104.main.BreakerRobotController.RobotMode;
import frc.team5104.subsystem.BreakerSubsystem;
import frc.team5104.subsystem.squeezy.SqueezySystems.arms;
import frc.team5104.subsystem.squeezy.SqueezySystems.fold;
import frc.team5104.subsystem.squeezy.SqueezySystems.wheels;
import frc.team5104.util.BooleanChangeListener;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import frc.team5104.util.controller;

public class SqueezyManager extends BreakerSubsystem.Manager {
	public static enum SqueezyState {
				//  Fold  |  Arms  |  Wheels  
				// -------|--------|---------
		intake,	//  down  |  out   |  in (intake speed)
		eject,	//  down  |  i-hld |  out (eject speed)
		hold,	//  ethr  |  hold  |  in (hold speed)
		idle	//  ethr  |  idle  |  idle 
	}
	private static SqueezyState currentState = /*SqueezyMainState.hold*/SqueezyState.idle;
	
	public static enum SqueezyEjectSpeed {
		Low(_SqueezyConstants._wheelEjectSpeedLow, _SqueezyConstants._armsEjectSpeedLow),
		Med(_SqueezyConstants._wheelEjectSpeedMed, _SqueezyConstants._armsEjectSpeedMed),
		High(_SqueezyConstants._wheelEjectSpeedHigh, _SqueezyConstants._armsEjectSpeedHigh);
		double wheelSpeed; double armsSpeed; SqueezyEjectSpeed (double wheelSpeed, double armsSpeed) { this.wheelSpeed = wheelSpeed; this.armsSpeed = armsSpeed; } 
	}

	//private static boolean vHasCube = false;
	static double vWheelEjectSpeed = SqueezyEjectSpeed.High.wheelSpeed;
	static double vArmsEjectSpeed = SqueezyEjectSpeed.High.armsSpeed;
	static double vEjectTime;
	static BooleanChangeListener vBCubeEjected = new BooleanChangeListener(true);
	static BooleanChangeListener vBHitOut = new BooleanChangeListener(true);
	static BooleanChangeListener vBHasCube = new BooleanChangeListener(true);
	
	public void enabled(RobotMode mode) {
		
	}
	
	public void update() {
		switch (currentState) {
			case intake: {
				// Fold
				fold.down();
				
				//Arms
				if (!arms.hitOutsideLimitSwitch()) {
					arms.set(_SqueezyConstants._armsOutSpeed);
				}
				else {
					arms.set(0);
				}
				
				//Wheels
				wheels.intake();
				
				break;
			}
			case eject: {
				// Fold
				fold.down();
				
				boolean time = ((double)(System.currentTimeMillis()) - vEjectTime) < _SqueezyConstants._ejectTime;
				if (!arms.hitOutsideLimitSwitch() && time) {
					//Arms
					arms.set(vArmsEjectSpeed);
					
					//Wheels
					wheels.eject(vWheelEjectSpeed);
				} 
				else 
					setState(SqueezyState.idle);
				
				break;
			}
			case hold: {
				//Switch to idle since there was no cube
				if (arms.hitInsideLimitSwitch())
					setState(SqueezyState.idle);
				
				arms.set(_SqueezyConstants._armsHoldSpeed);
				wheels.hold();
				
//				shouldHoldCube = false;
//				if (vHasCube) {
//					//Arms
//					arms.set(Constants.Squeezy._armsHoldSpeed);
//					
//					//Wheels
//					wheels.hold();
//				}
//				else {
//					//Arms
//					arms.set(Constants.Squeezy._armsInSpeed);
//					
//					//Wheels
//					wheels.intake();
//					
//					//Has Cube
//					vHasCube = arms.isPhysicallyStopped();
//				}
				
				break;
			}
			case idle: {
				//Arms
				arms.set(0);
				
				//Wheels
				wheels.idle();
				
				break;
			}
		} //End of Switch/Case
		
		if (vBCubeEjected.get(currentState == SqueezyState.eject))
			controller.rumbleHardFor(BreakerMath.clamp(Math.abs(vWheelEjectSpeed) * 3, 0, 1), 0.5);
		
		if (vBHasCube.get(arms.isPhysicallyStopped())) { 
			console.log(c.SQUEEZY, "Physically Stopped");
			controller.rumbleHardFor(0.4, 0.2);
		}
		
		if (vBHitOut.get(arms.hitOutsideLimitSwitch()))
			controller.rumbleSoftFor(0.4, 0.2);
	}
	
	public void disabled() {
		
	}
	
	static void setState(SqueezyState state) {
		currentState = state;
	}
	
	public static SqueezyState getState() {
		return currentState;
	}
}
