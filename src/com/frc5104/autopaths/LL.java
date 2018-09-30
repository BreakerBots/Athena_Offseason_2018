package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class LL extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	9.56, 168, 90
	 */
	
	public LL() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(22, 2, 45)
				//measured at (14, 1, 90)
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
