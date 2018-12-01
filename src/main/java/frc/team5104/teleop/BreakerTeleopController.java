package frc.team5104.teleop;

import frc.team5104.subsystem.climber.*;
import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.subsystem.drive.DriveSystems;
import frc.team5104.subsystem.drive.RobotDriveSignal;
import frc.team5104.subsystem.drive.RobotDriveSignal.DriveUnit;
import frc.team5104.subsystem.elevator.ElevatorActions;
import frc.team5104.subsystem.squeezy.SqueezyActions;
import frc.team5104.util.Curve;
import frc.team5104.util.CurveInterpolator;
import frc.team5104.util.Deadband;
import frc.team5104.util.controller;
import frc.team5104.util.controller.Control;

public class BreakerTeleopController {
	public static final CurveInterpolator vTeleopLeftSpeed  = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	public static final CurveInterpolator vTeleopRightSpeed = new CurveInterpolator(HMI.Drive._driveCurveChange, HMI.Drive._driveCurve);
	
	public static void update() {
		if (HMI.Climbing._ptoShift.getPressed()) {
			ClimberActions.switchPto();
			controller.rumbleHardFor(1, .3);
			SqueezyActions.foldSet(true);
			DriveActions.stop();
		}
		if (ClimberSystems.solenoid.inElevator()) {
			//Squeezy
			if (HMI.Squeezy._foldUp.getPressed() || HMI.Squeezy._foldUpAlt.getPressed())
				SqueezyActions.foldSet(true);
			if (HMI.Squeezy._foldDown.getPressed() || HMI.Squeezy._foldDownAlt.getPressed())
				SqueezyActions.foldSet(false);
			if (HMI.Squeezy._intake.getPressed())
				SqueezyActions.intake();
			if (HMI.Squeezy._eject.getPressed())
				SqueezyActions.eject();
			if (HMI.Squeezy._hold.getPressed())
				SqueezyActions.hold();
			if (HMI.Squeezy._idle.getPressed())
				SqueezyActions.idle();
			//Driving
			double turn = Deadband.get(HMI.Drive._turn.getAxis(), -0.2);
			double forward = Deadband.get(HMI.Drive._forward.getAxis() - HMI.Drive._reverse.getAxis(), 0.1);
			double x1 = (1 - Math.abs(forward)) * (1 - 0.3) + 0.3;
			turn = Curve.getBezierCurve(turn, x1, 0.4, 1, 0.2);
			vTeleopLeftSpeed.setSetpoint(forward - turn);
			vTeleopRightSpeed.setSetpoint(forward + turn);
			//		if (Control.MENU.getHeld()) {
			//			DriveActions.set(new RobotDriveSignal(5, 5, DriveUnit.feetPerSecond), false);
			//		}
			//		else
			DriveActions.set(new RobotDriveSignal(vTeleopLeftSpeed.update(), vTeleopRightSpeed.update(),
					DriveUnit.percentOutput), true);
			if (HMI.Drive._shift.getPressed())
				DriveSystems.shifters.toggle();
			
			//Elevator
			ElevatorActions.setSpeed(Deadband.getReverse(HMI.Elevator._drive.getAxis(), 0.1));
		} else {
			ClimberActions.setSpeed(HMI.Climbing._climb.getAxis() - Control.LT.getAxis());
		}
		
		//Updates
		controller.update();
	}
}
