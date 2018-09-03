package com.frc5104.autopaths;

import com.frc5104.autocommands.BreakerCommandGroup;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

/*Breakerbots Robotics Team 2018*/
public class AutoSelector {
	
	public static volatile String gameData = null;
	
	//Having the paths predeclared lets them process waypoints in robot idle
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
		//Default Path is Baseline
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
		
		//I Recived Game Data
		if (gameData != null) {
			console.log("Recieved Game Data => " + gameData + ". At => " + DriverStation.getInstance().getMatchTime(), Type.AUTO);
			
			//Get our Robot Position on the Field
			String position;
			position = NetworkTableInstance.
					getDefault().
					getTable("Autonomous").
					getEntry("AutoPos").
					getString("null");

			if (!position.equals("null")) {
				switch (position) {
					case "L":
						auto = (gameData.charAt(0) == 'L') ? Paths.LL.getPath() : Paths.LR.getPath();
						break;
					case "C":
						auto = (gameData.charAt(0) == 'L') ? Paths.CL.getPath() : Paths.CR.getPath();
	
						break;
					case "R":
						auto = (gameData.charAt(0) == 'L') ? Paths.RL.getPath() : Paths.RR.getPath();
						break;
					default:
						//Let it just flow through to default at baselines
						break;
				};
			}
		}
		//Got No Game Data => Defaults to Run Baseline
		else {
			console.log("Failed Game Data. At => " + DriverStation.getInstance().getMatchTime(), Type.AUTO);
		}
		
		//Print out the Path were Running
		console.log("Chose Autonomous Route => " + auto.getClass().getName(), Type.AUTO);
		
		return auto;
	}
}
