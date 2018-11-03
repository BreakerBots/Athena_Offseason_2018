package frc.team5104.auto.paths;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto.actions.*;
import jaci.pathfinder.Waypoint;

public class CR extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	32.5, 108, 0
	 */
	

	public CR() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(7, 4, 0)
			}));
		add(new DriveStop());
		add(new SqueezyFold(false));
		add(new Delay(250));
		add(new SqueezyEject());
	}
}
