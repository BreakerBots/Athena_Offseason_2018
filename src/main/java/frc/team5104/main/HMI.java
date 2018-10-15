package frc.team5104.main;

import frc.team5104.util.Curve;
import frc.team5104.util.Deadband;
import frc.team5104.util.controller.Control;

/*Breakerbots Robotics Team 2018*/
/**
 * All Controls used in Athena's Code
 */
public class HMI {

	//Drive
	public static class Drive {
		public static double getTurn() {
			return Deadband.get(Control.LX.getAxis(), -0.2);
		}
		public static double applyTurnCurve(double turn, double forward) {
			double x1 = (1 - Math.abs(forward)) * (1 - 0.3) + 0.3;
			return Curve.getBezierCurve(turn, x1, 0.4, 1, 0.2);
		}
		
		public static double getForward() {
			return Deadband.get(Control.LT.getAxis() - Control.RT.getAxis(), 0.1);
		}
		public static final Control _shift = Control.LJ;
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
