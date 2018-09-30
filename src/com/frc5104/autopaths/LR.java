package com.frc5104.autopaths;

import com.frc5104.autocommands.*;
import com.frc5104.main.Constants;

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
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0,		0	* Constants.AutonomousWP._xAngleMult, 0 ),
				new Waypoint(16,	0	* Constants.AutonomousWP._xAngleMult, 0 ),
				new Waypoint(50,	0	* Constants.AutonomousWP._xAngleMult, 0 ),
				new Waypoint(10,	10	* Constants.AutonomousWP._xAngleMult, 90),
				new Waypoint(0,		5	* Constants.AutonomousWP._xAngleMult, 90)
				//new Waypoint(18.8,	18.3	* Constants._xAngleMult, 0 ),
				//new Waypoint(20,	20.5	* Constants._xAngleMult, 90)
			}));
		add(new DriveStop());
		add(new Delay(1000));
		add(new SqueezyFold(false));
		add(new Delay(100));
		add(new SqueezyEject());
	}
}
