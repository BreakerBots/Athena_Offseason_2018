package com.frc5104.autocommands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.frc5104.main.Constants;
import com.frc5104.main.Devices;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.utilities.SerialTrajectory;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/*Breakerbots Robotics Team 2018*/
public class MotionProfile implements BreakerCommand {

	//Motion Profiling Objects
	static Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, Constants._maxVelocity, Constants._maxAcceleration, Constants._maxJerk);
	static Trajectory trajectory;
	TankModifier modifier;
	EncoderFollower left;
	EncoderFollower right;
		
    public MotionProfile(Waypoint[] points) {
    	trajectory = getTrajectory(points);
    }

    public void initialize() {
    	console.log("Running MP Path", Type.AUTO);
    	Devices.Drive.Gyro.reset();
		
		//Modify The Tank To The Wheel Base Width, The Distance Between The Left and Right Wheels
		modifier = new TankModifier(trajectory);
		modifier.modify(Constants._wheelBaseWidth);
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
		
		Drive.encoders.reset(10);
		
		//Configure Tank Drive
		left.configureEncoder(0, (int) Math.round(Constants._ticksPerRevolution), Constants._wheelDiameter);
		left.configurePIDVA(Constants._PIDA[0], Constants._PIDA[1], Constants._PIDA[2], 1 / Constants._maxVelocity, Constants._PIDA[3]);
		right.configureEncoder(0, (int) Math.round(Constants._ticksPerRevolution), Constants._wheelDiameter);
		right.configurePIDVA(Constants._PIDA[0], Constants._PIDA[1], Constants._PIDA[2], 1 / Constants._maxVelocity, Constants._PIDA[3]);
		
		//Just in case wait a 100ms for everything to catch up
		try {
			Thread.sleep(100);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public void execute() {
    	//Check Teleop Periodic For Reference on What Needs To Be Flipped
		int leftEncoder = Drive.encoders.getLeft();
		int rightEncoder = Drive.encoders.getRight();
		double angle = -Devices.Drive.Gyro.getAngle() / Math.cos(Pathfinder.d2r(Constants._gyroAngle));
		
		//Calculate Left and Right Speed for Wheels
		double l = left.calculate(-leftEncoder);
		double r = right.calculate(-rightEncoder);

		//Calculate Angle
		double gyro_heading = angle;
		double desired_heading = Pathfinder.r2d(left.getHeading());

		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		double a = Pathfinder.boundHalfDegrees((-1.0/80.0) * angleDifference * Constants._angleMult);
		
		//Clamp All Varibles
		a = clamp(a, -1, 1);
		l = clamp(l, -1, 1);
		r = clamp(r, -1, 1);
		
		//Move The Wheels
		Drive.getInstance().set(
				-l + a, 
				-r - a
		);
    }

    public boolean isFinished() {
    	return left.isFinished() && right.isFinished();
    }
    
    public void end() {
    	
    }

    public double clamp(double a, double min, double max) {
    	return a < min ? min : (a > max ? max : a);
    }
    
    public static Trajectory getTrajectory(Waypoint[] points) {
		//Parse trajectory name
		String s = "";
    	for (Waypoint p : points) {
    		s += (Double.toString(p.x) + "/" + Double.toString(p.y) + "/" + Double.toString(p.angle));
    	}
    	s = "_" + s.hashCode();
    	
    	//Read file
    	console.log("Looking for MP Cache Under =>" + s, Type.AUTO);
    	Trajectory t = readFile(s);
    	
    	//If the file does not exist, generate a path and save
    	if (t == null) {
    		console.log("No MP Cache Found => Generating Path", Type.AUTO);
    		console.sets.create("MPGEN");
    		t = (Trajectory) Pathfinder.generate(points, config);
    		writeFile(s, t);
    		console.log("MP Path Generation Took " + console.sets.getTime("MPGEN") + "s", Type.AUTO);
    	}
    	return t;
	}
	
	//Finds, reads, and returns the trajectory saved with name
	public static Trajectory readFile(String name) {
		try {
			File file = new File("/home/lvuser/MotionProfilingCache/" + name);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Trajectory t = ((SerialTrajectory) ois.readObject()).getTrajectory();
			ois.close();
			fis.close();
			return t;
		}
		catch (Exception e) {
			//Expected to have errors => no print
			return null;
		}
	}
	
	//Writes the path to a new file
	public static void writeFile(String name, Trajectory t) {
		try {
			FileOutputStream fos = new FileOutputStream("/home/lvuser/MotionProfilingCache/" + name);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(new SerialTrajectory(t));
		    oos.close();
		    fos.close();
		}
		catch (Exception e) {
			console.error(e);
		}
	}
}
