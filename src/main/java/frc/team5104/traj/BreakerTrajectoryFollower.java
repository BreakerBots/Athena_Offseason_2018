package frc.team5104.traj;

import frc.team5104.main.Constants;
import frc.team5104.traj.RobotDriveSignal.DriveUnit;
import jaci.pathfinder.Trajectory;

/*Breakerbots Robotics Team 2018*/
/**
 * Pathfinder Trajectory Follower (Ramsete Follower)
 * <br> Based on the <a href="https://www.dis.uniroma1.it/~labrob/pub/papers/Ramsete01.pdf">Ramsete Follower</a> ... <a href="https://github.com/TeamSteamRobotics/Competition2018/blob/feature/ramsete/src/main/java/frc/team5119/robot/autonomous/RamseteFollower.java">Example</a>
 * <br> Follows a trajectory through indexes and returns motor speeds (ft/s) every tick 
 */
public class BreakerTrajectoryFollower {
	private Trajectory trajectory;
	private RobotPosition robotPosition;
	private int segment = 0;
	private double v, w, k1, k3, e_x, e_y, e_theta, v_d, w_d, w_L, w_R;
	private static final double b    = Constants.Autonomous._tfB,
								zeta = Constants.Autonomous._tfZeta,
								k2 = b;

	public BreakerTrajectoryFollower(Trajectory trajectory) {
		this.trajectory = trajectory;
		segment = 0;
	}
	
	/**
	 * Get the current drive signal (Call Every Loop)
	 * @param robotPosition The Robot's position on the field (get from Odometry.java)
	 * @return The Motor Speeds to follow the trajector (IN FEET PER SECOND!!!)
	 */
	public RobotDriveSignal getNextDriveSignal(RobotPosition currentRobotPosition) {
		robotPosition = currentRobotPosition;

        v_d = trajectory.get(segment).velocity;
        w_d = (trajectory.get(segment+1).heading - trajectory.get(segment).heading)/trajectory.get(segment).dt;
        k1 = 2 * zeta * Math.sqrt(Math.pow(w_d, 2) + b * Math.pow(v_d, 2));
        k3 = k1;

        e_x = Math.cos(robotPosition.getTheta()) * (trajectory.get(segment).x - robotPosition.x) + Math.sin(robotPosition.getTheta()) * (trajectory.get(segment).y - robotPosition.y);
        e_y = Math.cos(robotPosition.getTheta()) * (trajectory.get(segment).y - robotPosition.y) - Math.sin(robotPosition.getTheta()) * (trajectory.get(segment).x - robotPosition.x);
        e_theta = trajectory.get(segment).heading - robotPosition.getTheta();

        v = v_d * Math.cos(e_theta) + k1 * e_x;
        w = w_d + k2 * sinE_thetaOverE_theta() * e_y + k3 * e_theta;

        w_L = (Constants._wheelBaseWidth * w - 2 * v) / -Constants._wheelDiameter;
        w_R = (Constants._wheelBaseWidth * w + 2 * v) /  Constants._wheelDiameter;

        return new RobotDriveSignal(w_L, w_R, DriveUnit.feetPerSecond);
	}

	public boolean isFinished() {
        return segment >= trajectory.length();
    }
	
	private double sinE_thetaOverE_theta() {
        if (e_theta < 0.0000001) {
            return 1.0;
        } else {
            return Math.sin(e_theta)/e_theta;
        }
    }
	
	/**
	 * Get the starting robot position in a trajectory (should be 0, 0, 0)
	 */
	public RobotPosition getInitRobotPosition() {
		return new RobotPosition(trajectory.get(0).x,trajectory.get(0).y, trajectory.get(0).heading);
	}
}