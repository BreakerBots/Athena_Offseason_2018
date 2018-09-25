package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class LL extends BreakerPath {
	public LL() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(22, 2, 45)
					//measured at (14, 1, 90)
			}));
		add(new DriveStop());
	}
}
