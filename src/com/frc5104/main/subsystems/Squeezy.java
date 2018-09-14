package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.FilteredUltraSonic;
import com.frc5104.utilities.TimedButton;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/*Breakerbots Robotics Team 2018*/
/**
 * Squeezy Sanchez (A Decent but also sucky intake mechanism)
 */
public class Squeezy implements BreakerSubsystem {
	private static Squeezy _inst = null; 
	public static Squeezy getInstance() { if (_inst == null) _inst = new Squeezy(); return _inst; }
	
	//Constants References
	static final int kHasCubePosition = Constants.Squeezy.HasCubePosition;
	static final double kHoldEffort = Constants.Squeezy.HoldEffort;
	static final double kShootSqueezeEffort = Constants.Squeezy.ShootSqueezeEffort;
	static final double kCloseEffort = Constants.Squeezy.CloseEffort;
	static final double kOpenEffort  = Constants.Squeezy.OpenEffort;
	static final double kRightSpinMultiplier = Constants.Squeezy.RightSpinMultiplier;
	static final double kIntakeEffort = Constants.Squeezy.IntakeEffort;
	static final double kPinchEffort = Constants.Squeezy.PinchEffort;
	public static double kEjectEffort = Constants.Squeezy.EjectEffort;
	
	public enum SqueezyState {
		EMPTY, EJECT,
		//Auto State Chart
		INTAKE, CLOSING, HOLDING, TILT_UNJAM, UNJAM,
		//Manual State Chart
		MANUAL_OPEN, MANUAL_CLOSE
		
	}
	
	private boolean manualStateDiagram = false;
	private SqueezyState prevState = SqueezyState.EJECT;
	private SqueezyState state = SqueezyState.HOLDING;
	private ControllerHandler controller = ControllerHandler.getInstance();
	
	//References
	private static TalonSRX squeezer  = Devices.Squeezy.squeeze;
	private TalonSRX leftSpin  = Devices.Squeezy.leftSpin;
	private TalonSRX rightSpin = Devices.Squeezy.rightSpin;
	private SqueezySensors sensors = new SqueezySensors();
	private DoubleSolenoid fold = Devices.Squeezy.fold;
	
	//Eject Timing
	long ejectTime = System.currentTimeMillis();
	
	private Squeezy () {
		//Make sure that the motor output and encoder counts are in sync
			//OTHERWISE, the finely tuned closed-loop control becomes
			//chaotic and accelerates away from the setpoint
		squeezer.setSensorPhase(true);
		state = SqueezyState.HOLDING;
		updateState();
		update();
	}//Squeezy
	
	public void foldUp() {
		fold.set(DoubleSolenoid.Value.kReverse);
	}
	public boolean foldedUp() {
		return fold.get() == DoubleSolenoid.Value.kReverse;
	}
	public void foldDown() {
		fold.set(DoubleSolenoid.Value.kForward);
	}
	public boolean foldedDown() {
		return fold.get() == DoubleSolenoid.Value.kForward;
	}
	public void processFold() {
		if (controller.getPressed(HMI.kSqueezyDown)) {
			console.log("Squeezy down", Type.INTAKE);
			foldDown();
		}
		if (controller.getPressed(HMI.kSqueezyUp)) {
			console.log("Squeezy up", Type.INTAKE);
			foldUp();
		}
	}
	
