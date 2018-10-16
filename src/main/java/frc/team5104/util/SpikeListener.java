package frc.team5104.util

/**
 * <h1>Spike Listener</h1>
 * Returns true if significant spike has occurred in the given interval
 */

public class SpikeListener {
	private int numPlacesToCheck;
	private double threshold;
	
	private double[] values;
	private double oldMean, newMean, updateMean;
	private boolean first;
	
	public SpikeListener() {
		numPlacesToCheck = 10;
		threshold = 3;
		init();
	}
	
	public SpikeListener(int numPlacesToCheck, double threshold) {
		this.numPlacesToCheck = numPlacesToCheck;
		this.threshold = threshold;
		init();
	}
	
	public void init() {
		values = new double[numPlacesToCheck];
		for(int i = 0; i < values.length; i++) {
			values[i] = 0;
		}
		updateMean = 0;
		oldMean = 0; 
		newMean = 0;
		first = false;
	}
	
	public boolean update(double newValue) {
		double sum = newValue;
		boolean r = false;
		for(int i = values.length - 2; i >= 0; i--) {
			values[i + 1] = values[i];
			sum += values[i];
		}
		values[0] = newValue;
		updateMean++;
    	if(updateMean > numPlacesToCheck/2) {
	        double avg = sum/values.length;
		    if(first) {
		        oldMean = newMean;
		        newMean = avg;
	            System.out.println(avg + " " + oldMean + " " + newMean);
		        r = ((newMean - oldMean) >= threshold);
		    } else {
		        newMean = avg;
		    }
		    updateMean = 0;
		    first = true;
		}
		
		return r;
	}
}
