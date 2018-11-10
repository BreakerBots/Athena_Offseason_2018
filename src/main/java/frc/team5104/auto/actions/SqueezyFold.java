package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerPathAction;
import frc.team5104.subsystem.squeezy.SqueezyActions;

/*Breakerbots Robotics Team 2018*/
public class SqueezyFold extends BreakerPathAction {

	private boolean up;
	
	public SqueezyFold(boolean up) {
		this.up = up;
	}
	
    public void init() {
    	SqueezyActions.foldSet(up);
    }

    public boolean update() {
    	return true;
    }

    public void end() {
    	
    }
}
