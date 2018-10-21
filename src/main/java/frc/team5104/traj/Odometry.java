package frc.team5104.traj;

import frc.team5104.main.Constants;
import frc.team5104.main.subsystems.Drive;
import frc.team5104.util.BreakerMath;
import frc.team5104.util.console;
import frc.team5104.main.Units;

import edu.wpi.first.wpilibj.Notifier;

/*Breakerbots Robotics Team 2018*/
/**
 * <h1>Odometry (Robot Position Estimator/Kinematics)</h1>
 * Calculates the Robots x, y position according to encoder values.
 */
public class Odometry {
	private static Notifier _thread = null;
		
	private volatile static double lastPos, currentPos, dPos, theta;
	public volatile static RobotPosition position = new RobotPosition(0, 0, 0);
	
	private static void init() {
		lastPos = currentPos = (Drive.encoders.getLeft() + Drive.encoders.getRight()) / 2;
		_thread = new Notifier(() -> {
			currentPos = (Drive.encoders.getLeft() + Drive.encoders.getRight()) / 2;
			dPos = Units.ticksToFeet(currentPos - lastPos);
			lastPos = currentPos;
			theta = Units.degreesToRadians(BreakerMath.bound180(Drive.gyro.getAngle()));
            position.set(
        		position.x + Math.cos(theta) * dPos, 
        		position.y + Math.sin(theta) * dPos, 
        		theta
            );
        });
	}
	
	public static void run() {
		if (_thread == null)
			init();
		
		_thread.startPeriodic(1.0 / Constants.Loops._odometryHz);
	}
	
	public static void stop() {
		if (_thread != null)
			_thread.stop();
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
