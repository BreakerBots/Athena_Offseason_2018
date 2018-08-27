package com.frc5104.autocommands;

public interface BreakerCommand {
	public void initialize();
	public void execute();
	public boolean isFinished();
	public void end();
}