	private TimedButton grabbedSensor = new TimedButton();
	private boolean leftUnjam = true;
	private void updateState() {
		if (squeezer.getSensorCollection().isFwdLimitSwitchClosed()) {
//			if (!calibrated)
			squeezer.setSelectedSensorPosition(0, 0, 10);
		}
		
		prevState = state;
		
		switch (state) {
		case EMPTY:
			if (controller.getPressed(HMI.kSqueezyIntake)) {
				state = SqueezyState.INTAKE;
				manualStateDiagram = false;
			}
			break;
		case EJECT:
			if ((System.currentTimeMillis() - ejectTime) > 1000)
				if (manualStateDiagram)
					state = SqueezyState.MANUAL_OPEN;
				else
					state = SqueezyState.UNJAM;
			break;
		//--------------------------Auto State Chart--------------------------//
		case INTAKE:
			//UltraSonic: Move directly to holding when box is detected by motor stalls
			if (sensors.detectBox()) {
				state = SqueezyState.CLOSING;
				grabbedSensor.reset();
			}
			break;
		case CLOSING:
//			if (controller.getPressed(HMI.kSqueezyCancel))
//				state = SqueezyState.UNJAM;
//			if (!(sensors.detectBox() && squeezer.getSelectedSensorPosition(0) > -90000))
//				state = SqueezyState.INTAKE;
//			if (sensors.detectBoxHeld()) {
//				state = SqueezyState.HOLDING;
//				ControllerHandler.getInstance().rumbleHardFor(0.5, 0.5);
			int vel = getEncoderVelocity();
			int pos = getEncoderPosition();
			boolean bool = vel < 10 && vel > -500;
//				bool = bool && pos < -60000;
			grabbedSensor.update(bool);
				
			if (grabbedSensor.get(20)) {
				state = SqueezyState.HOLDING;
				controller.rumbleHardFor(0.5, 0.5);
			}
			if (squeezer.getSensorCollection().isRevLimitSwitchClosed())
				state = SqueezyState.INTAKE;
			break;
		case HOLDING:
			if (squeezer.getSensorCollection().isRevLimitSwitchClosed())
				state = SqueezyState.INTAKE;
//			if (getEncoderPosition() > kHasCubePosition) {
			if (controller.getPressed(HMI.kSqueezyKnock)) {
				leftUnjam = sensors.getDistances()[1] > sensors.getDistances()[2];
				ejectTime = System.currentTimeMillis();
				state = SqueezyState.TILT_UNJAM;
			}
			break;
		case TILT_UNJAM:
			if (squeezer.getSensorCollection().isRevLimitSwitchClosed())
				state = SqueezyState.EMPTY;
			if (System.currentTimeMillis() - ejectTime > 500) {
				state = SqueezyState.HOLDING;
			}
			break;
		case UNJAM:
			if (squeezer.getSensorCollection().isFwdLimitSwitchClosed())
				state = SqueezyState.INTAKE;
			if (controller.getPressed(HMI.kSqueezyIntake))
				state = SqueezyState.INTAKE;
			break;
			
		//---------------------Manual State Chart------------------------//
		case MANUAL_OPEN:
		case MANUAL_CLOSE:
			//Manual Controls are available at any time,
				// thus they do not fall in here.
			//However, we do want the ability to go back into auto/intake mode.
			if (controller.getPressed(HMI.kSqueezyIntake)) {
				state = SqueezyState.INTAKE;
				manualStateDiagram = false;
			}
			break;
		}
		
		if (controller.getPressed(HMI.kSqueezyOpen)) {
			state = SqueezyState.MANUAL_OPEN;
			manualStateDiagram = true;
		}
		if (controller.getPressed(HMI.kSqueezyClose)) {
			state = SqueezyState.MANUAL_CLOSE;
			manualStateDiagram = true;
		}
		
		if (controller.getPressed(HMI.kSqueezyEject)) {
			console.log("Ejecting", Type.INTAKE);
			ejectTime = System.currentTimeMillis();
			state = SqueezyState.EJECT;
		}
		if (controller.getPressed(HMI.kSqueezyNeutral))
			state = SqueezyState.EMPTY;
		
	}
	
	private void update() {
		sensors.update();
		console.log(state.toString(), Type.AUTO);
		switch (state) {
		case EMPTY:
			foldDown();
			spinStop();
			if (foldedUp())
				close();
			else
				leave();
			break;
		case EJECT:
			foldDown();
			spinOut();
			shootSqueeze();
			break;
		case INTAKE:
			foldDown();
			spinIn();
			if (foldedUp())
				close();
			else
				open();
			break;
		case CLOSING:
			foldDown();
			spinIn();
			close();
			break;
		case HOLDING:
			foldUp();
			spinPinch();
			hold();
			break;
		case TILT_UNJAM:
			foldUp();
			spinUnjam();
			hold();
			break;
		case UNJAM:
			foldDown();
			spinStop();
			if (foldedUp())
				close();
			else
				open();
			break;
		case MANUAL_OPEN:
			foldDown();
			spinIn();
			if (foldedDown())
				open();
			else
				close();
			break;
		case MANUAL_CLOSE:
			foldDown();
			spinIn();
			close();
			break;
		}
	}
	
	public double getRelativeEncoderPosition() {
		double raw_pos = getEncoderPosition();
		double rel_pos = raw_pos / -120000;
		
		return rel_pos;
	}
	public double getRelativeEncoderVelocity() {
		int raw_vel = getEncoderVelocity();
		
		//if raw_vel = (raw_pos - raw_prev_pos) / time
		//and pos = raw_pos / -120000.0
		
		double rel_vel = raw_vel / -120000;
				
		return rel_vel;
	}
	public int getEncoderPosition() {
		return squeezer.getSelectedSensorPosition(0);
	}
	public static int getEncoderVelocity() {
		return squeezer.getSelectedSensorVelocity(0);
	}
	
	public boolean getOpenLimitSwitch() {
		return squeezer.getSensorCollection().isFwdLimitSwitchClosed();
	}

	public boolean getClosedLimitSwitch() {
		return squeezer.getSensorCollection().isRevLimitSwitchClosed();
	}

	public void forceState(SqueezyState newState) {
		state = newState;
		if (state == SqueezyState.EJECT)
			ejectTime = System.currentTimeMillis();
	}
	
