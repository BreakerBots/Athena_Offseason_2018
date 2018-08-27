package com.frc5104.autocommands;

public class BreakerCommandGroup {
	public BreakerCommand[] cs = new BreakerCommand[10];
	public int cl = 0;
	
	public void add(BreakerCommand command) {
		cs[cl] = command;
		cl++;
	}
	
	public void update() {
		
	}
	
	public void init() {
		
	}
}
