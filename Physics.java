//: Tanks/Physics.java

package tanks;

import static java.lang.Math.*;
import java.util.HashMap;

/**
 * Class for calculating physics.
 * @author Michal Czop
 */

public class Physics {

	/** Variables - initial conditions */

	/** Initial velocity in axis x. */
	private double v0X;

	/** Initial velocity in axis y. */
	private double v0Y;

	/** Initial abs value of velocity. */
	private double v0;

	/** Initial value of angle. */
	private double startAngle;

	/** Variables - end conditions */
	private double vX;
	private double vY;
	private double v;
	private double angle;
	
	/** Distance moved in axis x. */
	private double deltaX;

	/** Distance moved in axis y. */
	private double deltaY;

	/** Physics constants. */
	private final double g = 100; // [m/s^2]

	/** 
	 * Calculates movement variables. Returns HashMap
	 * containing velocity, angle, delta x and delta y after
	 * time period.
	 * @param startVelocity - initial value of velocity
	 * @param startAngle - initial value of angle
	 * @param windForce - horizontal force of wind
	 * @param timePeriod - time value for calculations
	 */
	public HashMap advancedTrajectory (double startVelocity, double startAngle, double windForce, long timePeriod) {

		HashMap<String, Double> endConditions = new HashMap<String, Double>();
		double time = (double)timePeriod/1000;
		double mass = 100;//[kg];
		double windAcc = windForce/mass;
	
		/** Start conditions. */
		v0X = startVelocity*cos(toRadians(startAngle));
		v0Y = startVelocity*sin(toRadians(startAngle));

		/** End conditions. */
		vX = v0X - windAcc*time;
		vY = v0Y + g*time;
		deltaX = v0X*time - (windAcc*pow(time, 2))/2;
		deltaY = v0Y*time + (g*pow(time, 2))/2;

		v = sqrt(pow(vX, 2) + pow(vY, 2));

		if (vX >= 0 && vY >= 0) { angle = toDegrees(asin(vY/v)); }
		if (vX < 0 && vY >= 0) { angle = 180-toDegrees(asin(vY/v)); }
		if (vX < 0 && vY < 0) { angle = 180-toDegrees(asin(vY/v)); }
		if (vX >= 0 && vY < 0) { angle = 360+toDegrees(asin(vY/v)); }

		endConditions.put("velocity", v);
		endConditions.put("angle", angle);
		endConditions.put("deltaX", deltaX);
		endConditions.put("deltaY", deltaY);

		return endConditions;
	}

	/** 
	 * Calculates movement variables in perfect vacuum.
	 * Returns HashMap containing velocity, angle, 
	 * delta x and delta y after time period.
	 * @param startVelocity - initial value of velocity
	 * @param startAngle - initial value of angle
	 * @param timePeriod - time value for calculations
	 */
	
	public HashMap simpleTrajectory (double startVelocity, double startAngle, long timePeriod) {

		HashMap<String, Double> endConditions = new HashMap<String, Double>();
		double time = (double)timePeriod/1000;
	
		/** Start conditions. */
		v0X = startVelocity*cos(toRadians(startAngle));
		v0Y = startVelocity*sin(toRadians(startAngle));

		/** End conditions. */
		vX = v0X;
		vY = v0Y + g*time;
		deltaX = v0X*time;
		deltaY = v0Y*time + (g*pow(time, 2))/2;

		v = sqrt(pow(vX, 2) + pow(vY, 2));

		if (vX >= 0 && vY >= 0) { angle = toDegrees(asin(vY/v)); }
		if (vX < 0 && vY >= 0) { angle = 180-toDegrees(asin(vY/v)); }
		if (vX < 0 && vY < 0) { angle = 180-toDegrees(asin(vY/v)); }
		if (vX >= 0 && vY < 0) { angle = 360+toDegrees(asin(vY/v)); }

		endConditions.put("velocity", v);
		endConditions.put("angle", angle);
		endConditions.put("deltaX", deltaX);
		endConditions.put("deltaY", deltaY);

		return endConditions;
	}
}

