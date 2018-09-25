package com.frc5104.main;

import com.frc5104.autocommands.BreakerPathScheduler;
import com.frc5104.main.subsystems.BreakerSubsystemManager;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.math.Odometry;
import com.frc5104.utilities.console;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.console.c;
import com.frc5104.utilities.console.t;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;

/*Breakerbots Robotics Team 2018*/
/**
 * Just an Extension onto Interative Robot
 * Just misc function calls that (almost) never change
 */
public abstract class BreakerRobot extends TimedRobot {
	private static boolean isEnabled = false;
	private static boolean printedMatch = false;
	
	// -- Robot
	public abstract void _robotInit();
	public void robotInit() {
		//Run the loops at 50hz
		this.setPeriod(1.0 / Constants.Loops._robotHz);
		
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		//Call fallthrough
		_robotInit();
		
		//Run Odometry
		Odometry.run();
		
		//Init Subsystems
		BreakerSubsystemManager.init();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
	}
	public void robotPeriodic() {
		//Idle Update Subsystems
		BreakerSubsystemManager.idleUpdate();
		
		if (DriverStation.getInstance().isFMSAttached() && !printedMatch) {
			System.out.println(
				"[Match]: " +
				DriverStation.getInstance().getMatchType().toString() + " Match " +
				"#" + DriverStation.getInstance().getMatchNumber() + " " +
				"at " + DriverStation.getInstance().getEventName() + " " + 
				"on the " + DriverStation.getInstance().getAlliance() + "Alliance"
			);
			printedMatch = true;
		}
		
		if (RobotController.isBrownedOut())
			console.error("Robot Browning Out! Battery Voltage: " + RobotController.getBatteryVoltage() + ", Input Current: " + RobotController.getInputCurrent() + ", Input Voltage: " + RobotController.getInputVoltage());
	}
	public void disabledInit() {
		pitchEnabledStatus(false);
		
		//End the log file
		console.logFile.end();
	}
	
	
	// -- Autonomous
	public abstract void _autonomousInit();
	public void autonomousInit() {
		pitchEnabledStatus(true);
		
		console.logFile.start();
		console.log(c.AUTO, "Initalizing Autonomous");
		
		//Stop Motors
		Drive.stop();
		
		//Auto Init Subsystems
		BreakerSubsystemManager.autoInit();
		
		//Call fallthrough
		_autonomousInit();
	}
	public void autonomousPeriodic() {
		//Run Autonomous Path
		BreakerPathScheduler.getInstance().update();
		
		//Auto Update Subsystems
		BreakerSubsystemManager.autoUpdate();
	}
	
	
	// -- Teleoperation
	public abstract void _teleopInit();
	public void teleopInit() {
		pitchEnabledStatus(true);
		
		console.logFile.start();
		console.log(c.TELEOP, "Initializing Teleop");
		  
		//Teleop Init Subsystems
		BreakerSubsystemManager.teleopInit();
		
		//Call fallthrough
		_teleopInit();
	}
	public void teleopPeriodic() {
		//Update the Controll Handler Class
		controller.update();
		
		//Teleop Update Subsystems
		BreakerSubsystemManager.teleopUpdate();
	}
	
	
	// -- Test Mode
	public abstract void _testInit();
	public abstract void _testPeriodic();
	public void testInit() {
		pitchEnabledStatus(true);
		
		//Call fallthrough
		_testInit();
	}
	
	public void testPeriodic() {
		//Call fallthrough
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
