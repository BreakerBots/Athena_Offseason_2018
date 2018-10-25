package frc.team5104.auto;

import java.io.Serializable;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

/**
 * A Cerealizable Pathfinder Trajectory (For Motion Profiling Path Caching)
 */
public class BreakerCacheTrajectory implements Serializable {
	private static final long serialVersionUID = 9023409738013710743L;
	CerealSegment[] s;
	
	/**
	 * Trajectory => SerialTrajectory
	 */
	public BreakerCacheTrajectory(Trajectory t) {
		Segment[] sa = t.segments;
		s = new CerealSegment[sa.length];
		for (int i = 0; i < sa.length; i++) {
			s[i] = new CerealSegment(sa[i]);
		}
	}
	
	/**
	 * SerialTrajectory (this) => Trajectory
	 */
	public Trajectory getTrajectory() {
		Segment[] sa = new Segment[s.length];
		for (int i = 0; i < sa.length; i++) {
			sa[i] = s[i].getSeg();
		}
		return new Trajectory(sa);
	} 
	
	/**
	 * *A flake of a chereo*
	 * A serializeable Pathfinder Segment (Part of a Trajectory)
	 */
	private class CerealSegment implements Serializable {
		private static final long serialVersionUID = 5352736871944902882L;
		/**
		 * Segment => CerealSegment
		 */
		public CerealSegment(Segment s) {
			dt = s.dt;
			x = s.x;
			y = s.y;
			position = s.position;
			velocity = s.velocity;
			acceleration = s.acceleration;
			jerk = s.jerk;
			heading = s.heading;
		}
		
		/**
		 * CerealSegment => Segment
		 */
		public Segment getSeg() {
			return new Segment(
				dt,
				x,
				y,
				position,
				velocity,
				acceleration,
				jerk,
				heading
			);
		}
		
		//Doubles attached to Segment (the basic variabled that get serialized)
		double dt;
		double x;
		double y;
		double position;
		double velocity;
		double acceleration;
		double jerk;
		double heading;
	}
}
