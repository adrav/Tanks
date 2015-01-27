package tanks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

public class Missle extends GameObject {

	// abs value of velocity
	protected double velocity;

	// angle counted counterclockwise from x-axis
	protected double angle;

	private Physics ph = new Physics();

	HashMap<String, Double> movementData = new HashMap<String, Double>();

	public Missle(Game game, int x, int y, double velocity, double angle) {
		super(game, x, y);
		defaultColor = new Color(0, 0, 255);
		color = defaultColor;
		defaultShape = new Rectangle(x, y, 10, 10);
		shape = defaultShape;
		this.velocity = velocity;
		this.angle = angle;
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

	@Override
	public void move(long deltaTime) {
		movementData = ph.advancedTrajectory(velocity, angle, gameInstance.windPower, deltaTime);
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
	public void draw(Graphics2D g) {
		g.fillOval((int) x, (int)y, (int)(shape.getWidth()), (int)(shape.getHeight()));
	}

	@Override
	void inCollision(GameObject other) {
		if(other.getClass().getSimpleName().equals("BackgroundObject")) {
			this.setIsDestroyed(true);
		}
		
	}
}


