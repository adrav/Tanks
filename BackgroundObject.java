//: Tanks/BackGroundObject.java

package tanks;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Independent object which appears in game
 * and interacts with other objects. Extends GameObject.
 * Overrides: draw(Graphics2D g)
 * Implements: inCollision(GameObject other)
 * @author Michal Czop
*/

public class BackgroundObject extends GameObject{
	
	/** Field which determines if objects can bounce
	 * from background
	 */
	private boolean causesBounce;
	
	/** Constructor
	 *@param game - game instance
	 *@param x - position on x axis
	 *@param y - position on y axis
	 *@param shape - rectangle defining appearance in game
	 *@param causesBounce - determines if other objects can bounce from this one
	 */
	public BackgroundObject(Game game, int x, int y, Rectangle shape, boolean causesBounce) {
		super(game, x, y);
		this.shape = shape;
		this.causesBounce = causesBounce;
		
	}

	public void draw(Graphics2D g) {
		g.fillRect((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
	}
	
	/** 
	 * Determines actions performed after collision 
	 * with other objects.
	 * @param other - GameObject which collides with this
	*/
	public void inCollision(GameObject other) {
		if(other.getClass().getSimpleName().equals("Missle")) {
			other.inCollision(this);
		}
		if(other.getClass().getSimpleName().equals("Bomb")) {
			other.inCollision(this);
		}
		if(other.getClass().getSimpleName().equals("Bouncer")) {
			other.inCollision(this);
		}
		if(other.getClass().getSimpleName().equals("Tank")) {
			other.inCollision(this);
		}
	}

	/** Getters and setters for fields. */
	public boolean getCausesBounce() {
		return causesBounce;
	}

	public void setCausesBounce(boolean causesBounce) {
		this.causesBounce = causesBounce;
	}
}
