package frc.team5104.traj;

/*Breakerbots Robotics Team 2018*/
public class RobotPosition {
	public double x, y;
	private double t;
	public RobotPosition(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.t = theta;
	}
	
	public void set(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.t = theta;
	}
	
    public double getTheta() {
        return t % (Math.PI * 2.0);
    }
	
	public String toString() {
		return  "x: " + x + ", " +
				"y: " + y + ", " +
				"t: " + t;
	}
}
