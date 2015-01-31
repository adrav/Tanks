//: Tanks/Bomb.java

package tanks;

import java.awt.Color;
import java.awt.Rectangle;

/**
 * Game object representing weapon called Bomb. Bomb explodes
 * after collision. Contains data needed
 * for displaying and calculating of movement.
 * Extends Missile.
 * Overrides: draw(Graphics2D g), move(long deltaTime)
 * Implements: inCollision(GameObject other)
 * @author Michal Czop
 */

public class Bomb extends Missle {

	/** Value indicating if bomb is in explosion state. */
	private boolean isExploding;

	/** Determines how many times bomb size will be multiplied
	 * after explosion.
	 */
	private double explosionFactor;

	/** Determines how long will explosion last. */
	private long explosionTime;

	/** Current system time. */
	private long currentTime;

	/** Time lapsed since last iteration. */
	private long deltaTime;

	/** Determines how long will explosion last. */
	private long explosionDurationMilis;

	/** Factor for calculating size of explosion. */
	private double tempFactor;
	
	/**
	 * Constructor.
	 * @param game - game instance
	 * @param x - position on x axis
	 * @param y - position on y axis
	 * @param velocity - abs value of current velocity
	 * @param angle - value of current angle
	 */
	public Bomb(Game game, int x, int y, double velocity, double angle) {
		super(game, x, y, velocity, angle);
		isExploding=false;
		explosionFactor = 10; 
		explosionDurationMilis = 2000;
		color = new Color(255, 0, 0);
	}
	
	/** 
	 * Handles movement of missile basing on data 
	 * calculated by Physics class.
	 * @param deltaTime - time lapsed since last iteration
	 */
	@Override
	public void move (long deltaTime ) {
		if (!isExploding) {
			super.move(deltaTime);
		}
		else {
			animate();
		}
	}
	
	/** 
	 * Changes apperance of bomb during explosion
	 * by changing its size.
	 */
	private void animate() {
		currentTime = System.currentTimeMillis();
		deltaTime = currentTime - explosionTime;
		tempFactor = (double) deltaTime / explosionDurationMilis;
		refactorSize((tempFactor*explosionFactor < 1)? 1 : tempFactor*explosionFactor);
		if (tempFactor > 1) {
			isExploding = false;
			isDestroyed = true;
		}
	}
	
	/** 
	 * Calculates size of bomb during explosion.
	 */
	private void refactorSize(double factor) {
		double newWidth = defaultShape.width * factor;
		double newHeight = defaultShape.height * factor;
		double newX = (x - (newWidth - shape.width)/2+0.1);
		double newY = (y - (newHeight - shape.height)/2+0.1);
		x = newX;
		y = newY;
		shape = new Rectangle((int)x, (int)y, (int)newWidth, (int)newHeight);
	}
	
	/** 
	 * Determines actions performed after collision 
	 * with other objects.
	 * @param other - second object
	*/
	@Override
	void inCollision(GameObject other) {
		if (other.getClass().getSimpleName().equals("BackgroundObject") && !isExploding) {
			isExploding = true;
			explosionTime = System.currentTimeMillis();
		}

		if (other.getClass().getSimpleName().equals("Tank") && !isExploding) {
			isDestroyed = true;
			other.setIsDestroyed(true);
		}
	}
}
