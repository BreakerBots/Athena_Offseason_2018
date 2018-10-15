//Good

package frc.team5104.autopaths;

import frc.team5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class RR extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	-9.56, 168, -90
	 */
	
	public RR() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(10, -1, -45)
					//measured at (14, -1, -90)
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
