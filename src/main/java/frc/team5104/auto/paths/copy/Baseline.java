// Good

package frc.team5104.auto.paths.copy;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto.actions.DriveStop;
import frc.team5104.auto.actions.DriveTrajectory;
import jaci.pathfinder.Waypoint;

public class Baseline extends BreakerPath {
	public Baseline() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(6.5, 0, 0)
			}));
		add(new DriveStop());
	}
}
