package com.frc5104.autopaths;

import com.frc5104.autocommands.*;
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
				new Waypoint(50/6.0, 0, 0),
				new Waypoint(150/6.0, 0, 0),
				new Waypoint(200/6.0, 226/12.0, 90),
				//new Waypoint(241/12.0, 250/12.0, 90),
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
