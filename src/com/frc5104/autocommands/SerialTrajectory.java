package com.frc5104.autocommands;

import java.io.Serializable;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

public class SerialTrajectory implements Serializable {
	CerealSegment[] s;
	
	public SerialTrajectory(Trajectory t) {
		Segment[] sa = t.segments;
		s = new CerealSegment[sa.length];
		for (int i = 0; i < sa.length; i++) {
			s[i] = new CerealSegment(sa[i]);
		}
	}
	
	public Trajectory getTrajectory() {
		Segment[] sa = new Segment[s.length];
		for (int i = 0; i < sa.length; i++) {
			sa[i] = s[i].getSeg();
		}
		return new Trajectory(sa);
	} 
	
	public class CerealSegment implements Serializable {
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
