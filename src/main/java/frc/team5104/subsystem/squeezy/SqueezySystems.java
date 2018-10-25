package frc.team5104.subsystem.squeezy;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team5104.main.Devices;
import frc.team5104.subsystem.BreakerSubsystem;

class SqueezySystems extends BreakerSubsystem.Systems {
	private static TalonSRX dArms       = Devices.Squeezy.squeeze;
	private static TalonSRX dLWheel     = Devices.Squeezy.leftSpin;
	private static TalonSRX dRWheel     = Devices.Squeezy.rightSpin;
	private static DoubleSolenoid dFold = Devices.Squeezy.fold; 
	
	static class wheels {
		static void intake() {
			dLWheel.set(ControlMode.PercentOutput, -_SqueezyConstants._wheelIntakeSpeed);
			dRWheel.set(ControlMode.PercentOutput,  _SqueezyConstants._wheelIntakeSpeed);
		}
		
		static void eject(double speed) {
			dLWheel.set(ControlMode.PercentOutput, -speed);
			dRWheel.set(ControlMode.PercentOutput,  speed);
		}
		
		static void idle() {
			dLWheel.set(ControlMode.PercentOutput, 0);
			dRWheel.set(ControlMode.PercentOutput, 0);
		}
		
		static void hold() {
			dLWheel.set(ControlMode.PercentOutput, -_SqueezyConstants._wheelHoldSpeed);
			dRWheel.set(ControlMode.PercentOutput,  _SqueezyConstants._wheelHoldSpeed);
		}
	}
	
	static class fold {
		static void up() {
			dFold.set(DoubleSolenoid.Value.kReverse);
		}
		
		static void down() {
			dFold.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	static class arms {	
		static void set(double speed) {
			dArms.set(ControlMode.PercentOutput, speed);
		}
		
		static boolean hitInsideLimitSwitch() {
			return dArms.getSensorCollection().isRevLimitSwitchClosed();
		}
		
		static boolean hitOutsideLimitSwitch() {
			return dArms.getSensorCollection().isFwdLimitSwitchClosed();
		}
		
		static boolean isPhysicallyStopped() {
			return 
					dArms.getOutputCurrent() > 
					(NetworkTableInstance.
					getDefault().
					getTable("Autonomous").
					getEntry("SqueezyCurrent").
					getDouble(_SqueezyConstants._armsPhysicallyStoppedCurrent));
		}
	}
}
