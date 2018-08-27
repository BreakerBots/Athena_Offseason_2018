package com.frc5104.autocommands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.frc5104.main.subsystems.Drive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.command.Command;
import com.frc5104.pathfinder.Pathfinder;
import com.frc5104.pathfinder.Trajectory;
import com.frc5104.pathfinder.Waypoint;
import com.frc5104.pathfinder.followers.EncoderFollower;
import com.frc5104.pathfinder.modifiers.TankModifier;
=======
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
>>>>>>> 18fad26ef3f74c5e45a7d2bd93113eec6619f508

/*Breakerbots Robotics Team 2018*/
public class MotionProfile implements BreakerCommand {

	//Tuning Varibles
	double _PIDA[] = { 5.0, 0.0, 0.0, 0 }; // Tuning Variables for the Robot
	static double _maxVelocity = /*6.5*//*4.9*/2.2; // The Max Velocity of Your Robot
	static double _maxAcceleration = /*6.5*//*0.5*/3.0; // The Max Acc of Your Robot
	static double _maxJerk = /*196.8*/100; // The Max Jerk of Your Robot
	double _angleMult = 0.8; /*Keep from 0.6 - 0.8 MULTIPLYING*/
	
	//Robot Varibles (In Metric)
	/*Athena has a slope of 56d*/
	double _wheelDiameter = /*0.1524*/6; // The Diameter of the Wheel in Meters
	double _ticksPerRevolution = 4698.25181344; //How Many Encoder Ticks it Takes To Make A Full Wheel Rotation
	double _wheelBaseWidth = /*0.5588*/26.15; //The Distance from the Left and Right Wheels in Meters
	
	//Motion Profiling Objects
	static Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, _maxVelocity, _maxAcceleration, _maxJerk);
	static Trajectory trajectory;
	TankModifier modifier;
	EncoderFollower left;
	EncoderFollower right;
	
	ADXRS450_Gyro Gyro = new ADXRS450_Gyro();
	
	public static Trajectory getTrajectory(Waypoint[] points) {
		//Parse trajectory name
		String s = "";
    	for (Waypoint p : points) {
    		s += (Double.toString(p.x) + "/" + Double.toString(p.y) + "/" + Double.toString(p.angle));
    	}
    	s = "_" + s.hashCode();
    	
    	//Read file
    	Trajectory t = readFile(s);
    	
    	//If the file does not exist, generate a path and save
    	if (t == null) {
    		System.out.println("AUTO: Generating Path");
    		t = Pathfinder.generate(points, config);
    		writeFile(s, t);
    	}
    	return t;
	}
	
	//Finds, reads, and returns the trajectory saved with name
	public static Trajectory readFile(String name) {
		try {
			System.out.println("Reading /home/lvuser/MotionProfilingCache/" + name);
			File file = new File("/home/lvuser/MotionProfilingCache/" + name);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Trajectory t = (Trajectory) ois.readObject();
			return t;
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//Writes the path to a new file
	public static void writeFile(String name, Trajectory t) {
		try {
			FileOutputStream fos = new FileOutputStream("/home/lvuser/MotionProfilingCache/" + name);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(t);
		    oos.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
		
    public MotionProfile(Waypoint[] points) {
    	trajectory = getTrajectory(points);
    }

    public void initialize() {
    	System.out.println("AUTO: Running Path");
    	Gyro.reset();
		
		//Modify The Tank To The Wheel Base Width, The Distance Between The Left and Right Wheels
		modifier = new TankModifier(trajectory);
		modifier.modify(_wheelBaseWidth);
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
		
		Drive.getInstance().resetEncoders(10);
		
		//Configure Tank Drive
		left.configureEncoder(0, (int) Math.round(_ticksPerRevolution), _wheelDiameter);
		left.configurePIDVA(_PIDA[0], _PIDA[1], _PIDA[2], 1 / _maxVelocity, _PIDA[3]);
		right.configureEncoder(0, (int) Math.round(_ticksPerRevolution), _wheelDiameter);
		right.configurePIDVA(_PIDA[0], _PIDA[1], _PIDA[2], 1 / _maxVelocity, _PIDA[3]);
		
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
		int leftEncoder = Drive.getInstance().getLeftEncoder();
		int rightEncoder = Drive.getInstance().getRightEncoder();
		double angle = -Gyro.getAngle() / Math.cos(Pathfinder.d2r(65));
		
		//Calculate Left and Right Speed for Wheels
		double l = left.calculate(-leftEncoder);
		double r = right.calculate(-rightEncoder);

		//Calculate Angle
		double gyro_heading = angle;
		double desired_heading = Pathfinder.r2d(left.getHeading());

		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		double a = Pathfinder.boundHalfDegrees((-1.0/80.0) * angleDifference * _angleMult);
		
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
}
