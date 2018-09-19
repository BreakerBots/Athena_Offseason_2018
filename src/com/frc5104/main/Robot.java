package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.autopaths.*;
import com.frc5104.main.subsystems.*;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;
import com.frc5104.utilities.console.t;

import edu.wpi.first.wpilibj.IterativeRobot;

/*Breakerbots Robotics Team 2018*/
public class Robot extends IterativeRobot {

	
	// -- Robot
	public void robotInit() {
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		BreakerSubsystemManager.throwSubsystems(new BreakerSubsystem[] {
			Drive.getInstance(), 
			SqueezyV2.getInstance(), 
			Elevator.getInstance(), 
			Climber.getInstance()
		});
		BreakerSubsystemManager.init();
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
	}
	public void robotPeriodic() {
		BreakerSubsystemManager.idleUpdate();
	}
	public void disabledInit() {
		console.logFile.end();
	}
	
	
	// -- Autonomous
	public void autonomousInit() {
		console.logFile.start();
		console.log(c.AUTO, "Initalizing Autonomous");
		
		BreakerSubsystemManager.autoInit();
		
		//BreakerCommandScheduler.getInstance().set(AutoSelector.getAuto());
		BreakerCommandScheduler.getInstance().set(AutoSelector.Paths.Baseline.getPath());
	}
	public void autonomousPeriodic() {
		BreakerCommandScheduler.getInstance().update();
		BreakerSubsystemManager.autoUpdate();
	}
	
	
	// -- Teleoperation
	public void teleopInit() {
		console.logFile.start();
		console.log(c.TELEOP, "Initializing Teleop");
		  
		BreakerSubsystemManager.teleopInit();
	}
	public void teleopPeriodic() {
		ControllerHandler.getInstance().update();
		BreakerSubsystemManager.teleopUpdate();
	}
	
	
	// -- Test Mode
	public void testInit() {
		
	}
	
	public void testPeriodic() {
		
	}
}