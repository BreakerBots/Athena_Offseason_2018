package com.frc5104.traj;

import com.frc5104.main.Constants;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.utilities.BreakerMath;
import com.frc5104.utilities.console;
import com.frc5104.main.Units;

import edu.wpi.first.wpilibj.Notifier;

/*Breakerbots Robotics Team 2018*/
public class Odometry {
	private static Notifier m_notifier = null;
		
	private volatile static double lastPos, currentPos, dPos, theta;
	public volatile static RobotPosition position = new RobotPosition(0, 0, 0);
	
	private static void init() {
		lastPos = currentPos = (Drive.encoders.getLeft() + Drive.encoders.getRight())/2;
		m_notifier = new Notifier(() -> {
			currentPos = (Drive.encoders.getLeft() + Drive.encoders.getRight())/2;
			dPos = Units.ticksToFeet(currentPos - lastPos);
			lastPos = currentPos;
			theta = Units.degreesToRadians(BreakerMath.bound180(Drive.gyro.getAngle() * 1));
            position.set(
        		position.x + Math.cos(theta) * dPos, 
        		position.y + Math.sin(theta) * dPos, 
        		theta
            );
            //console.log(position.toString());
        });
	}
	
	public static void run() {
		if (m_notifier == null)
			init();
		
		m_notifier.startPeriodic(1.0 / Constants.Loops._odometryHz);
	}
	
	public static void stop() {
		if (m_notifier != null)
			m_notifier.stop();
	}
	
	public static RobotPosition getPosition() {
		return position;
	}
	
	public static void reset() {
		console.log("Resetting Odometry");
		
		Drive.gyro.reset();
		Drive.encoders.reset(10);
		
		try { Thread.sleep(10); } catch (Exception e) {}
		
		lastPos = 0; 
		currentPos = 0; 
		dPos = 0; 
		theta = 0;
		position = new RobotPosition(0, 0, 0);
		lastPos = 0;
		init();
		
		console.log("Finished Resetting Odometry at " + getPosition().toString());
	}
}
