package tanks;

import static java.lang.Math.*;

import java.util.HashMap;

public class Physics {

	// variables - initial conditions
	private double v0X;
	private double v0Y;
	private double v0;
	private double startAngle;

	// variables - end conditions
	private double vX;
	private double vY;
	private double v;
	private double angle;
	private double range;

	private double deltaX;
	private double deltaY;

	// physics constants
	private final double g = 10; // [m/s^2]

	// calculates power, angle and movement since in current moment
	// angle in degrees is measured counterclockwise from x axis
	// time period conted in miliseconds
	// 1m = 20 pixels

	public HashMap advancedTrajectory (double startVelocity, double startAngle, double windForce, long timePeriod) {

		HashMap<String, Double> endConditions = new HashMap<String, Double>();
		double time = (double)timePeriod/1000;
		double mass = 100;//[kg];
		double windAcc = windForce/mass;
	
		// Start conditions - SPRAWDZONE
		v0X = startVelocity*cos(toRadians(startAngle));
		v0Y = startVelocity*sin(toRadians(startAngle));

		// End conditions - SPRAWDZONE
		vX = v0X - windAcc*time;
		vY = v0Y - g*time;
		deltaX = v0X*time - (windAcc*pow(time, 2))/2;
		deltaY = v0Y*time - (g*pow(time, 2))/2;

		v = sqrt(pow(vX, 2) + pow(vY, 2));

		if (vX >= 0 && vY >= 0) { angle = toDegrees(asin(vY/v)); }
		if (vX < 0 && vY >= 0) { angle = 180-toDegrees(asin(vY/v)); }
		if (vX < 0 && vY < 0) { angle = 180-toDegrees(asin(vY/v)); }
		if (vX >= 0 && vY < 0) { angle = 360+toDegrees(asin(vY/v)); }

		endConditions.put("velocity", v);
		endConditions.put("angle", angle);
		endConditions.put("deltaX", deltaX);
		endConditions.put("deltaY", deltaY);

		// return HashMap<new velocity, new angle, deltaX, deltaY> 
		return endConditions;
	}

	// trajcectory in perfect vacum
	
	public HashMap simpleTrajectory (double startVelocity, double startAngle, long timePeriod) {

		HashMap<String, Double> endConditions = new HashMap<String, Double>();
		double time = (double)timePeriod/1000;
	
		// Start conditions - SPRAWDZONE
		v0X = startVelocity*cos(toRadians(startAngle));
		v0Y = startVelocity*sin(toRadians(startAngle));

		// End conditions - SPRAWDZONE
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

		// return HashMap<new velocity, new angle, deltaX, deltaY> 
		return endConditions;
	}
}


//// testy
//
//	public static void main(String[] args) {
//		
//		Physics ph = new Physics();
//		HashMap<String, Double> result = new HashMap<String, Double>();
//		/*
//		result = ph.simpleTrajectory(10, 45, 1);
//		System.out.println("deltaX = " + result.get("deltaX"));	
//		System.out.println("deltaY = " + result.get("deltaY"));
//		System.out.println("new velocity = " + result.get("velocity"));
//		System.out.println("new angle = " + result.get("angle"));
//		*/
//
//		double[][] results = new double[15][2];
//		for (int i = 0; i < 15; i++) {
//			System.out.print("[");
//
//			result = ph.advancedTrajectoryResistance(30, 45, i*500 + 500);
//			results[i][0] = result.get("deltaX");
//			results[i][1] = result.get("deltaY");
//
//			System.out.println("[" + results[i][0] + ", " + results[i][1] + "]");
//		}
//		System.out.println("], ");
//		
//		System.out.println("pozdROCK!");
//
//
//
//	}
