package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.frc5104.pathfinder.Waypoint;

public class LL extends BreakerCommandGroup {
	Waypoint[] points = {
		new Waypoint(0, 0, 0),
		new Waypoint(0, 10, 0)
	};
	
	public void init() {
		add(new MotionProfile(points));
		add(new StopDrive());
	}
}
