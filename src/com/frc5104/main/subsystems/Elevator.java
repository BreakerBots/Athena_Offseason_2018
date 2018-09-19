package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.main.subsystems.Drive.shifters.Gear;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.Deadband;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/*Breakerbots Robotics Team 2018*/
public class Elevator extends BreakerSubsystem {
	private static Elevator _inst = null; 
	public static Elevator getInstance() { if (_inst == null) _inst = new Elevator(); return _inst; }
	
	//Constants References
	public static final double kDownScalar = Constants.Elevator.DownScalar;
	public static final int SOFT_STOP_BOTTOM = Constants.Elevator.SOFT_STOP_BOTTOM;
	public static final int SOFT_STOP_TOP = Constants.Elevator.SOFT_STOP_TOP;
	
	//References
	private ControllerHandler controller = ControllerHandler.getInstance();
	private TalonSRX talon1 = Devices.Elevator.a;
	private TalonSRX talon2 = Devices.Elevator.b;
	
	public enum Control {
		kEffort, 
		kCalibrate //Reset the encoder by driving downwards until limit switch is hit
	}
	private boolean calibrated = false;
	Control controlMode = Control.kCalibrate;
	Stage currentStage;

	public double effort = 0;
	public int prevEnc = -1;
	
	public enum Stage {
		kBottom(0),
		//kPortal(-3000),
		kSwitch(-5000),
		kLowerScale(-13500),
		kTop(-13770);
		
		int position;
		Stage (int position){
			this.position = position;
		}
		public int getCounts() {
			return this.position;
		}
	}
	
	private Elevator () {
		talon1.configReverseSoftLimitEnable(false, 10);
		talon1.configReverseSoftLimitThreshold(SOFT_STOP_TOP, 10);

		talon1.configForwardSoftLimitEnable(false, 10);
		talon1.configForwardSoftLimitThreshold(SOFT_STOP_BOTTOM, 10);
		
		currentStage = Stage.kBottom;
	}
	
	protected void teleopUpdate() {
		//if (controlMode == Elevator.Control.kEffort) {
			double output = Deadband.get(controller.getAxis(HMI.kElevatorUpDown), 0.2);
			if (output > 0) output *= kDownScalar;
			talon1.set(ControlMode.PercentOutput, output);
		//}
		
		vibrateIfApproaching();
		
		/*
		if (controlMode == Control.kCalibrate) {
			talon1.set(ControlMode.PercentOutput, 0.1);
			if (talon1.getSensorCollection().isFwdLimitSwitchClosed()) {
				//Reset Talon Encoder Position & Soft Limit
				talon1.setSelectedSensorPosition(0, 0, 10);
				
				calibrated = true;
				controlMode = Control.kEffort;
				clearIaccum();
				update();
			}
		}
		*/
	}

	public void vibrateIfApproaching() {
		if (!calibrated) return;
		//Raising the elevator triggers when it passes a setpoint,
		//lowering triggers when it approachDistance above a setpoint.
		int approachDistance = 100;
		
		int enc = talon1.getSelectedSensorPosition(0);
		
		for (Stage checkStage: Stage.values()) {
			boolean wasInZone, isInZone;
			if (effort > 0) {
				//DOWN (fwd limit switch is on the bottom)
				wasInZone = prevEnc > checkStage.getCounts()-approachDistance;
				isInZone = enc > checkStage.getCounts()-approachDistance;
			} else {
				//UP
				wasInZone = prevEnc < checkStage.getCounts();
				isInZone = enc < checkStage.getCounts();
			}
			//if (!wasInZone && isInZone)
				//ControllerHandler.getInstance().rumbleHardFor(1, 0.1);
		}
		
		prevEnc = enc;
	}
	
	public boolean isCalibrated() {
		return calibrated;
	}
	public int getError() {
		return talon1.getClosedLoopError(0);
	}
	
	public void clearIaccum() {
		talon1.setIntegralAccumulator(0, 0, 10);
	}
	
	public boolean onTarget() {
		return Math.abs(talon1.getSelectedSensorPosition(0) - currentStage.getCounts()) < 500;
	}
	
	public boolean getLowerLimit() {
		return talon1.getSensorCollection().isFwdLimitSwitchClosed();
	}
	
	public boolean getUpperLimit() {
		return talon1.getSensorCollection().isRevLimitSwitchClosed();
	}
	
	public int getEncoderPosition() {
		return talon1.getSelectedSensorPosition(0);
	}
	
	public boolean isLowEnoughToDrop() {
		return talon1.getSelectedSensorPosition(0) > SOFT_STOP_BOTTOM - 2000;
	}
	
	public Control controlMode() {
		return controlMode;
	}
	
	public void enableForwardLimitSwitch () {
		talon1.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
	}

	public void disableForwardLimitSwitch () {
		talon1.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen, 10);
	}

	protected void init() {
		
	}

	protected void autoUpdate() {
		
	}

	protected void idleUpdate() {
		
	}
	
	protected void initNetworkPosting() {
		//NetworkTableInstance.getDefault().getTable("elevator");
	}
	
	protected void postToNetwork() {
		
	}

	protected void teleopInit() {
		
	}

	protected void autoInit() {
		
	}
}
