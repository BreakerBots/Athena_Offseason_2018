package com.frc5104.math;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.frc5104.main.Constants;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/*Breakerbots Robotics Team 2018*/
/**
 * Generates and Caches Trajectory
 * Uses Jaci's Pathfinder for the Trajectory Generation
 * Caches Trajectories by a hashing of there waypoints
 */
public class BreakerTrajectoryGenerator {
	//Pathfinder Config
	private static Trajectory.Config config = new Trajectory.Config(
			Constants.Autonomous._fitMethod, 
			Constants.Autonomous._samples, 
			1.0 / Constants.Autonomous._dt, 
			Constants.Autonomous._maxVelocity, 
			Constants.Autonomous._maxAcceleration, 
			Constants.Autonomous._maxJerk
	);
	
	/**
	 * Will either return a cached version of a Trajectory under those points (~500ms)
	 * or will Generate a Trajectory a cache it (~5000ms - ~15000ms)
	 * @param points Waypoints to generate the trajectory from
	 * @return A Trajectory to follow those waypoints
	 */
	public static Trajectory getTrajectory(Waypoint[] points) {
		try {
			//Parse trajectory name
			String s = "";
	    	for (Waypoint p : points) {
	    		s += (Double.toString(p.x) + "/" + Double.toString(p.y) + "/" + Double.toString(p.angle));
	    	}
	    	s = "_" + s.hashCode();
	    	
	    	//Read file
	    	console.log(c.AUTO, "Looking for Similar Cached Trajectory Under " + s);
	    	Trajectory t = readFile(s);
	    	
	    	//If the file does not exist, generate a path and save
	    	if (t == null) {
	    		console.log(c.AUTO, "No Similar Cached Trajectory Found => Generating Path");
	    		console.sets.create("MPGEN");
	    		t = (Trajectory) Pathfinder.generate(points, config);
	    		writeFile(s, t);
	    		console.log(c.AUTO, "Trajectory Generation Took " + console.sets.getTime("MPGEN") + "s");
	    	}
	    	return t;
		}
		catch (Exception e) {
			console.error(e);
			return null;
		}
	}
	
	/**
	 * (!) Deprication Notice
	 */
	public static TankModifier getTankModifier(Trajectory t) {
		TankModifier m = new TankModifier(t);
		m.modify(Constants._wheelBaseWidth);
		return m;
	}
	
	/**
	 * Finds, reads, and returns the trajectory saved with name
	 */
	private static Trajectory readFile(String name) {
		try {
			File file = new File("/home/lvuser/MotionProfilingCache/" + name);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Trajectory t = ((BreakerCacheTrajectory) ois.readObject()).getTrajectory();
			ois.close();
			fis.close();
			return t;
		}
		catch (Exception e) {
			//Expected to have errors => no print
			return null;
		}
	}
	
	/**
	 * Writes the path to a new file
	 */
	private static void writeFile(String name, Trajectory t) {
		try {
			FileOutputStream fos = new FileOutputStream("/home/lvuser/MotionProfilingCache/" + name);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(new BreakerCacheTrajectory(t));
		    oos.close();
		    fos.close();
		}
		catch (Exception e) {
			console.error(e);
		}
	}
}
