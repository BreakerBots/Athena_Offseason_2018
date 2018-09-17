package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Devices;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.ControllerHandler.Control;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/*Breakerbots Robotics Team 2018*/
/**
 * Squeezy Sanchez (A Decent but also sucky intake mechanism, with new and improved code!!!)
 */
public class SqueezyV2 extends BreakerSubsystem {
	private static SqueezyV2 _inst = null; 
	public static SqueezyV2 getInstance() { if (_inst == null) _inst = new SqueezyV2(); return _inst; }
	
	/*
	 * TODO:
	 * 	 - Arms Detect Physically Stopped
	 * 	 - Test
	 *   - If Adopted, move constants and controls
	 */
	
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
	private static enum SqueezyState {
				//  Fold  |  Arms  |  Wheels  
				// -------|--------|---------
		intake,	//  down  |  out   |  in (intake speed)
		eject,	//  down  |  i-hld |  out (eject speed)
		hold,	//  ethr  |  hold  |  in (hold speed)
		idle	//  ethr  |  idle  |  idle 
	}
	private static SqueezyState currentState = /*SqueezyMainState.hold*/SqueezyState.idle;
	
	//Constants (_ + DEVICE + CONSTANT)
	private static double _armsInSpeed       = 0.6;
	
	private static double _wheelEjectSpeed   = 0.8;
	private static double _armsOutEjectSpeed = 0.6;
	
	private static double _armsOutSpeed      = 0.6;
	private static double _wheelIntakeSpeed  = 0.8;
	
	private static double _armsHoldSpeed     = 0.1;
	private static double _wheelHoldSpeed    = 0.1;
	
	//Devices (d + DEVICE)
	private static TalonSRX dArms       = Devices.Squeezy.squeeze;
	private static TalonSRX dLWheel     = Devices.Squeezy.leftSpin;
	private static TalonSRX dRWheel     = Devices.Squeezy.rightSpin;
	private static DoubleSolenoid dFold = Devices.Squeezy.fold; 
	
	//Controls (c + CONTROL)
	private static Control cFoldUp   = Control.A;
	private static Control cFoldDown = Control.Y;
	
	private static Control cEject    = Control.LB;
	
	private static Control cIntake   = Control.B;
	private static Control cHold     = Control.X;
	
	private static Control cIdle     = Control.LIST;
	
				// <---- /Varibales ---->
	
	
	
	
	
				// <---- Systems (Handle Devices Directly for the State Machine) ---->
	
	// - Wheels
	private static class wheels {
		private static void intake() {
			dLWheel.set(ControlMode.PercentOutput, -_wheelIntakeSpeed);
			dRWheel.set(ControlMode.PercentOutput,  _wheelIntakeSpeed);
		}
		
		private static void eject() {
			dLWheel.set(ControlMode.PercentOutput, -_wheelEjectSpeed);
			dRWheel.set(ControlMode.PercentOutput,  _wheelEjectSpeed);
		}
		
		private static void idle() {
			dLWheel.set(ControlMode.PercentOutput, 0);
			dRWheel.set(ControlMode.PercentOutput, 0);
		}
		
		private static void hold() {
			dLWheel.set(ControlMode.PercentOutput, -_wheelHoldSpeed);
			dRWheel.set(ControlMode.PercentOutput,  _wheelHoldSpeed);
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
		
		public static int getVelocity() {
			return dArms.getSelectedSensorVelocity(0);
		}
		
		public static int getPosition() {
			return dArms.getSelectedSensorPosition(0);
		}
		
		public static void resetPosition() {
			dArms.setSelectedSensorPosition(0, 0, 10);
		}

		public static boolean isPhysicallyStopped() {
			//TODO
			return false;
		}
	}
	
				// <---- /Systems ---->
	
	
	
	
	
				// <---- Actions (Control Subsystems and State Machine) ---->
	
	public static class actions {
		public static void foldSet(boolean up) {
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
			setState(SqueezyState.eject);
		}
		
		public static void intake() {
			setState(SqueezyState.intake);
		}
		
		public static void hold() {
			setState(SqueezyState.hold);
		}
		
		public static void idle() {
			setState(SqueezyState.idle);
		}
	}
	
				// <---- /Actions ---->
	
	
	
	
	
				// <---- Management (Calls Actions and Handles State Machine) ---->
	
	protected void init() {
		
	}
	
	private static void setState(SqueezyState state) {
		currentState = state;
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
					arms.set(_armsOutSpeed);
				}
				
				//Wheels
				wheels.intake();
			}
			case eject: {
				//  Fold: down, 
				//  Arms: idle-hold (moving out slightly), 
				//  Wheels: out (eject speed)
				
				// Fold
				fold.down();
				
				//Arms
				if (!arms.hitOutsideLimitSwitch()) {
					arms.set(_armsOutEjectSpeed);
				}
				
				//Wheels
				wheels.eject();
			}
			case hold: {
				//  Fold: either, 
				//  Arms: hold, 
				//  Wheels: in (hold speed)
				// *Hold includes moving the arms in to a position to hold the cube
				
				//Switch to idle since there was no cube
				if (arms.hitInsideLimitSwitch())
					setState(SqueezyState.idle);
				
				boolean shouldHoldCube = arms.isPhysicallyStopped();
				if (shouldHoldCube) {
					//Arms
					arms.set(_armsHoldSpeed);
					
					//Wheels
					wheels.hold();
				}
				else {
					//Arms
					arms.set(_armsInSpeed);
					
					//Wheels
					wheels.idle();
				}
			}
			case idle: {
				//  Fold: either, 
				//  Arms: idle, 
				//  Wheels: idle
				
				//Arms
				arms.set(0);
				
				//Wheels
				wheels.idle();
			}
		}
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
		
		ControllerHandler controller = ControllerHandler.getInstance();
		
		//Fold Up
		if (controller.getPressed(cFoldUp))
			actions.foldSet(true);
		
		//Fold Down
		if (controller.getPressed(cFoldDown))
			actions.foldSet(false);
		
		//Intake
		if (controller.getPressed(cIntake))
			actions.intake();
		
		//Eject
		if (controller.getPressed(cEject))
			actions.eject();
		
		//Hold
		if (controller.getPressed(cHold))
			actions.hold();
		
		//Idle
		if (controller.getPressed(cIdle))
			actions.idle();
		
		update();
	}
	
	protected void autoInit() {
		setState(SqueezyState.hold);
	}
	
	protected void autoUpdate() {
		//Controlled in the Breaker Command System
		
		update();
	}
	
	protected void idleUpdate() {
		
	}
	
	protected void initNetworkPosting() {
		
	}
	
	protected void postToNetwork() {
		
	}
	
				// <---- /Management ---->
}