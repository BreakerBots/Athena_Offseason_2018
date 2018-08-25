package com.frc5104.main;

import com.frc5104.autopaths.AutoSelector;
import com.frc5104.main.subsystems.Climber;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.main.subsystems.Elevator;
import com.frc5104.main.subsystems.Shifters;
import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.main.subsystems.SqueezySensors;
import com.frc5104.utilities.ControllerHandler;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
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
		
		squeezy.initTable(null);
		
		elevator.initTable(null);
		
		climber.init();

		CameraServer.getInstance().startAutomaticCapture();
	}
	
	
	
	//  ----------------------------------------  Autonomous  ----------------------------------------  \\
	public void autonomousInit() {
		squeezy.forceState(SqueezyState.HOLDING);
		squeezy.foldUp();
		
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(AutoSelector.getAuto());
	}
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		squeezy.update();
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
		squeezy.postData();
		
		elevator.updateTables();
	}
}
