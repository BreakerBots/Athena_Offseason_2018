package com.frc5104.autopaths;

import com.frc5104.autocommands.*;
import com.frc5104.main.Constants;

import jaci.pathfinder.Waypoint;

public class CR extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	32.5, 108, 0
	 */
	
	public CR() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(15, 4.0 * Constants.AutonomousWP._xAngleMult, 0)
					//measured at (9, 2.7, 0)
			}));
		add(new DriveStop());
	}
}
