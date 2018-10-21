package frc.team5104.util;

import java.io.PrintWriter;

public class CSV {
	public String content;
	
	/**
	 * Log data to import into a CSV
	 * @param heads Header of the file
	 */
	public CSV(String[] heads) {
		content = "";
		for(int i = 0; i < heads.length; i++) 
			content += heads[i] + (i < heads.length - 1 ? ", " : "");
		content += '\n';
	}
	
	/**
	 * Log next set of values
	 * @param values Follow the order of the head
	 */
	public void update(String[] values) {
		for(int i = 0; i < values.length; i++) 
			content += values[i] + (i < values.length - 1 ? ", " : "");
		content += '\n';
	}
	
	/**
	 * Saves the CSV File
	 * @param path The path where to save on the robot
	 * @param fileName The file name (not the extension)
	 */
	public void writeFile(String path, String fileName) {
		try {
			String home = "/home/lvuser/";
			PrintWriter writer = new PrintWriter(home + path + "/" + fileName + ".txt", "UTF-8");
			writer.print(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
