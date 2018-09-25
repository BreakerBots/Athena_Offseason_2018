package com.frc5104.autopaths;

import com.frc5104.autocommands.*;
import com.frc5104.main.Constants;

import jaci.pathfinder.Waypoint;

public class CL extends BreakerPath {
	public CL() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(12, -5.0 * Constants.AutonomousWP._xAngleMult, 0)
					//measured at (9, 2.7, 0)
			}));
		add(new DriveStop());
	}
}
