package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.math.BreakerMath;
import com.frc5104.utilities.BooleanChangeListener;
import com.frc5104.utilities.console;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.console.c;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*Breakerbots Robotics Team 2018*/
/**
 * Squeezy Sanchez (A Decent but also sucky intake mechanism, with new and improved code!!!)
 */
public class Squeezy extends BreakerSubsystem {
	/*
	 * Squeezy Vocab:
	 * 	 - Word: Description/Definition
	 * 		- Subword [MODIFIER] (ABBRIVIATION): Description/Definition <Relative Reference>
	 * 
	 * 	 - Fold: Squeezy Moving/Folding/Lifting Up and Down
	 * 		- Up: Squeezy is folded up closer to the elevator (facing up)
	 * 		- Down: Squeezy is folded down closer to the group (facing forward)
	 * 		- Either (ethr): Squeezy is either folder <Up> or <Down> 
	 * 
	 * 	 - Wheels: The Green Wheels on Squeezy
	 * 		- In [Speed]: The Wheels are spinning in an intake motion at <Speed> (in toward the robot)
	 * 		- Out [Speed]: The Wheels are spinning in an eject motion at <Speed> (out from the robot)
	 * 		- Idle: The Wheels are not being spun by the motor
	 * 
	 * 	 - Arms: Squeezy Arms and the Motor/Belt moving them
	 * 		- Out: The Arms are the point farthest from eachother (fully open)
	 * 		- In: The Arms are at the point closest to eachother (full in)
	 * 		- Holding (hld or hold): The Arms are at or moving toward ~13 inches from eachother and pushing against the cube (enought to hold the cube)
	 * 		- Idle (idl or idle): The Arms can be in any position and the motor is idle
	 * 		- Idle Hold (i-hld or ihld): The Arms are ~13 inches from eachother (touching the cube), but not forcing/hold the cube
	 * 		- Idle Out (i-out or iout): The arms are <Out> and <Idle>
	 * 		- Idle In (i-in or iin): The arms are <In> and <Idle>
	 * 		- Moving Out [Speed] (m-out): The arms are moving toward the <Out> position at <Speed>
	 * 		- Moving In [Speed] (m-in): The arms are moving toward the <In> position at <Speed>
	 * 
	 * 	- Cube: Refering the Power-Up 2018 game object, the Power Cube (a milk crate with a yellow cover, 13"w x 13"l x 11"h)
	 * 		- This mecanism is capable of picking up cubes at 13" and 11"
	 */
	
	
	
	
	
	// <---- Variables ---->
	//State
	public static enum SqueezyState {
				//  Fold  |  Arms  |  Wheels  
				// -------|--------|---------
		intake,	//  down  |  out   |  in (intake speed)
		eject,	//  down  |  i-hld |  out (eject speed)
		hold,	//  ethr  |  hold  |  in (hold speed)
		idle	//  ethr  |  idle  |  idle 
	}
	private static SqueezyState currentState = /*SqueezyMainState.hold*/SqueezyState.idle;
	
	//Devices (d + DEVICE)
	private static TalonSRX dArms       = Devices.Squeezy.squeeze;
	private static TalonSRX dLWheel     = Devices.Squeezy.leftSpin;
	private static TalonSRX dRWheel     = Devices.Squeezy.rightSpin;
	private static DoubleSolenoid dFold = Devices.Squeezy.fold; 
	
	
	//Variables (v + VAR)
	private static boolean vHasCube = false;
	private static double vWheelEjectSpeed = SqueezyEjectSpeed.High.wheelSpeed;
	private static double vArmsEjectSpeed = SqueezyEjectSpeed.High.armsSpeed;
	private static double vEjectTime;
	private static BooleanChangeListener vBCubeEjected = new BooleanChangeListener(true);
	private static BooleanChangeListener vBHitOut = new BooleanChangeListener(true);
	private static BooleanChangeListener vBHasCube = new BooleanChangeListener(true);
	
	//Eject Speeds
	public static enum SqueezyEjectSpeed {
		Low(Constants.Squeezy._wheelEjectSpeedLow, Constants.Squeezy._armsEjectSpeedLow),
		Med(Constants.Squeezy._wheelEjectSpeedMed, Constants.Squeezy._armsEjectSpeedMed),
		High(Constants.Squeezy._wheelEjectSpeedHigh, Constants.Squeezy._armsEjectSpeedHigh);
		double wheelSpeed; double armsSpeed; SqueezyEjectSpeed (double wheelSpeed, double armsSpeed) { this.wheelSpeed = wheelSpeed; this.armsSpeed = armsSpeed; } 
	}
	// <---- /Varibales ---->
	
	
	
	
	
