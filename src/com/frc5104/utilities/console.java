package com.frc5104.utilities;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.wpi.first.wpilibj.DriverStation;

/*Breakerbots Robotics Team 2018*/
public class console {
	
	//  ----------------------------------------  Normal Logging  ----------------------------------------  \\
	public static enum Type { 
		TELEOP("i "), 
		AUTO  ("i "), 
		MAIN  ("s "), 
		INTAKE("i "), 
		ELEV  ("i "), 
		DRIVE ("i "), 
		OTHER ("o "), 
		ERROR ("s "),
		WARN  ("s ");
		
		String mod;
		Type (String mod) {
			this.mod = mod;
		}
	}
	/**
	 * Prints out text to the console under the category "OTHER"
	 * @param a The text to print out
	 */
	public static void log(String a) { log(a, Type.OTHER); }
	
	
	//Main Log Function (Acts as Bus for other functions)
	/**
	 * Prints out text to the console under the specific category
	 * @param a The text to print out
	 * @param t The desired category
	 */
	public static void log(String a, Type t) {
		String f = t.mod + t.toString() + ": " + a;
		System.out.println(f);
		log += f + "\n";
	}
	
	
	/**
	 * Prints out text to the console under the category "ERROR"
	 * @param a The text to print out
	 */
	public static void error(String a) { log(a, Type.ERROR); }
	/**
	 * Prints out text to the console under the category "ERROR"
	 * @param a The text to print out
	 */
	public static void error(Exception a) { log(a.getMessage(), Type.ERROR); }
	/**
	 * Prints out text to the console under the category "WARN"
	 * @param a The text to print out
	 */
	public static void warn(String a) { log(a, Type.WARN); }
	
	
	
	//  ----------------------------------------  Timing Groups/Sets  ----------------------------------------  \\
	public static class sets {
		public static final int MaxSets = 10;
		public static String sn[] = new String[MaxSets];
		public static long sv[] = new long[MaxSets];
		public static int si = 0;
		
		/** Creates a new timing group/set and starts the timer */
		public static void create(String name) {
			if (getIndex(name) != -1) {
				reset(name);
			}
			else if (si < (MaxSets - 1)) {
				sn[si] = name.toLowerCase();
				sv[si] = System.currentTimeMillis();
				si++;
			}
			else
				console.log("Max Amount of Sets Created", Type.ERROR);
		}
		
		/** Returns the index of the timing group/set (-1 if not found) */
		public static int getIndex(String name) {
			name = name.toLowerCase();
			int t = -1;
			for (int i = 0; i < sn.length; i++) {
				if (sn[i] == name)
					t = i;
			}
			return t;
		}
		
		/** Resets the time of the corresponding timing group/set */
		public static void reset(String name) {
			int i = getIndex(name);
			if (i != -1) {
				sv[i] = System.currentTimeMillis();
			}
		}
		
		public static enum TimeFormat { Milliseconds, Seconds, Minutes }
		/** Returns the time (in seconds) of the corresponding timing group/set. Returns -1 if nothing found */
		public static double getTime(String name) {
			return getTime(name, TimeFormat.Seconds);
		}
		/** Returns the time (in specified format) of the corresponding timing group/set. Returns -1 if nothing found */
		public static double getTime(String name, TimeFormat format) {
			int i = getIndex(name);
			if (i == -1) return -1;
			else {
				double r = System.currentTimeMillis() - sv[i];
				console.log(System.currentTimeMillis() + ", " + sv[i]);
				switch (format) {
					case Milliseconds:
						return r;
					case Minutes:
						return r / 1000.0;
					case Seconds:
						return r / 1000.0 / 60.0;
					default:
						return r / 1000.0;
				}
			}
		}
		
		/**
		 * Similar to normal "console.log" with the time of a timing group/set appended
		 * "CATEGORY: MESSAGE TIMESPACER TIME". Ex) "AUTO: Initialization took 10.26s"
		 * @param a The text to print out
		 * @param t The Category under which to print out
		 * @param timingGroupName The name of the timing group/set
		 * @param timeSpacer What to add to the message before the time.
		 */
		public static void log(String a, Type t, String timingGroupName, String timeSpacer) {
			console.log(a + " " + timeSpacer + " " + getTime(timingGroupName) + "s", t);
		}
	}


	
	//  ----------------------------------------  File Logging  ----------------------------------------  \\
	private static String log = "";
	private static boolean isLogging = false;
	public static void startLogFile() {
		if (!isLogging) {
			isLogging = true;
			log = "";
		}
	}
	
	public static void endLogFile() {
		try {
			if (isLogging) {
				isLogging = false;
				
				//File Path
				String filePath = "/home/lvuser/";
				if (DriverStation.getInstance().isFMSAttached())
					filePath += "MatchLog/";
				else
					filePath += "GeneralLog/";
				
				//File Name
				String fileName = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm").format(LocalDateTime.now()) + ".txt";
				
				console.log("Saving Log File as: " + filePath + fileName);
				
				//Save File
				PrintWriter writer = new PrintWriter(filePath + fileName, "UTF-8");
				writer.print(log);
				writer.close();
			}
		} catch (Exception e) { console.error(e); }
	}
}












