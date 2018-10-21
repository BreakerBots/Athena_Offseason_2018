package frc.team5104.auto.paths;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto.actions.*;
import jaci.pathfinder.Waypoint;

public class RL extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	0, 180, 0 (Spacer)
	 * 	0, 196, 0 (Begin Turn around Switch)
	 * 	-57, 226, -90 (End Turn around Switch)
	 * 	-226, 226, 0 (Begin Turn to Eject at Switch)
	 * 	-247.75, 241, -90 (End Turn to Eject at Switch)
	 */
	
	public RL() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(0, 10, 0)
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
