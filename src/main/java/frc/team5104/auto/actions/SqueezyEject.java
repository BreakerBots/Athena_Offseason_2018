package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerPathAction;
import frc.team5104.subsystem.squeezy.SqueezyActions;
import frc.team5104.subsystem.squeezy.SqueezyManager;
import frc.team5104.subsystem.squeezy.SqueezyManager.SqueezyEjectSpeed;

/*Breakerbots Robotics Team 2018*/
public class SqueezyEject extends BreakerPathAction {

	SqueezyEjectSpeed speed;
	
	public SqueezyEject() {
		this.speed = SqueezyEjectSpeed.High;
	}
	
	public SqueezyEject(SqueezyEjectSpeed speed) {
		this.speed = speed;
	}
	
    public void init() {
    	SqueezyActions.eject(this.speed);
    }

    public boolean update() {
    	return SqueezyManager.getState() == SqueezyManager.SqueezyState.idle;
    }

    public void end() {
    	
    }
}
