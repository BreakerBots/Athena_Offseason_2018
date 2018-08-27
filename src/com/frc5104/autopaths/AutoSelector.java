package com.frc5104.autopaths;

import com.frc5104.autocommands.BreakerCommandGroup;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

/*Breakerbots Robotics Team 2018*/
public class AutoSelector {
	
	public enum Position {
		kLeft, kCenter, kRight
	}
	
	public static volatile String gameData = null;
	
	public static enum Paths {
		Baseline(new Baseline()),
		LL(new LL()), 
		LR(new LR()),
		CL(new CL()),
		CR(new CR()),
		RL(new RL()),
		RR(new RR());
		
		BreakerCommandGroup path;
		Paths (BreakerCommandGroup path){
			this.path = path;
		}
		public BreakerCommandGroup getPath() {
			return this.path;
		}
	}
	
	public static BreakerCommandGroup getAuto() {
		BreakerCommandGroup auto = Paths.Baseline.getPath();

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
						auto = (gameData.charAt(0) == 'L') ? Paths.LL.getPath() : Paths.LR.getPath();
						break;
					case "Center":
						auto = (gameData.charAt(0) == 'L') ? Paths.CL.getPath() : Paths.CR.getPath();
	
						break;
					case "Right":
						auto = (gameData.charAt(0) == 'L') ? Paths.RL.getPath() : Paths.RR.getPath();
						break;
				};
		}
		else {
			System.out.println("AUTO: Failed Game Data. At => " + DriverStation.getInstance().getMatchTime());
		}
		
		System.out.println("AUTO: Running path => " + auto.getClass().getName());
		
		return auto;
	}
}
