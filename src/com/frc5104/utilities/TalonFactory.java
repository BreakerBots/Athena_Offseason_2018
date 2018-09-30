package com.frc5104.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/*Breakerbots Robotics Team 2018*/
/**
 * Brewing fresh talons since 2017
 */
public class TalonFactory {
	public static class settings {
		public static final int timeoutMs = 10;
	}
	
	/**
	 * Creates a TalonSRX and Factory Resets the Settings [Base Function]
	 * @param id Device ID of the TalonSRX
	 */
	public static TalonSRX getTalon(int id) {
		TalonSRX talon = new TalonSRX(id);
		
		talon.configOpenloopRamp(0, settings.timeoutMs);
		talon.configClosedloopRamp(0, settings.timeoutMs);
		talon.configPeakOutputForward(1, settings.timeoutMs);
		talon.configPeakOutputReverse(-1, settings.timeoutMs);
		talon.configNominalOutputForward(0, settings.timeoutMs);
		talon.configNominalOutputReverse(0, settings.timeoutMs);
		talon.configNeutralDeadband(0.0, settings.timeoutMs);
		talon.configVoltageCompSaturation(0, settings.timeoutMs);
		talon.configVoltageMeasurementFilter(32, settings.timeoutMs);
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, settings.timeoutMs);
		//talon.configSelectedFeedbackSensor(RemoteFeedbackDevice.None, 0, 0);
		//talon.configSelectedFeedbackCoefficient(1.0);
		//talon.configRemoteFeedbackFilter(off 0);
		//talon.configSensorTerm	Quad (0) for all term types);
		talon.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, settings.timeoutMs);
		talon.configVelocityMeasurementWindow(64, settings.timeoutMs);
		//talon.configReverseLimitSwitchSource(0, and "Normally Open");
		//talon.configForwardLimitSwitchSource(LimitSwitchSource.?, LimitSwitchNormal.NormallyOpen, 0);
		talon.configForwardSoftLimitThreshold(0, settings.timeoutMs);
		talon.configReverseSoftLimitThreshold(0, settings.timeoutMs);
		talon.configForwardSoftLimitEnable(false, settings.timeoutMs);
		talon.configReverseSoftLimitEnable(false, settings.timeoutMs);
		talon.config_kP(0, 0, settings.timeoutMs);
		talon.config_kI(0, 0, settings.timeoutMs);
		talon.config_kD(0, 0, settings.timeoutMs);
		talon.config_kF(0, 0, settings.timeoutMs);
		talon.config_IntegralZone(0, 0, settings.timeoutMs);
		talon.configAllowableClosedloopError(0, 0, settings.timeoutMs);
		talon.configMaxIntegralAccumulator(0, 0, settings.timeoutMs);
		//talon.configClosedLoopPeakOutput(1.0);
		//talon.configClosedLoopPeriod(1);
		//talon.configAuxPIDPolarity(false);
		talon.configMotionCruiseVelocity(0, settings.timeoutMs);
		talon.configMotionAcceleration(0, settings.timeoutMs);
		//talon.configMotionProfileTrajectoryPeriod(0);
		talon.configSetCustomParam(0, 0, settings.timeoutMs);
		talon.configPeakCurrentLimit(0, settings.timeoutMs);
		talon.configContinuousCurrentLimit(0, settings.timeoutMs);
		talon.setNeutralMode(NeutralMode.Brake);
		
		talon.set(ControlMode.PercentOutput, 0);
		
		return talon;
	}
	
	/**
	 * Creates a TalonSRX, Factory Resets the Settings, and makes it a follower (matches the speed) of another talon
	 * @param id Device ID of the TalonSRX
	 * @param followerId Device ID of the TalonSRX to follow
	 */
	public static TalonSRX getTalonFollower(int id, int followerId) {
		TalonSRX talon = getTalon(id);
		talon.set(ControlMode.Follower, followerId);
		return talon;
	}
 }
