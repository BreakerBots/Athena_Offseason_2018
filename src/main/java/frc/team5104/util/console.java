package frc.team5104.util;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.team5104.main._RobotConstants;

/*Breakerbots Robotics Team 2018*/
/**
 * <h1>Console</h1>
 * A class for handling logging/printing
 */
public class console {
	
	//  ----------------------------------------  Normal Logging  ----------------------------------------  \\
	/**
	 * Categories for Logging
	 */
	public static enum c { 
		TELEOP, 
		AUTO, 
		MAIN, 
		SQUEEZY, 
		ELEV, 
		DRIVE, 
		OTHER
	};
	
	/**
	 * Types for Logging
	 * "message" is the text appended to begining of log
	 * ERROR => "ERROR"
	 * INFO => ""
	 * WARNING => "WARNING"
	 */
	public static enum t { 
		ERROR  ("ERROR "),
		INFO   (""),
		WARNING("WARNING ");
		
		String message;
		t (String message) {
			this.message = message;
		}
	};
	
	//Main Log Function (Acts as Bus for other functions)
	/**
	 * Prints out text to the console under the specific category (Base Function)
	 * Examples:
	 * 	 2.12 [MAIN]: Message
	 *   90.12 ERROR [AUTO]: Message
	 * @param a The text to print out
	 * @param t The type (Error, Info, Warning)
	 * @param c the category (INTAKE, AUTO...)
	 */
	public static void log(c c, t t, Object... a) {
		String f = String.format("%.2f", Timer.getFPGATimestamp()) + ": " + t.message + "[" + c.toString() + "]: " + objectArrayToString(a);
		System.out.println(f);
		if (logFile.isLogging)
			logFile.log += f + "\n";
	}
	
	// -- INFO
	/**
	 * Prints out text to the console under the type "INFO"
	 * @param c The category to print under
	 * @param a The text to print out
	 */
	public static void log(c c, Object... a) { log(c, t.INFO, objectArrayToString(a)); }
	/**
	 * Prints out text to the console under the type "INFO" and category "OTHER"
	 * @param a The text to print out
	 */
	public static void log(Object... a) { log(c.OTHER, t.INFO, objectArrayToString(a)); }
	
	// -- ERROR
	/**
	 * Prints out text to the console under the type "ERROR"
	 * @param c The category to print under
	 * @param a The text to print out
	 */
	public static void error(c c, Object... a) { log(c, t.ERROR, objectArrayToString(a)); }
	/**
	 * Prints out text to the console under the type "ERROR" and category "OTHER"
	 * @param a The text to print out
	 */
	public static void error(Object... a) { log(c.OTHER, t.ERROR, objectArrayToString(a)); }
	
	// -- WARNING
	/**
	 * Prints out text to the console under the type "WARN"
	 * @param c The category to print under
	 * @param a The text to print out
	 */
	public static void warn(c c, Object... a) { log(c, t.WARNING, objectArrayToString(a)); }
	/**
	 * Prints out text to the console under the type "WARN" and category "OTHER"
	 * @param a The text to print out
	 */
	public static void warn(Object... a) { log(c.OTHER, t.WARNING, objectArrayToString(a)); }
	
	
	
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
				console.log("Max Amount of Sets Created");
		}
		
		/** Returns the index of the timing group/set (-1 if not found) */
		public static int getIndex(String name) {
			name = name.toLowerCase();
			for (int i = 0; i < sn.length; i++) {
				if (name.equals(sn[i]))
					return i;
			}
			return -1;
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
			if (i == -1) return 0;
			else {
				double r = System.currentTimeMillis() - sv[i];
				switch (format) {
					case Milliseconds:
						return r;
					case Seconds:
						return r / 1000.0;
					case Minutes:
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
		public static void log(c c, t t, String timingGroupName, Object... a) {
			console.log(c, t, objectArrayToString(a) + " " + String.format("%.2f", getTime(timingGroupName)) + "s");
		}
	}
	
	//  ----------------------------------------  File Logging  ----------------------------------------  \\
	public static class logFile {
		private static String log = "";
		public static boolean isLogging = false;
		
		/**
		 * Starts Recording the "console.logs" into a string that can be saved with "console.logFile.end()"
		 * If the logger is already logging it will not clear the log, but just do nothing
		 */
		public static void start() {
			if (!isLogging) {
				isLogging = true;
				log = "";
			}
		}
		
		/**
		 * Saves the log string that is logged onto after calling "console.logFile.start()"
		 * Match => "MatchLog" if Constants.SaveMatchLogs
		 * Other => "GeneralLog" if Constants.SaveNonMatchLogs
		 */
		public static void end() {
			try {
				if (isLogging) {
					isLogging = false;
					
					//File Path
					boolean hasFMS = DriverStation.getInstance().isFMSAttached();
					if (hasFMS ? _RobotConstants.Logging._SaveMatchLogs : _RobotConstants.Logging._SaveNonMatchLogs) {
						String filePath = "/home/lvuser/" + (hasFMS ? "MatchLog/" : "GeneralLog/");
						
						//File Name
						filePath += DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm").format(LocalDateTime.now()) + ".txt";
						
						console.log("Saving Log File as: " + filePath);
						
						//Save File
						PrintWriter writer = new PrintWriter(filePath, "UTF-8");
						writer.print(log);
						writer.close();
					}
				}
			} catch (Exception e) { console.error(e); }
		}
	}
	
	private static String objectArrayToString(Object[] a) {
		String r = "";
		for (int i = 0; i < a.length; i++) {
			if (i != (a.length - 1))
				r += a[i] + ", ";
			else
				r += a[i];
		}
		return r;
	}
}












