package com.frc5104.autocommands;

import com.frc5104.main.Constants;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.traj.BreakerTrajectoryGenerator;
import com.frc5104.traj.RobotDriveSignal;
import com.frc5104.traj.RobotDriveSignal.DriveUnit;
import com.frc5104.utilities.BreakerMath;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/*Breakerbots Robotics Team 2018*/
/**
 * Follow a Trajectory using Pathfinder
 * (!) Deprication Notice
 */
public class DriveTrajectoryWP extends BreakerAction {

	private Trajectory t;
	private TankModifier m;
	private EncoderFollower l;
	private EncoderFollower r;
	private Waypoint[] p;
		
    public DriveTrajectoryWP(Waypoint[] points) {
    	this.p = points;
    }

    public void init() {
    	console.sets.create("RunTrajectoryTime");
    	console.log(c.AUTO, "Running TrajectoryWP");
    	
    	t = BreakerTrajectoryGenerator.getTrajectoryWP(p);
    	m = BreakerTrajectoryGenerator.getTankModifier(t);
    	l = new EncoderFollower(m.getLeftTrajectory());
		r = new EncoderFollower(m.getRightTrajectory());
    	
    	//Reset Devices
    	Drive.gyro.reset();
		Drive.encoders.reset(10);
		
		//Configure Tank Drive
		l.configureEncoder(0, (int) Math.round(Constants._ticksPerRevolution), Constants._wheelDiameter);
		l.configurePIDVA(Constants.AutonomousWP._PIDA[0], Constants.AutonomousWP._PIDA[1], Constants.AutonomousWP._PIDA[2], 1 / Constants.AutonomousWP._maxVelocity, Constants.AutonomousWP._PIDA[3]);
		r.configureEncoder(0, (int) Math.round(Constants._ticksPerRevolution), Constants._wheelDiameter);
		r.configurePIDVA(Constants.AutonomousWP._PIDA[0], Constants.AutonomousWP._PIDA[1], Constants.AutonomousWP._PIDA[2], 1 / Constants.AutonomousWP._maxVelocity, Constants.AutonomousWP._PIDA[3]);
		
		//Wait 100ms for Device Catchup
		try {
			Thread.sleep(100);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public boolean update() {
		int le = Drive.encoders.getLeft();
		int re = Drive.encoders.getRight();
		
		//Calculate Left and Right Speed for Wheels
		double ls = l.calculate(-le);
		double rs = r.calculate(-re);

		//Calculate Angle
		double a = Pathfinder.boundHalfDegrees((-1.0/80.0) * (Pathfinder.boundHalfDegrees(Pathfinder.r2d(l.getHeading()) - Drive.gyro.getAngle())) * Constants.AutonomousWP._angleMult);
		
		//Clamp All Varibles
		a = BreakerMath.clamp(a, -1, 1);
		ls = BreakerMath.clamp(ls, -1, 1);
		rs = BreakerMath.clamp(rs, -1, 1);
		
		//Move The Wheels
		Drive.set(
			new RobotDriveSignal(
				-ls + a, //Left
				-rs - a,  //Right
			DriveUnit.percentOutput)
		);
		
		//Return
		return l.isFinished() && r.isFinished();
    }

    public void end() {
    	Drive.stop();
    	console.log(c.AUTO, "TrajectoryWP Finished in " + console.sets.getTime("RunTrajectoryTime") + "s");
    }
}
