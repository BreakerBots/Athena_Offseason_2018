package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class Baseline extends BreakerPath {
	public Baseline() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(10, 0, 0)
			}));
		add(new DriveStop());
	}
}
