//Good 

package frc.team5104.autopaths;

import frc.team5104.autocommands.*;

import jaci.pathfinder.Waypoint;

public class CL extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	-32.5, 108, 0
	 */
	
	public CL() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(10, -7.4, 0)
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
