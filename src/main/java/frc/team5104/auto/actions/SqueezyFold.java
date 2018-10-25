package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerPathAction;
import frc.team5104.subsystem.squeezy.SqueezyActions;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

/*Breakerbots Robotics Team 2018*/
public class SqueezyFold extends BreakerPathAction {

	private boolean up;
	
	public SqueezyFold(boolean up) {
		this.up = up;
	}
	
    public void init() {
    	console.log(c.SQUEEZY, up ? "Folding Up" : "Folding Down");
    	SqueezyActions.foldSet(up);
    }

    public boolean update() {
    	return true;
    }

    public void end() {
    	
    }
}
