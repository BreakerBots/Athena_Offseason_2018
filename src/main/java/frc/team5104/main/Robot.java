package frc.team5104.main;

import edu.wpi.first.wpilibj.CameraServer;
import frc.team5104.auto.AutoSelector;
import frc.team5104.auto.BreakerPathScheduler;
import frc.team5104.subsystem.BreakerSubsystemManager;
import frc.team5104.subsystem.climber.ClimberManager;
import frc.team5104.subsystem.drive.DriveManager;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.subsystem.elevator.ElevatorManager;
import frc.team5104.subsystem.squeezy.SqueezyManager;
import frc.team5104.teleop.BreakerTeleopController;
import frc.team5104.util.console;

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
public class Robot extends BreakerRobotController.BreakerRobot {
	public Robot() {
		BreakerSubsystemManager.throwSubsystems(
			new SqueezyManager(),
			new ElevatorManager(),
			new DriveManager(), 
			new ClimberManager()
		);
		
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	//Main
	public void mainEnabled() {
		BreakerSubsystemManager.enabled(mode);
		console.logFile.start();
		Odometry.reset();
	}
	
	public void mainDisabled() {
		BreakerSubsystemManager.disabled();
		console.logFile.end();
	}
	
	public void mainLoop() {
		if (enabled) {
			BreakerSubsystemManager.update();
		}
		//console.log(Odometry.getPosition().toString(), "A: " + DriveSystems.gyro.getAngle());
	}

	//Auto
	public void autoEnabled() {
		BreakerPathScheduler.set(
//			AutoSelector.getAuto()
 			AutoSelector.Paths.RL.getPath()
		);
	}
	
	public void autoLoop() {
		BreakerPathScheduler.update();
	}
	
	//Teleop
	public void teleopLoop() {
		BreakerTeleopController.update();
	}
}
