package frc.team5104.main;

import frc.team5104.autocommands.BreakerPathScheduler;
import frc.team5104.autopaths.AutoSelector;
import frc.team5104.main.subsystems.*;

import edu.wpi.first.wpilibj.CameraServer;

/* Breakerbots Robotics Team 2018
 *  ____                 _             _           _       
 * | __ ) _ __ ___  __ _| | _____ _ __| |__   ___ | |_ ___ 
 * |  _ \| '__/ _ \/ _` | |/ / _ \ '__| '_ \ / _ \| __/ __|
 * | |_) | | |  __/ (_| |   <  __/ |  | |_) | (_) | |_\__ \
 * |____/|_|  \___|\__,_|_|\_\___|_|  |_.__/ \___/ \__|___/ 
 */
/**
 * Fallthrough from <strong>Breaker Robot Controller</strong>
 */
public class Robot implements BreakerRobotController.BreakerRobot {
	public void robotInit() {
		BreakerSubsystemManager.throwSubsystems(
			Squeezy.class,
			Elevator.class,
			Drive.class
		);
		
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void autonomousInit() {
		BreakerPathScheduler.getInstance().set(
			AutoSelector.getAuto()
 			//AutoSelector.Paths.CL.getPath()
		);
	}
}
