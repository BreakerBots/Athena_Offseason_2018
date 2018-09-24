package com.frc5104.autopaths;

import com.frc5104.autocommands.*;
import com.frc5104.main.Constants;

import jaci.pathfinder.Waypoint;

public class LR extends BreakerPath {
	public LR() {
		add(new DriveTrajectoryWP(new Waypoint[] {
				new Waypoint(0,		0	* Constants.AutonomousWP._xAngleMult, 0 ),
				new Waypoint(16,	0	* Constants.AutonomousWP._xAngleMult, 0 ),
				new Waypoint(54,	0	* Constants.AutonomousWP._xAngleMult, 0 ),
				new Waypoint(10,	10	* Constants.AutonomousWP._xAngleMult, 90),
				//new Waypoint(18.8,	18.3	* Constants._xAngleMult, 0 ),
				//new Waypoint(20,	20.5	* Constants._xAngleMult, 90)
			}));
		add(new DriveStop());
	}
}
