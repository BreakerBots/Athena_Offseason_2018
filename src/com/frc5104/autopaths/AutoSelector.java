package com.frc5104.autopaths;

import com.frc5104.autocommands.BreakerPath;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;
import com.frc5104.utilities.console.t;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

/*Breakerbots Robotics Team 2018*/
/**
 * Uses ("FMS Data" + "Net Table" => "The Correct Path") 
 */
public class AutoSelector {
	
	public static volatile String gameData = null;
	
	//A trick to the pre initialize all the paths
	public static enum Paths {
		Baseline(new Baseline()),
		LL(new LL()), 
		LR(new LR()),
		CL(new CL()),
		CR(new CR()),
		RL(new RL()),
		RR(new RR());
		
		BreakerPath path;
		Paths (BreakerPath path){
			this.path = path;
		}
		public BreakerPath getPath() {
			return this.path;
		}
	}
	
	public static BreakerPath getAuto() {
		console.sets.create("GetAuto");
		//Default Path is Baseline
		BreakerPath auto = Paths.Baseline.getPath();

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
			console.log(c.AUTO, "Recieved Game Data => " + gameData + ". At => " + DriverStation.getInstance().getMatchTime());
			
			//Get our Robot Position on the Field
			String position;
			position = NetworkTableInstance.
					getDefault().
					getTable("Autonomous").
					getEntry("AutoPos").
					getString("null");

			if ((gameData.charAt(0) == 'L' || gameData.charAt(0) == 'R') && !position.equals("null")) {
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
			console.log(c.AUTO, "Failed Game Data. At => " + DriverStation.getInstance().getMatchTime());
		}
		
		//Print out the Path were Running + Time it took to choose
		console.sets.log(c.AUTO, t.INFO, "GetAuto", "Chose Autonomous Route: " + auto.getClass().getSimpleName() + " and took ");
		
		return auto;
	}
}
