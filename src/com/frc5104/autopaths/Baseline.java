// Good

package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class Baseline extends BreakerPath {
	public Baseline() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(9.5, 0, 0)
			}));
		add(new DriveStop());
	}
}
