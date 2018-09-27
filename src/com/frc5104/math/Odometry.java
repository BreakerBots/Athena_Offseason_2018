package com.frc5104.math;

import com.frc5104.main.Constants;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.utilities.console;
import com.frc5104.main.Units;

import edu.wpi.first.wpilibj.Notifier;

/*Breakerbots Robotics Team 2018*/
public class Odometry {
	private static Notifier m_notifier = null;
		
	private static double lastPos, currentPos, dPos;
	private volatile static double x, y, theta;
	public static RobotPosition position = new RobotPosition(0, 0, 0);
	
	private static void init() {
		lastPos = currentPos = (Drive.encoders.getLeft() + Drive.encoders.getRight())/2;
		m_notifier = new Notifier(() -> {
			currentPos = (Drive.encoders.getLeft() + Drive.encoders.getRight())/2;
            dPos = Units.ticksToFeet(currentPos - lastPos);
            theta = Units.degreesToRadians(BreakerMath.bound180(Drive.Gyro.getAngle()));
			x +=  Math.cos(theta) * dPos;
            y +=  Math.sin(theta) * dPos;
            lastPos = currentPos;
            position.set(x, y, theta);
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
	
	public static void setPosition(RobotPosition robotPosition) {
		position = robotPosition;
	}
}
