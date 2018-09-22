package com.frc5104.utilities;

import java.lang.reflect.Method;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ntConsole {
	
	private static String streamString = "";
	private static String inputString = "";
	private static String cmdsString = "";
	private static int updateCounter = 0;
	
	private static NetworkTable table;
	
	private static class registeredCommands {
		private static String[] coms = new String[50];
		private static Class[]  locs = new Class [50];
		private static Method[] funs = new Method[50];
		private static int i = 0;
		
		private static void push(Class loc, String com, Method fun) {
			if (i < 50) {
				coms[i] = com;
				locs[i] = loc;
				funs[i] = fun;
				i++;
			}
		}
		
		private static int findIndexOfCommand(String com) {
			for (int i = 0; i < coms.length; i++) {
				if (com.equals(coms[i]))
					return i;
			}
			return -1;
		}
	}
	
	/**
	 * An init function for this class, call in robotInit
	 */
	public static void init() {
		//Init Network Table
		if (table == null)
			table = NetworkTableInstance.getDefault().getTable("Console");
		
		table.getEntry("stream").setString("");
		table.getEntry("input").setString("");
		table.getEntry("response").setString("");
		table.getEntry("commands").setString("");
	}
	
	/**
	 * An update function for this class, call in robotPeriodic
	 */
	public static void update() {
		try {
		if (updateCounter > (0.5/*seconds*/ * 50)) {
			updateCounter = 0;
			
			//Init Network Table
			if (table == null)
				table = NetworkTableInstance.getDefault().getTable("Console");
			
			//Input
			String ci = table.getEntry("input").getString("");
			if (!ci.equals("") && !ci.equals(inputString)) {
				inputString = ci;
				ci = ci.substring(1);
				
				//Parse & Execute
				int ca = ci.indexOf(' ');
				String com = ci.toLowerCase().substring(0, ca);
				String mod = ci.substring(ca + 1);
				String cr = executeCommand(com, mod);
				
				//Send Response
				table.getEntry("response").setString(ci + "<splitme>" + cr);
			}
			
			//Stream
			if (streamString != "") {
				table.getEntry("stream").setString(streamString);
				streamString = "";
			}
		}
		updateCounter++;
		} catch (Exception e) { console.error(e); }
	}
	
	/**
	 * Logs a message to the dashboard console
	 * @param msg The message
	 */
	public static void logToStream(String msg) {
		//Add Splitter
		if (streamString != "")
			streamString += "<splitme>";
		
		//Add Message
		streamString += msg;
	}
	
	/**
	 * Register a new command that can be called with dashboard console
	 * @param location The Class of where the function is
	 * @param command The name of the command in which to register
	 * @param functionCallback The function called when the command is executed. Must take in String (the mod) and return a string (the response)
	 */
	public static void registerCommand(Class<?> location, String command, String functionCallback) {
		try {
			Method func = location.getMethod(functionCallback, String.class);
			
			registeredCommands.push(
				location,
				command.toLowerCase(),
				func
			);
			
			cmdsString += command.toLowerCase() + "<splitme>";
			table.getEntry("commands").setString(cmdsString);
		} catch (Exception e) {
			console.error("Failed to create the console command " + command + " because " + e); 
		}
	}
	
	private static String executeCommand(String com, String mod) {
		int a = registeredCommands.findIndexOfCommand(com.toLowerCase());
		if (a != -1) {
			try {
				return (String) registeredCommands.funs[a].invoke(registeredCommands.locs[a], mod);
			} 
			catch (Exception e) {
				console.error(e);
				e.printStackTrace();
				return "An unknown error occured";
			}
		}
		else
			return "That is not a registered command";
	}
}
