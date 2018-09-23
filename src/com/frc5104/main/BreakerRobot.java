package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.main.subsystems.BreakerSubsystemManager;
import com.frc5104.utilities.console;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.console.c;
import com.frc5104.utilities.console.t;

import edu.wpi.first.wpilibj.IterativeRobot;

/*Breakerbots Robotics Team 2018*/
/**
 * Just an Extension onto Interative Robot
 * Just misc function calls that (almost) never change
 */
public abstract class BreakerRobot extends IterativeRobot {
	private static boolean isEnabled = false;
	
	// -- Robot
	public abstract void _robotInit();
	public void robotInit() {
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		_robotInit();
		BreakerSubsystemManager.init();
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
	}
	public void robotPeriodic() {
		BreakerSubsystemManager.idleUpdate();
	}
	public void disabledInit() {
		pitchEnabledStatus(false);
		console.logFile.end();
	}
	
	
	// -- Autonomous
	public abstract void _autonomousInit();
	public void autonomousInit() {
		pitchEnabledStatus(true);
		console.logFile.start();
		console.log(c.AUTO, "Initalizing Autonomous");
		
		BreakerSubsystemManager.autoInit();
		
		_autonomousInit();
	}
	public void autonomousPeriodic() {
		BreakerCommandScheduler.getInstance().update();
		BreakerSubsystemManager.autoUpdate();
	}
	
	
	// -- Teleoperation
	public abstract void _teleopInit();
	public void teleopInit() {
		pitchEnabledStatus(true);
		console.logFile.start();
		console.log(c.TELEOP, "Initializing Teleop");
		  
		BreakerSubsystemManager.teleopInit();
		
		_teleopInit();
	}
	public void teleopPeriodic() {
		controller.update();
		BreakerSubsystemManager.teleopUpdate();
	}
	
	
	// -- Test Mode
	public abstract void _testInit();
	public abstract void _testPeriodic();
	public void testInit() {
		pitchEnabledStatus(true);
		_testInit();
	}
	
	public void testPeriodic() {
		_testPeriodic();
	}
	
	// -- Enabling
	private void pitchEnabledStatus(boolean status) {
		if (isEnabled != status) {
			isEnabled = status;
			console.log(c.MAIN, t.INFO, "Robot " + (isEnabled ? "Enabled" : "Disabled"));
		}
	}
}