	// <---- Systems (Handle Devices Directly for the State Machine) ---->
	// - Wheels
	private static class wheels {
		private static void intake() {
			dLWheel.set(ControlMode.PercentOutput, -Constants.Squeezy._wheelIntakeSpeed);
			dRWheel.set(ControlMode.PercentOutput,  Constants.Squeezy._wheelIntakeSpeed);
		}
		
		private static void eject() {
			dLWheel.set(ControlMode.PercentOutput, -vWheelEjectSpeed);
			dRWheel.set(ControlMode.PercentOutput,  vWheelEjectSpeed);
		}
		
		private static void idle() {
			dLWheel.set(ControlMode.PercentOutput, 0);
			dRWheel.set(ControlMode.PercentOutput, 0);
		}
		
		private static void hold() {
			dLWheel.set(ControlMode.PercentOutput, -Constants.Squeezy._wheelHoldSpeed);
			dRWheel.set(ControlMode.PercentOutput,  Constants.Squeezy._wheelHoldSpeed);
		}
	}
	
	// - Fold
	private static class fold {
		private static void up() {
			dFold.set(DoubleSolenoid.Value.kReverse);
		}
		
		private static void down() {
			dFold.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	// - Arms
	private static class arms {	
		//Hold, Idle and Out are handled in the state machine
		
		public static void set(double speed) {
			dArms.set(ControlMode.PercentOutput, speed);
		}
		
		public static boolean hitInsideLimitSwitch() {
			return dArms.getSensorCollection().isRevLimitSwitchClosed();
		}
		
		public static boolean hitOutsideLimitSwitch() {
			return dArms.getSensorCollection().isFwdLimitSwitchClosed();
		}
		
		//public static int getVelocity() {
		//	return dArms.getSelectedSensorVelocity(0);
		//}
		
		//public static int getPosition() {
		//	return dArms.getSelectedSensorPosition(0);
		//}
		
		public static void resetPosition() {
			dArms.setSelectedSensorPosition(0, 0, 10);
		}

		public static double getCurrent() {
			return dArms.getOutputCurrent();
		}
		
		public static boolean isPhysicallyStopped() {
			return getCurrent() > Constants.Squeezy._armsPhysicallyStoppedCurrent;
		}
	}
	// <---- /Systems ---->
	
	
	
	
	
	// <---- Actions (Control Subsystems and State Machine) ---->
	public static class actions {
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
			vEjectTime = System.currentTimeMillis();
			vWheelEjectSpeed = SqueezyEjectSpeed.High.wheelSpeed;
			vArmsEjectSpeed = SqueezyEjectSpeed.High.armsSpeed;
			setState(SqueezyState.eject);
		}
		
		public static void eject(SqueezyEjectSpeed speed) {
			console.log(c.SQUEEZY, "Ejecting at " + speed.name() + " speed");
			vEjectTime = System.currentTimeMillis();
			vWheelEjectSpeed = speed.wheelSpeed;
			vArmsEjectSpeed = speed.armsSpeed;
			setState(SqueezyState.eject);
		}
		
		public static void intake() {
			console.log(c.SQUEEZY, "Intaking");
			setState(SqueezyState.intake);
		}
		
		public static void hold() {
			console.log(c.SQUEEZY, "Holding");
			vHasCube = false;
			setState(SqueezyState.hold);
		}
		
		public static void idle() {
			console.log(c.SQUEEZY, "Idling");
			setState(SqueezyState.idle);
		}
	}
	
	private static void setState(SqueezyState state) {
		currentState = state;
	}
	
	public static SqueezyState getState() {
		return currentState;
	}
	// <---- /Actions ---->
	
	
	
	
	
	// <---- Management (Calls Actions and Handles State Machine) ---->
	protected Squeezy() {
		
	}
	
