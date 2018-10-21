package frc.team5104.main;

import frc.team5104.autocommands.BreakerPathScheduler;
import frc.team5104.main.subsystems.BreakerSubsystemManager;
import frc.team5104.main.subsystems.Drive;
import frc.team5104.traj.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.controller;
import frc.team5104.util.console.c;
import frc.team5104.util.console.t;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;

/* Breakerbots Robotics Team 2018
 *  ____                 _             _           _       
 * | __ ) _ __ ___  __ _| | _____ _ __| |__   ___ | |_ ___ 
 * |  _ \| '__/ _ \/ _` | |/ / _ \ '__| '_ \ / _ \| __/ __|
 * | |_) | | |  __/ (_| |   <  __/ |  | |_) | (_) | |_\__ \
 * |____/|_|  \___|\__,_|_|\_\___|_|  |_.__/ \___/ \__|___/ 
 */
/**
 * <h1>Breaker Robot Controller</h1>
 * An extension onto the Timed Robot
 * Calls system manages, logs, and drops functions down to a BreakerRobot (Robot.java)
 */
public class BreakerRobotController extends TimedRobot {
	private static boolean isEnabled = false;
	private static boolean printedMatch = false;
	private static BreakerRobot robot = new Robot();
	
	// -- Robot
	public void robotInit() {
		//Run the loops at 50hz
		this.setPeriod(1.0 / Constants.Loops._robotHz);
		
		console.sets.create("RobotInit");
		console.log(c.MAIN, t.INFO, "Initializing Code");
		
		//Call fallthrough
		robot.robotInit();
		
		//Init Subsystems
		BreakerSubsystemManager.init();
		
		//Run Odometry
		Odometry.run();
		
		console.log(c.MAIN, "Devices Created and Seth Proofed");
		
		console.sets.log(c.MAIN, t.INFO, "RobotInit", "Initialization took ");
	}
	
	public void robotPeriodic() {
		//Idle Update Subsystems
		BreakerSubsystemManager.idleUpdate();
		
		//Print out the Match
		try { if (DriverStation.getInstance().isFMSAttached() && !printedMatch) {
			System.out.println(
				"[Match]: " +
				DriverStation.getInstance().getMatchType().toString() + " Match " +
				"#" + DriverStation.getInstance().getMatchNumber() + " " +
				"at " + DriverStation.getInstance().getEventName() + " " + 
				"on the " + DriverStation.getInstance().getAlliance() + "Alliance"
			);
			printedMatch = true;
		} } catch (Exception err) { console.error(err); }
		
		//Call fallthrough
		robot.robotPeriodic();
		
		if (RobotController.isBrownedOut())
			console.error("Robot Browning Out! Battery Voltage: " + RobotController.getBatteryVoltage() + ", Input Current: " + RobotController.getInputCurrent() + ", Input Voltage: " + RobotController.getInputVoltage());
	}
	public void disabledInit() {
		pitchEnabledStatus(false);
		
		BreakerSubsystemManager.disabled();
		
		//End the log file
		console.logFile.end();
	}
	
	
	// -- Autonomous
	public void autonomousInit() {
		pitchEnabledStatus(true);
		
		console.logFile.start();
		console.log(c.AUTO, "Initalizing Autonomous");
		
		//Stop Motors
		Drive.stop();
		
		//Auto Init Subsystems
		BreakerSubsystemManager.autoInit();
		
		//Call fallthrough
		robot.autonomousInit();
	}
	public void autonomousPeriodic() {
		//Run Autonomous Path
		BreakerPathScheduler.getInstance().update();
		
		//stupid rumble thing
		controller.update();
		
		//Auto Update Subsystems
		BreakerSubsystemManager.autoUpdate();
		
		//Call fallthrough
		robot.autonomousPeriodic();
	}
	
	
	// -- Teleoperation
	public void teleopInit() {
		pitchEnabledStatus(true);
		
		console.logFile.start();
		console.log(c.TELEOP, "Initializing Teleop");
		  
		//Teleop Init Subsystems
		BreakerSubsystemManager.teleopInit();
		
		//Call fallthrough
		robot.teleopInit();
	}
	public void teleopPeriodic() {
		//Update the Controll Handler Class
		controller.update();
		
		//Teleop Update Subsystems
		BreakerSubsystemManager.teleopUpdate();
		
		//Call fallthrough
		robot.teleopPeriodic();
		
		DriverStation.getInstance().waitForData(1000 / Constants.Loops._robotHz);
	}
	
	
	// -- Test Mode
	public void testInit() {
		pitchEnabledStatus(true);
		
		//Call fallthrough
		robot.testInit();
	}
	
	public void testPeriodic() {
		//Call fallthrough
		robot.testPeriodic();
	}
	
	// -- Enabling
	private void pitchEnabledStatus(boolean status) {
		if (isEnabled != status) {
			isEnabled = status;
			console.log(c.MAIN, t.INFO, "Robot " + (isEnabled ? "Enabled" : "Disabled"));
		}
	}
	
	/**
	 * <h1>Breaker Robot</h1>
	 * A fallthrough from the <strong>Breaker Robot Controller</strong>
	 * <br><strong>Functions:</strong>
	 * <br> - robotInit
	 * <br> - robotPeriodic
	 * <br> - autonomousInit
	 * <br> - autonomousPeriodic
	 * <br> - teleopInit
	 * <br> - teleopPeriodic
	 * <br> - testInit
	 * <br> - testPeriodic
	 */
	public interface BreakerRobot {
		public default void robotInit() {};
		public default void robotPeriodic() {};
		public default void autonomousInit() {};
		public default void autonomousPeriodic() {};
		public default void teleopInit() {};
		public default void teleopPeriodic() {};
		public default void testInit() {};
		public default void testPeriodic() {};
	}
}