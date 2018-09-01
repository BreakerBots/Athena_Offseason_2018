package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.autopaths.*;
import com.frc5104.main.subsystems.*;
import com.frc5104.main.subsystems.Drive.shifters.Gear;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import edu.wpi.first.wpilibj.IterativeRobot;

/*Breakerbots Robotics Team 2018*/
public class Robot extends IterativeRobot {
	
	//  ----------------------------------------  Subsystems  ----------------------------------------  \\
	Drive drive = Drive.getInstance();
	
	Squeezy squeezy = Squeezy.getInstance();
	SqueezySensors squeezySensors = SqueezySensors.getInstance();
	
	Elevator elevator = Elevator.getInstance();
	
	ControllerHandler controller = ControllerHandler.getInstance();
	
	Climber climber = Climber.getInstance();
	
	
	
	//  ----------------------------------------  Main Init  ----------------------------------------  \\
	public void robotInit() {
		console.sets.create("RobotInit");
		console.log("Initializing Code", Type.MAIN);
		
		//squeezy.initTable(null);
		
		//elevator.initTable(null);
		
		climber.init();

		drive.init();
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		console.sets.log("Initialization took", Type.MAIN, "RobotInit", "");
	}
	
	
	
	//  ----------------------------------------  Autonomous  ----------------------------------------  \\
	public void autonomousInit() {
		console.log("Initalizing Autonomous", Type.AUTO);
		Drive.shifters.set(Gear.Low);
		
		//BreakerCommandScheduler.getInstance().set(AutoSelector.getAuto());
		BreakerCommandScheduler.getInstance().set(AutoSelector.Paths.Baseline.getPath());
		
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
		console.log("Initializing Teleop", Type.TELEOP);
		Drive.shifters.set(Gear.Low);
	}
	public void teleopPeriodic() {
		controller.update();
		
		drive.update();
		
		//climber.update();
		
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
