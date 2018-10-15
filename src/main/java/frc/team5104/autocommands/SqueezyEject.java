package frc.team5104.autocommands;

import frc.team5104.main.subsystems.Squeezy;
import frc.team5104.main.subsystems.Squeezy.SqueezyEjectSpeed;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

/*Breakerbots Robotics Team 2018*/
public class SqueezyEject extends BreakerAction {

	SqueezyEjectSpeed speed;
	
	public SqueezyEject() {
		this.speed = SqueezyEjectSpeed.High;
	}
	
	public SqueezyEject(SqueezyEjectSpeed speed) {
		this.speed = speed;
	}
	
    public void init() {
    	console.log(c.SQUEEZY, "Ejecting");
    	Squeezy.actions.eject(this.speed);
    }

    public boolean update() {
    	return Squeezy.getState() == Squeezy.SqueezyState.idle;
    }

    public void end() {
    	
    }
}
