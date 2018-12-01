//Good 

package frc.team5104.auto.paths;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto._AutoConstants;
import frc.team5104.auto.actions.*;
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
				new Waypoint(7, -5, 0)
			}));
		add(new DriveStop());
		add(new SqueezyFold(false));
		add(new Delay(_AutoConstants._ejectDelay));
		add(new SqueezyEject());
	}
}
