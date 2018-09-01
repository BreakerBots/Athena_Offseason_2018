package com.frc5104.main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.HMI;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.Deadband;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/*Breakerbots Robotics Team 2018*/
public class Elevator {

	public static final double kDownScalar = Constants.Elevator.DownScalar;
	
	public static final int SOFT_STOP_BOTTOM = Constants.Elevator.SOFT_STOP_BOTTOM;
	public static final int SOFT_STOP_TOP = Constants.Elevator.SOFT_STOP_TOP;
	
	public enum Stage {
		kBottom(0),
//		kPortal(-3000),
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
	}//Stage
	
	static Elevator m_instance = null;
	
	public static Elevator getInstance() {
		if (m_instance == null)
			m_instance = new Elevator();
		return m_instance;
	}//getInstance

	private ControllerHandler controller = ControllerHandler.getInstance();
	private TalonSRX talon1 = Devices.Elevator.a;
	private TalonSRX talon2 = Devices.Elevator.b;
	private NetworkTable table = null;
	
	public enum Control {
		kEffort, 
		kCalibrate //Reset the encoder by driving downwards until limit switch is hit
	}
	private boolean calibrated = false;
	Control controlMode = Control.kCalibrate;
	Stage currentStage;

	public Deadband userDeadband = new Deadband(0.2);
	public double effort = 0;
	public int prevEnc = -1;
	
	private Elevator () {
		talon1.configReverseSoftLimitEnable(false, 10);
		talon1.configReverseSoftLimitThreshold(SOFT_STOP_TOP, 10);

		talon1.configForwardSoftLimitEnable(false, 10);
		talon1.configForwardSoftLimitThreshold(SOFT_STOP_BOTTOM, 10);
		
		currentStage = Stage.kBottom;
	}
	
	public void userControl() {
		//if (controlMode == Elevator.Control.kEffort) {
			double output = userDeadband.get(controller.getAxis(HMI.kElevatorUpDown));
			if (output > 0) output *= kDownScalar;
			talon1.set(ControlMode.PercentOutput, output);
		//}
		
		//update();
		vibrateIfApproaching();
	}

	public void update() {
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
	}//update
	
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
			if (!wasInZone && isInZone)
				ControllerHandler.getInstance().rumbleHardFor(1, 0.1);
		}
		
		prevEnc = enc;
	}//vibrateIfApproaching
	
	//----- Elevator Sensors ------//
	public boolean isCalibrated() {
		return calibrated;
	}//isCalibrated
	
	public int getError() {
		return talon1.getClosedLoopError(0);
	}//getError
	
	public void clearIaccum() {
		talon1.setIntegralAccumulator(0, 0, 10);
	}
	
	public boolean onTarget() {
		return Math.abs(talon1.getSelectedSensorPosition(0) - currentStage.getCounts()) < 500;
	}//onTarget
	
	public boolean getLowerLimit() {
		return talon1.getSensorCollection().isFwdLimitSwitchClosed();
	}//getLowerLimit
	
	public boolean getUpperLimit() {
		return talon1.getSensorCollection().isRevLimitSwitchClosed();
	}//getUpperLimit
	
	public int getEncoderPosition() {
		return talon1.getSelectedSensorPosition(0);
	}//getEncoderPosition
	
	public boolean isLowEnoughToDrop() {
		return talon1.getSelectedSensorPosition(0) > SOFT_STOP_BOTTOM - 2000;
	}//isLowEnoughToDrop
	
	public Control controlMode() {
		return controlMode;
	}//controlMode
	
	public static boolean isRaised() {
		if (m_instance != null)
			return !m_instance.isLowEnoughToDrop();
		return false;
	}//isRaised
	
	//----- Network Tables ---------//
	public void initTable(NetworkTable inst) {
		if (inst == null) {
			inst = NetworkTableInstance.getDefault().getTable("elevator");
		}
		table = inst;
		
		if (!table.containsKey("closed_loop_control"))
			setBoolean("closed_loop_control",false);
		
		if (!table.containsKey("pid/clear_i_accum"))
			setBoolean("pid/clear_i_accum", false);
	}//initTable
	
	public void updateTables() {
		boolean lower = talon1.getSensorCollection().isFwdLimitSwitchClosed();
		boolean upper = talon1.getSensorCollection().isRevLimitSwitchClosed();
		if (lower) {
			talon1.setSelectedSensorPosition(0, 0, 10);
		}

		setBoolean("limits/lower-fwd", lower);
		setBoolean("limits/upper-rev", upper);
		
		setDouble("motor/effort", talon1.getMotorOutputPercent());
		setDouble("motor/voltage", talon1.getMotorOutputVoltage());
		setDouble("motor/current", talon1.getOutputCurrent());
		
		setDouble("motor2/effort", talon2.getMotorOutputPercent());
		setDouble("motor2/voltage", talon2.getMotorOutputVoltage());
		setDouble("motor2/current", talon2.getOutputCurrent());
		
		setDouble("pid/position", talon1.getSelectedSensorPosition(0));
		setDouble("pid/velocity", talon1.getSelectedSensorVelocity(0));
		
		setDouble("UpDown",talon1.getSelectedSensorPosition(0) / 1000);
		
		setDouble("pid/i_accum", talon1.getIntegralAccumulator(0));
		if (getBoolean("pid/clear_i_accum", false)) {
			setBoolean("pid/clear_i_accum", false);
			talon1.setIntegralAccumulator(0, 0, 10);
		}
	}//updateTables
	
	private void setString(String key, String value) {
		table.getEntry(key).setString(value);
	}//setString

	private String getString(String key, String defaultValue) {
		return table.getEntry(key).getString(defaultValue);
	}//getBoolean
	
	private void setDouble(String key, double value) {
		table.getEntry(key).setDouble(value);
	}//setDouble

	private double getDouble(String key, double defaultValue) {
		return table.getEntry(key).getDouble(defaultValue);
	}//getBoolean
	
	private void setBoolean(String key, boolean value) {
		table.getEntry(key).setBoolean(value);
	}//setBoolean
	
	private boolean getBoolean(String key, boolean defaultValue) {
		return table.getEntry(key).getBoolean(defaultValue);
	}//getBoolean
	
	
	public void enableForwardLimitSwitch () {
		talon1.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
		setBoolean("Limit Switch Enabled", true);
	}//disableForwardLimitSwitch

	public void disableForwardLimitSwitch () {
		talon1.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen, 10);
		setBoolean("Limit Switch Enabled", false);
	}//disableForwardLimitSwitch

	
}//Elevator
