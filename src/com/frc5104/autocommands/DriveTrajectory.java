package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;
import com.frc5104.traj.BreakerTrajectoryFollower;
import com.frc5104.traj.BreakerTrajectoryGenerator;
import com.frc5104.traj.Odometry;
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
    	console.sets.create("RunTrajectoryTime");
    	console.log(c.AUTO, "Running Trajectory");
    	
    	//Reset Odometry and Get Path (Reset it twice to make sure it all good)
    	Odometry.reset();
    	f = new BreakerTrajectoryFollower( BreakerTrajectoryGenerator.getTrajectory(p) );
		Odometry.reset();
		
		//Wait 100ms for Device Catchup
		try { Thread.sleep(100); }  catch (Exception e) { console.error(e); e.printStackTrace(); }
    }

    public boolean update() {
        Drive.set(f.getNextDriveSignal(Odometry.getPosition()));
    	
		return f.isFinished();
    }

    public void end() {
    	Drive.stop();
    	console.log(c.AUTO, "Trajectory Finished in " + console.sets.getTime("RunTrajectoryTime") + "s");
    }
}
