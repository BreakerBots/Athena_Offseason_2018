// Good

package frc.team5104.auto.paths;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto.actions.*;
import jaci.pathfinder.Waypoint;

public class LL extends BreakerPath {
	public LL() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(7.5, 0.5, 10)
			}));
		add(new DriveStop());
		add(new SqueezyFold(false));
		add(new Delay(250));
		add(new SqueezyEject());
	}
}
