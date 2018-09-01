package com.frc5104.utilities;

/*Breakerbots Robotics Team 2018*/
public class console {
	
	//Normal Logging
	public static enum Type { 
		TELEOP(color.CYAN_BOLD), 
		AUTO(color.PURPLE_BOLD), 
		MAIN(color.YELLOW_BOLD), 
		INTAKE(color.GREEN_BOLD), 
		ELEV(color.BLUE_BOLD), 
		DRIVE(color.BLACK_BOLD), 
		OTHER(color.WHITE_BOLD), 
		ERROR(color.RED_BOLD),
		WARN(color.YELLOW_BOLD_BRIGHT);
		
		String mod;
		Type (String mod){
			this.mod = mod;
		}
	}
	
	/**
	 * Prints out text to the console under the category "OTHER"
	 * @param a The text to print out
	 */
	public static void log(String a) { log(a, Type.OTHER); }
	/**
	 * Prints out text to the console under the specific category
	 * @param a The text to print out
	 * @param t The desired category
	 */
	public static void log(String a, Type t) {
		System.out.println(t.mod + t.toString() + ": " + color.RESET + a);
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
	
	//Timing Groups/Sets
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
			System.out.println(t.toString() + ": " + a + " " + timeSpacer + " " + getTime(timingGroupName) + "s");
		}
	}

	//Text Decoration and Colors
	public static class color {
	    // Reset
	    public static final String RESET = "\033[0m";  // Text Reset

	    // Regular Colors
	    public static final String BLACK = "\033[0;30m";   // BLACK
	    public static final String RED = "\033[0;31m";     // RED
	    public static final String GREEN = "\033[0;32m";   // GREEN
	    public static final String YELLOW = "\033[0;33m";  // YELLOW
	    public static final String BLUE = "\033[0;34m";    // BLUE
	    public static final String PURPLE = "\033[0;35m";  // PURPLE
	    public static final String CYAN = "\033[0;36m";    // CYAN
	    public static final String WHITE = "\033[0;37m";   // WHITE

	    // Bold
	    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
	    public static final String RED_BOLD = "\033[1;31m";    // RED
	    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
	    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
	    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
	    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
	    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

	    // Underline
	    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
	    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
	    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
	    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
	    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
	    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
	    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
	    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

	    // Background
	    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
	    public static final String RED_BACKGROUND = "\033[41m";    // RED
	    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
	    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
	    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
	    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
	    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
	    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

	    // High Intensity
	    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
	    public static final String RED_BRIGHT = "\033[0;91m";    // RED
	    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
	    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
	    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
	    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
	    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
	    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

	    // Bold High Intensity
	    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
	    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
	    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
	    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
	    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
	    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

	    // High Intensity backgrounds
	    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
	    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
	    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
	    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
	    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
	    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
	    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
	    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
	}
}
