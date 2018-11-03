package frc.team5104.auto.paths;

import frc.team5104.auto.BreakerPath;
import frc.team5104.auto.actions.*;
import jaci.pathfinder.Waypoint;

public class LR extends BreakerPath {
	/*
	 * Measured Points [x (in), y (in), angle (deg)]:
	 * 	0, 0, 0 (Base)
	 * 	0, 180, 0 (Spacer)
	 * 	0, 196, 0 (Begin Turn around Switch)
	 * 	57, 226, 90 (End Turn around Switch)
	 * 	226, 226, 0 (Begin Turn to Eject at Switch)
	 * 	247.75, 241, 90 (End Turn to Eject at Switch)
	 */
	
	public LR() {
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(180/12.0, 0, 0),
				new Waypoint(190/12.0, 0, 0),
				new Waypoint(225/12.0, 57/12.0, 90),
				new Waypoint(225/12.0, 145/12.0, 90),
			}));
		add(new DriveTrajectory(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(12/12.0, 12.0/12.0, 70),
		}));
		add(new DriveStop());
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
