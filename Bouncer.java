//: Tanks/Bouncer.java

package tanks;

/**
 * Game object representing weapon called Bouncer. Bouncer 
 * bounces few times before destruction. Contains data needed
 * for displaying and calculating of movement.
 * Extends Missile.
 * Overrides: 
 * Implements: inCollision(GameObject other)
 * @author Michal Czop
 */

public class Bouncer extends Missle {
	
	/** Value of possible bounce directions. */
	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}
	
	/** Value indicating if bomb is in bouncing state. */
	private boolean isBouncing;	

	/** Multiplier for velocity after bouncing. */
	private double bounceFactor; 

	/** How many times the missile will bounce. */
	private int bounceNumber; 
	
	/**
	 * Constructor.
	 * @param game - game instance
	 * @param x - position on x axis
	 * @param y - position on y axis
	 * @param velocity - abs value of current velocity
	 * @param angle - value of current angle
	 */
	public Bouncer(Game game, int x, int y, double velocity, double angle) {
		super(game, x, y, velocity, angle);
		setIsBouncing(false);
		bounceFactor = 0.6; 
		bounceNumber = 2; 
	}

	/**
	 * Calculates velocity and angle after bounce.
	 * @param d - direction on bounce
	 * @param colidedObject - object causing bounce
	 */
	private void bounce(Direction d, GameObject colidedObject) {
		switch(d) {
			case LEFT: break; // No such situation in the game
			case RIGHT: break; // No such situation in the game
			case UP: angle = 360-angle; velocity = velocity*bounceFactor; y=colidedObject.getY()-this.shape.height; break;
			case DOWN: break; // No such situation in the game
		}
	}
	
	/** 
	 * Determines actions performed after collision 
	 * with other objects.
	 * @param other - GameObject which collides with this
	*/
	@Override
	void inCollision(GameObject other) {
		if (other.getClass().getSimpleName().equals("BackgroundObject")) {
			if(((BackgroundObject)other).getCausesBounce() && bounceNumber>0) {
				bounce(Direction.UP, other);
				bounceNumber--;
			} else {
				isDestroyed = true;
			}
		}

		if (other.getClass().getSimpleName().equals("Tank")) {
			isDestroyed = true;
			other.setIsDestroyed(true);
		}
	}
	
	/** Getters and setters. */

	public boolean getIsBouncing() {
		return isBouncing;
	}

	public void setIsBouncing(boolean isBouncing) {
		this.isBouncing = isBouncing;
	}
}
