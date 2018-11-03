package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerPathAction;
import frc.team5104.auto.BreakerTrajectoryFollower;
import frc.team5104.auto.BreakerTrajectoryGenerator;
import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.Odometry;
import frc.team5104.util.console;
import frc.team5104.util.console.c;
import jaci.pathfinder.Waypoint;

/*Breakerbots Robotics Team 2018*/
/**
 * Follow a trajectory using the Breaker Trajectory Follower (Ramses Follower)
 */
public class DriveTrajectory extends BreakerPathAction {

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
    	DriveActions.set(f.getNextDriveSignal(Odometry.getPosition()), true);
    	
		return f.isFinished();
    }

    public void end() {
    	DriveActions.stop();
    	console.log(c.AUTO, "Trajectory Finished in " + console.sets.getTime("RunTrajectoryTime") + "s at position " + Odometry.getPosition().toString());
    }
}
