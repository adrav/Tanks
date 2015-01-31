//: Tanks/Missle.java

package tanks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 * Game object representing basic weapon. Contains data needed
 * for displaying and calculating of movement.
 * Extends GameObject.
 * Overrides: draw(Graphics2D g), move(long deltaTime)
 * Implements: inCollision(GameObject other)
 * @author Michal Czop
 */

public class Missle extends GameObject {

	/** Abs value of velocity. */
	protected double velocity;

	/** Angle counted counterclockwise from x-axis. */
	protected double angle;

	/** 
	 * Instance of Physics class used for calculating
	 * trajectory.
	 */
	private Physics ph = new Physics();

	/** HashMap containing movement parameters:
	 * deltaX, deltaY,
	 * velocity,
	 * angle.
	 */
	HashMap<String, Double> movementData = new HashMap<String, Double>();

	/** 
	 * Constructor 
	 * @param game - instance of game
	 * @param x - position on x axis
	 * @param y - position on y axis
	 * @param velocity - abs value of current velocity
	 * @param angle - value of current angle
	 */ 
	public Missle(Game game, int x, int y, double velocity, double angle) {
		super(game, x, y);
		defaultColor = new Color(0, 0, 255);
		color = defaultColor;
		defaultShape = new Rectangle(x, y, 10, 10);
		shape = defaultShape;
		this.velocity = velocity;
		this.angle = angle;
	}

	/** 
	 * Handles movement of missile basing on data 
	 * calculated by Physics class.
	 * @param deltaTime - time lapsed since last iteration
	 */
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
	
	/** Draws object to Graphics2D object. */
	@Override
	public void draw(Graphics2D g) {
		g.fillOval((int) x, (int)y, (int)(shape.getWidth()), (int)(shape.getHeight()));
	}

	/** 
	 * Determines actions performed after collision 
	 * with other objects.
	 * @param other - GameObject which collides with this
	*/
	@Override
	void inCollision(GameObject other) {
		if(other.getClass().getSimpleName().equals("BackgroundObject")) {
			this.setIsDestroyed(true);
		}
		
	}

	/** Getters and setters. */ 
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
}



