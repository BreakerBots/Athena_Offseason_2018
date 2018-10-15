package frc.team5104.util;

/*Breakerbots Robotics Team 2018*/
/**
 * <h1>Interpolating Curve</h1>
 * Iterates to a changing setpoint in a certain amount of time.
 */
public class CurveInterpolator {
	private double sp;  //Setpoint
	private double cp;  //Current Point
	
	private double spi; //Setpoint bottom
	private double ct;  //% Change in cp, +/- dt
	
	public double dt;
	
	Curve.BezierCurve curve;
	
	/**
	 * <h1>Interpolating Curve</h1>
	 * Iterates to a changing setpoint in a certain amount of time.
	 * @param dt The time to reach each setpoint (time to go from min to max in loops)
	 * @param min The min value being input for the Setpoint
	 * @param max The max value being input for the Setpoint
	 */
	public CurveInterpolator(double dt, Curve.BezierCurve curve) {
		this.dt = dt;
		this.curve = curve;
		setSetpoint(0, true);
	}
	
	/**
	 * Call this function periodically
	 * @return the current value
	 */
	public double update() {
		//Get Progress %
		double p = (cp - spi) / (sp - spi);
		
		//Add Step
		if (ct > 0 ? cp <= sp : cp >= sp) {
			cp += ct;
		}
		
		//Return Value at Progress
		return curve.getPoint(p) * (sp - spi) + (spi);
	}
	
	/**
	 * Sets the target/setpoint
	 * @param setPoint The setpoint to set to
	 */
	public void setSetpoint(double setPoint) {
		setSetpoint(setPoint, false);
	}
	
	/**
	 * Sets the target/setpoint
	 * @param setPoint The setpoint to set to
	 * @param instant If the current position should instantly move to this point [def false]
	 */
	public void setSetpoint(double setPoint, boolean instant) {
		//Setpoints
		sp = setPoint;
		if (instant)
			cp = setPoint;
		
		//Get init to calculate progress
		spi = cp;
		
		//Calculate change in cp, +/- dt
		ct = (sp > cp) ? dt : -dt;
	}
}





















