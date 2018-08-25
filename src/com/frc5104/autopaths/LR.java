package com.frc5104.autopaths;

import com.frc5104.autocommands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public class LR extends CommandGroup {
	public LR() {
		Waypoint[] points = {
			new Waypoint(0, 0, 0),
			new Waypoint(0, 10, 0)
		};
		addSequential(new MotionProfile(points));
    	addParallel(new StopDrive());
	}
}