	protected void update() {
		switch (currentState) {
			case intake: {
				//  Fold: down, 
				//	Arms: out, 
				//  Wheels: in (intake speed)
				
				// Fold
				fold.down();
				
				//Arms
				if (!arms.hitOutsideLimitSwitch()) {
					arms.set(Constants.Squeezy._armsOutSpeed);
				}
				else {
					arms.set(0);
					arms.resetPosition();
				}
				
				//Wheels
				wheels.intake();
				
				break;
			}
			case eject: {
				//  Fold: down, 
				//  Arms: idle-hold (moving out slightly), 
				//  Wheels: out (eject speed)
				
				// Fold
				fold.down();
				
				boolean time = ((double)(System.currentTimeMillis()) - vEjectTime) < Constants.Squeezy._ejectTime;
				if (!arms.hitOutsideLimitSwitch() && time) {
					//Arms
					arms.set(vArmsEjectSpeed);
					
					//Wheels
					wheels.eject();
				} 
				else 
					setState(SqueezyState.idle);
				
				break;
			}
			case hold: {
				//  Fold: either, 
				//  Arms: hold, 
				//  Wheels: in (hold speed)
				// *Hold includes moving the arms in to a position to hold the cube
				
				//Switch to idle since there was no cube
				if (arms.hitInsideLimitSwitch())
					setState(SqueezyState.idle);
				
				//shouldHoldCube = false;
				if (vHasCube) {
					//Arms
					arms.set(Constants.Squeezy._armsHoldSpeed);
					
					//Wheels
					wheels.hold();
				}
				else {
					//Arms
					arms.set(Constants.Squeezy._armsInSpeed);
					
					//Wheels
					wheels.intake();
					
					//Has Cube
					vHasCube = arms.isPhysicallyStopped();
				}
				
				break;
			}
			case idle: {
				//  Fold: either, 
				//  Arms: idle, 
				//  Wheels: idle
				
				//Arms
				arms.set(0);
				
				//Wheels
				wheels.idle();
				
				break;
			}
		} //End of Switch/Case
		
		if (vBCubeEjected.get(currentState == SqueezyState.eject))
			controller.rumbleHardFor(BreakerMath.clamp(Math.abs(vWheelEjectSpeed) * 3, 0, 1), 0.5);
		
		if (vBHasCube.get(arms.isPhysicallyStopped()))
			controller.rumbleHardFor(0.4, 0.2);
		
		if (vBHitOut.get(arms.hitOutsideLimitSwitch()))
			controller.rumbleSoftFor(0.4, 0.2);
	}
	
	protected void teleopInit() {
		setState(SqueezyState.idle);
	}
	
	protected void teleopUpdate() {
		/*
		 * User Actions:
		 * 		Fold Up (Stops Intake and Eject)
		 * 		Fold Down
		 * 		Eject (If holding and folded down)
		 * 		Intake (If folded down)
		 * 		Hold
		 * 		Idle
		 * 
		 * - Picking up cube:
		 * 		1) Fold Down
		 * 		2) Intake
		 * 		3) Drive up to cube
		 * 		4) Hold
		 */
		
		//Fold Up
		if (controller.getPressed(HMI.Squeezy._foldUp))
			actions.foldSet(true);
		
		//Fold Down
		if (controller.getPressed(HMI.Squeezy._foldDown))
			actions.foldSet(false);
		
		// - State Switching
		//Intake
		if (controller.getPressed(HMI.Squeezy._intake))
			actions.intake();
		
		//Eject
		if (controller.getPressed(HMI.Squeezy._eject))
			actions.eject();
		
		//Hold
		if (controller.getPressed(HMI.Squeezy._hold))
			actions.hold();
		
		//Idle
		if (controller.getPressed(HMI.Squeezy._idle))
			actions.idle();
		
		update();
	}
	
	protected void autoInit() {
		setState(SqueezyState.hold);
		actions.foldSet(true);
		//setState(SqueezyState.idle);
	}
	
	protected void autoUpdate() {
		update();
	}
	
	protected void idleUpdate() {
		
	}
	
	protected void initNetworkPosting() {
		
	}
	
	protected void postToNetwork() {
		
	}

	protected void init() {
		
	}

	public static void debug() {
		console.log(
				c.SQUEEZY,
				"State: " + currentState,
				"ArmPhSt: " + arms.isPhysicallyStopped() + "\t",
				"ArmCurr: " + arms.getCurrent() + "\t"
				);
	}
	
	protected void robotDisabled() {
		wheels.idle();
		arms.set(0);
	}
}