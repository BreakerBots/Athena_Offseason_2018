// Good

package frc.team5104.autopaths;

import frc.team5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class LL extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	9.56, 168, 90
	 */
	
	public LL() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(10, 10, 90)
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}