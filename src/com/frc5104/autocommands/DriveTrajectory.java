package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;
import com.frc5104.math.BreakerTrajectoryFollower;
import com.frc5104.math.BreakerTrajectoryGenerator;
import com.frc5104.math.Odometry;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

import jaci.pathfinder.Waypoint;

/*Breakerbots Robotics Team 2018*/
/**
 * Follow a trajectory using the Breaker Trajectory Follower (Ramses Follower)
 */
public class DriveTrajectory extends BreakerAction {

	private BreakerTrajectoryFollower f;
	private Waypoint[] p;
		
    public DriveTrajectory(Waypoint[] points) {
    	this.p = points;
    }

    public void init() {
    	f = new BreakerTrajectoryFollower(
	    			BreakerTrajectoryGenerator.getTrajectory(p)
	    		);
    	
    	console.log(c.AUTO, "Running Trajectory");
		
		//Reset Devices
		Drive.Gyro.reset();
		Drive.encoders.reset(10);
		
		Odometry.setPosition(f.getInitRobotPosition());
		
		//Wait 100ms for Device Catchup
		try { Thread.sleep(100); } 
		catch (Exception e) { console.error(e); e.printStackTrace(); }
    }

    public boolean update() {
		//int leftEncoder = Drive.encoders.getLeft();
		//int rightEncoder = Drive.encoders.getRight();
		//double angle = -Devices.Drive.Gyro.getAngle() / Math.cos(Pathfinder.d2r(Constants.Drive._gyroAngle));
		
        Drive.set(f.getNextDriveSignal(Odometry.getPosition()));
    	
		return f.isFinished();
    }

    public void end() {
    	Drive.stop();
    	console.log(c.AUTO, "Trajectory Finished");
    }
}
