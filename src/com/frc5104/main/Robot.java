package com.frc5104.main;

import com.frc5104.autopaths.AutoSelector;
import com.frc5104.main.subsystems.Drive;
import com.frc5104.main.subsystems.Elevator;
import com.frc5104.main.subsystems.Shifters;
import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.main.subsystems.SqueezySensors;
import com.frc5104.utilities.ControllerHandler;
import com.frc5104.utilities.HMI;
import com.frc5104.utilities.TalonFactory;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/*Breakerbots Robotics Team 2018*/
public class Robot extends IterativeRobot {
	
	int[] talonIDs = new int[] {
			 11, 12, 13, 14 //Drive
			,21, 22, 23     //Squeezy
			,31, 32         //Elevator
	};
	TalonFactory talonFactory = new TalonFactory(talonIDs);

	CommandGroup auto;

	Drive drive = Drive.getInstance();
	Shifters shifters = Shifters.getInstance();
	
	Squeezy squeezy = Squeezy.getInstance();
	SqueezySensors squeezySensors = SqueezySensors.getInstance();
	
	Elevator elevator = Elevator.getInstance();
	
	//Solenoid ptoSol = new Solenoid(4);
	//Servo hookHolder = new Servo(0);
	//double hookStartingPosition = 0;

	public DoubleSolenoid squeezyUpDown = new DoubleSolenoid(0, 1);
	
	ControllerHandler controller = ControllerHandler.getInstance();
	
	public void robotInit() {
		System.out.println("MAIN: Running Code");
		
		if (squeezy != null)
			squeezy.initTable(null);
		
		if (elevator != null)
			elevator.initTable(null);
		
		//hookHolder.setPosition(0.2);

		squeezyUpDown.set(DoubleSolenoid.Value.kReverse);
		
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void autonomousInit() {
		squeezy.forceState(SqueezyState.HOLDING);
		squeezyUpDown.set(Value.kReverse);
		
		auto = AutoSelector.getAuto();
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(auto);
	}
	
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		squeezy.update();
	}
	
	public void teleopInit() {
		if (shifters != null)
			shifters.shiftLow();
	}
	
	public void teleopPeriodic() {
		controller.update();
		
		//Drive
		drive.update();
		
		/*climbing
		if (controller.getHeldEvent(HMI.kPtoHoldAndHookPressButton, 0.4)) { 
			System.out.println("TELE: Switching PTO!");
			ptoSol.set(!ptoSol.get());
			//if (ptoSol.get())
			//	controller.rumbleSoftFor(0.5, 0.2);
			//else
			//	controller.rumbleHardFor(1, 0.2);
		}
		if (!ptoSol.get()) {
			controller.rumbleHardFor(0.7, 0.2);
		}
		if (controller.getPressed(HMI.kOpenHookHolder)) {
			hookHolder.setPosition(1 - hookHolder.getPosition());
		}
		*/
		
		//Shifters
		if (controller.getAxis(HMI.kDriveShift) > 0.6)
			shifters.shiftHigh();
		else
			shifters.shiftLow();
		
		//Elevator
		if (elevator != null) {
			elevator.userControl();
		}
		
		//Squeezy
		if (controller.getPressed(HMI.kSqueezyDown)) {
			System.out.println("TELE: Squeezy down");
			squeezyUpDown.set(DoubleSolenoid.Value.kForward);
		}
		if (controller.getPressed(HMI.kSqueezyUp)) {
			System.out.println("TELE: Squeezy up");
			squeezyUpDown.set(DoubleSolenoid.Value.kReverse);
		}
		if (squeezy != null) {
			squeezy.updateState();
			squeezy.update(squeezyUpDown.get() == DoubleSolenoid.Value.kReverse);
		}
	}
	
	public void robotPeriodic() {
		squeezySensors.updateSensors();
		
		squeezy.postData();
		
		elevator.updateTables();
	}
}
