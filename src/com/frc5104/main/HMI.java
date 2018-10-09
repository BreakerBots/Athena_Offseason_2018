package com.frc5104.main;

import com.frc5104.utilities.Curve;
import com.frc5104.utilities.controller;
import com.frc5104.utilities.controller.Control;

/*Breakerbots Robotics Team 2018*/
/**
 * All Controls used in Athena's Code
 */
public class HMI {

	//Drive
	public static class Drive {
		public static double driveX() {
			return controller.getAxis(Control.LX);
		}
		
		public static final boolean _usingTriggerDrive = true;
		public static double driveY() {
			if (_usingTriggerDrive) return controller.getAxis(Control.LT) - controller.getAxis(Control.RT);
			else return controller.getAxis(Control.LY);
		}
		
		public static final Control _shift = Control.LJ;
		
		public static final double _deadbandX = -0.2;
		public static final double _deadbandY = 0.1;
		public static final Curve.BezierCurve _turnCurve = new Curve.BezierCurve(0.9, 0.15, 1, 0.2);
		
		public static final Curve.BezierCurve _driveCurve = new Curve.BezierCurve(.2, 0, .2, 1);
		public static final double _driveCurveChange = 0.08;
	}
	
	//Elevator
	public static class Elevator {
		public static final Control _drive = Control.RY; //Right Stick
	}
	
	//Climbing
	public static class Climbing {
		public static final Control _ptoShift = Control.Y;
		public static final Control _openHookHolder = Control.Y;
	}
	
	//Squeezy
	public static class Squeezy {
		public static final Control _foldUp   = Control.A;
		public static final Control _foldDown = Control.Y;

		public static final Control _eject    = Control.LB;
		public static final Control _intake   = Control.B;
		public static final Control _hold     = Control.X;
		public static final Control _idle     = Control.LIST;
	}
}
