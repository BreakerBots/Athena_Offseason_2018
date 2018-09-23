package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class LL extends BreakerCommandGroup {
	Waypoint[] points = {
		new Waypoint(0, 0, 0),
		new Waypoint(22, 2, 45)
	};
	
	public void init() {
		add(new DriveTrajectoryWP(points));
		add(new DriveStop());
	}
}
