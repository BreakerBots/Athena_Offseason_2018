package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.autopaths.*;
import com.frc5104.main.subsystems.*;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;
import edu.wpi.first.wpilibj.IterativeRobot;

/*Breakerbots Robotics Team 2018*/
public class Robot extends IterativeRobot {
	
	
	//  ----------------------------------------  Robot  ----------------------------------------  \\
	public void robotInit() {
		console.sets.create("RobotInit");
		console.log("Initializing Code", Type.MAIN);
		
		BreakerSubsystemManager.throwSubsystems(new BreakerSubsystem[] {
			Drive.getInstance(), 
			Squeezy.getInstance(), 
			Elevator.getInstance(), 
			Climber.getInstance()
		});
		BreakerSubsystemManager.init();
		
		//CameraServer.getInstance().startAutomaticCapture();
		console.sets.log("Initialization took", Type.MAIN, "RobotInit", "");
	}
	public void robotPeriodic() {
		BreakerSubsystemManager.idleUpdate();
	}
	public void disabledInit() {
		console.endLogFile();
	}
	

	
	//  ----------------------------------------  Autonomous  ----------------------------------------  \\
	public void autonomousInit() {
		console.startLogFile();
		console.log("Initalizing Autonomous", Type.AUTO);
		
		BreakerSubsystemManager.autoInit();
		
		//BreakerCommandScheduler.getInstance().set(AutoSelector.getAuto());
		BreakerCommandScheduler.getInstance().set(AutoSelector.Paths.Baseline.getPath());
	}
	public void autonomousPeriodic() {
		BreakerCommandScheduler.getInstance().update();
		BreakerSubsystemManager.autoUpdate();
	}
	
	
	
	//  ----------------------------------------  Teleop  ----------------------------------------  \\
	public void teleopInit() {
		console.startLogFile();
		console.log("Initializing Teleop", Type.TELEOP);
		  
		BreakerSubsystemManager.teleopInit();
	}
	public void teleopPeriodic() {
		ControllerHandler.getInstance().update();
		BreakerSubsystemManager.teleopUpdate();
	}
}