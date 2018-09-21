package com.frc5104.utilities;

import java.lang.reflect.Method;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ntConsole {
	
	private static String streamString = "";
	
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
				if (coms[i] == com)
					return i;
			}
			return -1;
		}
	}
	
	/**
	 * An update function for this class, call in robotPeriodic
	 */
	public static void update() {
		//Init Network Table
		if (table == null)
			table = NetworkTableInstance.getDefault().getTable("Console");
		
		//Input
		String ci = table.getEntry("input").getString("");
		if (ci != "") {
			//Parse & Execute
			ci = ci.toLowerCase();
			String[] ca = ci.split(" ");
			String cr = executeCommand(ca[0], ca[1]);
			
			//Send Response
			table.getEntry("response").setString(cr);
			
			//Clear input
			table.getEntry("input").setString("");
		}
		
		//Stream
		table.getEntry("stream").setString(streamString);
		streamString = "";
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
	public static void registerCommand(Class location, String command, Method functionCallback) {
		registeredCommands.push(
			location,
			command.toLowerCase(),
			functionCallback
		);
	}
	
	private static String executeCommand(String com, String mod) {
		int a = registeredCommands.findIndexOfCommand(com);
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
