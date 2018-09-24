package com.frc5104.math;

import com.frc5104.main.Constants;
import com.frc5104.main.Units;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

/*Breakerbots Robotics Team 2018*/
/**
 * Pathfinder Trajectory Follower (Ramsete Follower)
 * Based of 3863's implementation of "https://www.dis.uniroma1.it/~labrob/pub/papers/Ramsete01.pdf" (5.12)
 * Follows a trajectory through indexes and returns motor speeds (ft/s) every tick 
 */
public class BreakerTrajectoryFollower {

	private static final double b    = Constants.Autonomous._tfB;
	private static final double zeta = Constants.Autonomous._tfZeta;
	
	private int i;
	
	private Trajectory trajectory;
	private RobotPosition robotPosition;

	public BreakerTrajectoryFollower(Trajectory trajectory) {
		this.trajectory = trajectory;
		i = 0;
	}
	
	/**
	 * Get the current drive signal (Call Every Loop)
	 * @param robotPosition The Robot's position on the field (get from Odometry.java)
	 * @return The Motor Speeds to follow the trajector (IN FEET PER SECOND!!!)
	 */
	public RobotDriveSignal getNextDriveSignal(RobotPosition robotPosition) {
		this.robotPosition = robotPosition;
		
		double left = 0;
		double right = 0;
		
		if (isFinished())
			return new RobotDriveSignal(left, right);
		

		//Get Current Segment from index
		Segment current = trajectory.get(i);
		
		//Find wanted rate of change of the heading (angle)
		double w_d = calcW_d();

		//Get Linear and Angular Velocities
		double v = calcVel(current.x, current.y, current.heading, current.velocity, w_d);		   //Linear velocity
		double w = calcAngleVel(current.x, current.y, current.heading, current.velocity, w_d);	  //Angular velocity

		//Clamp Angular and Linear Velocities
		//v = clamp(v, -20, 20);
		//w = clamp(w, Math.PI * -2.0, Math.PI * 2.0);

		//Convert Angular and Linear Velocities to into wheel speeds 
		left  = (-Constants._wheelBaseWidth * w) / 2 + v;
		right = (+Constants._wheelBaseWidth * w) / 2 + v;

		//Go to the next index
		i += 1;
	   
		return new RobotDriveSignal(left, right);
	}

	// -- Other -- \\
	/**
	 * Get the starting robot position in a trajectory (should be 0, 0, 0)
	 */
	public RobotPosition getInitRobotPosition() {
		return new RobotPosition(trajectory.get(0).x,trajectory.get(0).y, trajectory.get(0).heading);
	}

	public boolean isFinished() {
		return i == trajectory.length();
	}
	
	// -- Calculations -- \\
	private double calcW_d() {
		if (i < trajectory.length()-1) {
			double lastTheta = trajectory.get(i).heading;
			double nextTheta = trajectory.get(i + 1).heading;
			return (nextTheta - lastTheta) / trajectory.get(i).dt;
		} 
		else {
			return 0;
		}
	}
	private double calcVel(double x_d, double y_d, double theta_d, double v_d, double w_d) {
		double k = calcK(v_d, w_d);
		double thetaError = theta_d - robotPosition.getTheta();
		thetaError = Units.degreesToRadians(BreakerMath.bound180(Units.radiansToDegress(thetaError)));
	   
		return 
				v_d * Math.cos(thetaError) 
				+ k * (Math.cos(robotPosition.getTheta()) * (x_d - robotPosition.x) 
				+ Math.sin(robotPosition.getTheta()) * (y_d - robotPosition.y));
	}
	private double calcAngleVel(double x_d, double y_d, double theta_d, double v_d, double w_d) {
		double k = calcK(v_d, w_d);
		double thetaError = theta_d - robotPosition.getTheta();
		thetaError = Pathfinder.d2r(Pathfinder.boundHalfDegrees(Pathfinder.r2d(thetaError)));
		double sinThetaErrOverThetaErr;
		
		if (Math.abs(thetaError) < 0.00001)
			sinThetaErrOverThetaErr = 1; //A limit when "sin(x)/x" gets close to zero
		else
			sinThetaErrOverThetaErr = Math.sin(thetaError) / (thetaError);
		
		return w_d + b * v_d * (sinThetaErrOverThetaErr) * (Math.cos(robotPosition.getTheta()) * (y_d - robotPosition.y) - Math.sin(robotPosition.getTheta()) * (x_d - robotPosition.x)) + k * (thetaError); //from eq. 5.12
	}
	private double calcK(double v_d, double w_d) {
		return 2 * zeta * Math.sqrt(Math.pow(w_d, 2) + b * Math.pow(v_d, 2));
	}
}