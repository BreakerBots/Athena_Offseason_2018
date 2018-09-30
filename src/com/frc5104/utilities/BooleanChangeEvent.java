package com.frc5104.utilities;

/**
 * Send a boolean every tick to this class, and if that boolean changes (true => false, false => true) it will return true, else false
 * Similar to a button pressed event in controller.java
 */
public class BooleanChangeEvent {
	private boolean lv = false;
	private boolean ct;
	
	/**
	 * Constructor... Call the get function every loop
	 */
	public BooleanChangeEvent() {
		ct = false;
	}
	
	/**
	 * Constructor... Call the get function every loop
	 * @param onlyToggleWhenChangeToTrue If it should only return true when values go (false => true) and not (true => false)
	 */
	public BooleanChangeEvent(boolean onlyToggleWhenChangeToTrue) {
		ct = onlyToggleWhenChangeToTrue;
	}
	
	public boolean get(boolean currentValue) {
		if (currentValue != lv) {
			lv = currentValue;
			if (ct && currentValue == true)
				return true;
			else if (!ct)
				return true;
		}
		return false;
	}
}
