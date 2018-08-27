package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.autopaths.*;
import com.frc5104.main.subsystems.*;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.utilities.ControllerHandler;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

/*Breakerbots Robotics Team 2018*/
public class Robot extends IterativeRobot {
	
	//  ----------------------------------------  Subsystems  ----------------------------------------  \\
	Drive drive = Drive.getInstance();
	Shifters shifters = Shifters.getInstance();
	
	Squeezy squeezy = Squeezy.getInstance();
	SqueezySensors squeezySensors = SqueezySensors.getInstance();
	
	Elevator elevator = Elevator.getInstance();
	
	ControllerHandler controller = ControllerHandler.getInstance();
	
	Climber climber = Climber.getInstance();
	
	
	
	//  ----------------------------------------  Main Init  ----------------------------------------  \\
	public void robotInit() {
		System.out.println("MAIN: Running Code");
		
		//squeezy.initTable(null);
		
		//elevator.initTable(null);
		
		climber.init();

		drive.init();
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		System.out.println("MAIN: Initialized Code");
	}
	
	
	
	//  ----------------------------------------  Autonomous  ----------------------------------------  \\
	public void autonomousInit() {
		double curTime = (double)(System.currentTimeMillis());
		
		//BreakerCommandScheduler.getInstance().set(AutoSelector.getAuto());
		BreakerCommandScheduler.getInstance().set(AutoSelector.Paths.Baseline.getPath());
		
		System.out.println("Total time: " + (((double)(System.currentTimeMillis()) - curTime) / 1000) + "s");
		
		//squeezy.forceState(SqueezyState.HOLDING);
		squeezy.forceState(SqueezyState.EMPTY);
		squeezy.foldUp();
	}
	public void autonomousPeriodic() {
		BreakerCommandScheduler.getInstance().update();
		//squeezy.update();
	}
	
	
	
	//  ----------------------------------------  Teleop  ----------------------------------------  \\
	public void teleopInit() {
		if (shifters != null)
			shifters.shiftLow();
	}
	public void teleopPeriodic() {
		controller.update();
		
		drive.update();
		
		//climber.update();
		
		shifters.teleUpdate();
		
		elevator.userControl();
		
		squeezy.processFold();
		squeezy.updateState();
		squeezy.update();
	}
	
	
	
	//  ----------------------------------------  Main Loop  ----------------------------------------  \\	
	public void robotPeriodic() {
		squeezySensors.updateSensors();
		//squeezy.postData();
		
		//elevator.updateTables();
	}
}
