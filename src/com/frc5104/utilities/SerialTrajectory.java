package com.frc5104.utilities;

import java.io.Serializable;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

/**
 * A serializeable Pathfinder Trajectory (For Motion Profiling Path Caching)
 */
public class SerialTrajectory implements Serializable {
	CerealSegment[] s;
	
	/**
	 * Trajectory => SerialTrajectory
	 */
	public SerialTrajectory(Trajectory t) {
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
	 * A serializeable Pathfinder Segment (Part of a Trajectory)
	 */
	private class CerealSegment implements Serializable {
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
