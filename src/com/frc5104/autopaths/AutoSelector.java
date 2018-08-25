package com.frc5104.autopaths;

import java.lang.reflect.InvocationTargetException;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*Breakerbots Robotics Team 2018*/
public class AutoSelector {
	
	public enum Position {
		kLeft, kCenter, kRight
	}
	
	public static final double kSwitchEject = 0.6;
	public static final double kScaleEject = 1;
	public static DoubleSolenoid squeezySolenoid;
	
	public static volatile String gameData = null;
	public static Position robotPosition;
	
	
	public static CommandGroup getAuto() {
		CommandGroup auto = new Baseline();

		Thread gameDataThread = new Thread() {
			public void run() {
				//Loop to get Game Data
				while (!Thread.interrupted()) {
					//Get the data
					gameData = DriverStation.getInstance().getGameSpecificMessage();
					
					if (gameData != null)
						break;
					
					//Wait 100ms to continue loop
					try {
						Thread.sleep(100);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}; 
		
		//Run this thread for a max of 3000ms
		try {
			gameDataThread.start();
			gameDataThread.join(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (gameData != null) {
			System.out.println("AUTO: Recieved Game Data => " + gameData + ". At => " + DriverStation.getInstance().getMatchTime());
			String position;
			position = NetworkTableInstance.
					getDefault().
					getTable("Autonomous").
					getEntry("AutoPos").
					getString("null");

			if (!position.equals("null"))
				switch (position) {
					case "Left":
						auto = (gameData.charAt(0) == 'L') ? new LL() : new LR();
						break;
					case "Center":
						auto = (gameData.charAt(0) == 'L') ? new CL() : new CR();
	
						break;
					case "Right":
						auto = (gameData.charAt(0) == 'L') ? new RL() : new RR();
						break;
				};
		}
		else {
			System.out.println("AUTO: Failed Game Data. At => " + DriverStation.getInstance().getMatchTime());
		}
		
		System.out.println("AUTO: Running path => " + auto.getName());
		
		return auto;
	}
}
