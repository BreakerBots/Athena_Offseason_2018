package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class RR extends BreakerPath {
	public RR() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(22, -2, -45)
			}));
		add(new DriveStop());
	}
}
