package tanks;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashMap;

public class Missle extends GameObject {

	// abs value o velocity
	private double velocity;

	// angle counted counterclockwise from x-axis
	private double angle;

	private Physics ph = new Physics();

	HashMap<String, Double> movementData = new HashMap<String, Double>();

	public Missle(Game game, int x, int y, double velocity, double angle) {

		super(game, x, y);
		defaultColor = new Color(0, 0, 255);
		color = defaultColor;
		shape = defaultShape;
		this.velocity = velocity;
		this.angle = angle;
		shape = new Rectangle(x, y, 20, 20);

	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
 	}
	public double getVelocity() {
		return velocity;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	public double getAngle() {
		return angle;
	}

	public void move(long deltaTime) {

		movementData = ph.simpleTrajectory(velocity, angle, deltaTime);
		x += movementData.get("deltaX");
		y += movementData.get("deltaY");
		velocity = movementData.get("velocity");
		angle = movementData.get("angle");
		if (x>gameInstance.getWidth() + 100 || y > gameInstance.getHeight()+100 
				|| x < -50) {
			isDestroyed = true;
		}

	}

	@Override
	void inCollision(GameObject other) {
		// TODO Auto-generated method stub
		
	}
}