	public boolean isInState(SqueezyState checkState) {
		return state == checkState;
	}
	
	public boolean hasCube() {
		return state == SqueezyState.HOLDING;
	}
	
	private void setSpinners(double effort) {
		setSpinners(effort, 0);
	}
	private void setSpinners(double effort, int invert) {
		switch (invert) {
			case -1:
				leftSpin.set(ControlMode.PercentOutput, -effort);
				rightSpin.set(ControlMode.PercentOutput, -kRightSpinMultiplier*effort);
				break;
			case 0:
				leftSpin.set(ControlMode.PercentOutput, effort);
				rightSpin.set(ControlMode.PercentOutput, -kRightSpinMultiplier*effort);
				break;
			case 1:
				leftSpin.set(ControlMode.PercentOutput, effort);
				rightSpin.set(ControlMode.PercentOutput, kRightSpinMultiplier*effort);
				break;
		}
	}	
	private void spinIn() {
		setSpinners(kIntakeEffort);
	}
	private void spinOut() {
		setSpinners(kEjectEffort);
	}
	private void spinStop() {
		setSpinners(0);
	}
	private void spinPinch() {
		setSpinners(kPinchEffort);
	}
	private void spinUnjam() {
		setSpinners(kIntakeEffort, leftUnjam?-1:1);
	}
	
	private void open() {
		squeezer.set(ControlMode.PercentOutput, kOpenEffort);
	}
	private void close() {
		squeezer.set(ControlMode.PercentOutput, kCloseEffort);
	}
	private void shootSqueeze() {
		squeezer.set(ControlMode.PercentOutput, kShootSqueezeEffort);
	}
	private void hold() {
		squeezer.set(ControlMode.PercentOutput, kHoldEffort);
	}
	private void leave() {
		squeezer.set(ControlMode.PercentOutput, 0);
	}

	public void init() {
		
	}

	public void teleopUpdate() {
		update();
	}

	public void autoUpdate() {
		//update();
	}

	public void idleUpdate() {
		
	}

	public void initNetworkPosting() {
		
	}

	public void postToNetwork() {
		
	}

	public void teleopInit() {
		forceState(SqueezyState.INTAKE);
	}

	public void autoInit() {
		//squeezy.forceState(SqueezyState.HOLDING);
		forceState(SqueezyState.EMPTY);
		foldUp();
	}
	
	public class SqueezySensors {
		private FilteredUltraSonic centerUltra = new FilteredUltraSonic(0, 1, 50);
		private FilteredUltraSonic leftUltra = new FilteredUltraSonic(2, 3, 5);
		private FilteredUltraSonic rightUltra = new FilteredUltraSonic(4, 5, 5);
		
		private SqueezySensors() {
			centerUltra.init();
			leftUltra.init();
			rightUltra.init();
		}
		
		public void update() {
			centerUltra.update();
			leftUltra.update();
			rightUltra.update();
		}

		public boolean detectBox() {
			/*
			 * Squeezy's maximum no-block separation is 19in.
			 * Squeezy's minimum no-block separation is 8in
			 * 
			 * So, if the sum of the distances from the ultrasonics falls under 15in,
			 * it must be that there is a block between the two ultrasonics.
			 * 
			 * At the shortest distance, Left+Right will still be above 15in,
			 * at the largest distance, any significant block-sized object, (11-13in)
			 * will bring the Left+Right distance down to (19-11)+(0) == 8in.
			 */
			double left = leftUltra.getDistance();
			double right = rightUltra.getDistance();
			//if (leftUltra.getDistance() + rightUltra.getDistance() < 18)
			if (left < 10 || right < 10)
				return true;
			else
				return false;
		}
		
		public boolean detectBoxGone() {
			//if (!new Joystick(0).getRawButton(6))
			if (centerUltra.getDistance() > 11.5)
				return true;
			else
				return false;
			//if (leftUltra.getDistance() > 4 && rightUltra.getDistance() > 4)
			//	return true;
			//else
			//	return false;
		}
		
		public String encVel () {
			int vel = Squeezy.getEncoderVelocity();
			boolean bool = Math.abs(vel) < 30;
			return "Vel Check: "+bool+" -- "+vel;
		}
		
		public boolean detectBoxHeld() {
			//if (new Joystick(0).getRawButton(6))/*3-11-18*/
			
			if (centerUltra.getDistance() < /*6*//*5.5*//*New 3d plate*/3)
				//leftUltra.getDistance() < 2 &&
				//rightUltra.getDistance() < 2)
				return true;
			else
				return false;
		}
		
		public double[] getDistances() {
			double[] distances = new double[3];
			distances[0] = centerUltra.getDistance();
			distances[1] = leftUltra.getDistance();
			distances[2] = rightUltra.getDistance();
			
			return distances;
		}
	}
}