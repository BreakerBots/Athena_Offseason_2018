package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/*Breakerbots Robotics Team 2018*/
public class MotionProfile extends Command {

	//Tuning Varibles
	double _PIDA[] = { 5.0, 0.0, 0.0, 0 }; // Tuning Variables for the Robot
	double _maxVelocity = /*6.5*//*4.9*/2.2; // The Max Velocity of Your Robot
	double _maxAcceleration = /*6.5*//*0.5*/3.0; // The Max Acc of Your Robot
	double _maxJerk = /*196.8*/100; // The Max Jerk of Your Robot
	double _angleMult = 0.8; /*Keep from 0.6 - 0.8 MULTIPLYING*/
	
	//Robot Varibles (In Metric)
	/*Athena has a slope of 56d*/
	double _wheelDiameter = /*0.1524*/6; // The Diameter of the Wheel in Meters
	double _ticksPerRevolution = 4698.25181344; //How Many Encoder Ticks it Takes To Make A Full Wheel Rotation
	double _wheelBaseWidth = /*0.5588*/26.15; //The Distance from the Left and Right Wheels in Meters
	
	//Motion Profiling Objects
	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, _maxVelocity, _maxAcceleration, _maxJerk);
	Trajectory trajectory;
	TankModifier modifier;
	EncoderFollower left;
	EncoderFollower right;
	
	ADXRS450_Gyro Gyro = new ADXRS450_Gyro();
	
    public MotionProfile(Waypoint[] points) {
    	trajectory = Pathfinder.generate(points, config);
    }

    protected void initialize() {
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

    protected void execute() {
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

    protected boolean isFinished() {
    	return left.isFinished() && right.isFinished();
    }

    protected void end() {

    }

    protected void interrupted() {

    }
    
    public double clamp(double a, double min, double max) {
    	return a < min ? min : (a > max ? max : a);
    }
}
