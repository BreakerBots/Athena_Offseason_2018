package com.frc5104.autopaths;

import com.frc5104.autocommands.*;
import jaci.pathfinder.Waypoint;

public class RL extends BreakerPath {
	public RL() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(0, 10, 0)
			}));
		add(new DriveStop());
	}
}
