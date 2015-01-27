package tanks;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BackgroundObject extends GameObject{
	
	private boolean causesBounce;
	
	public BackgroundObject(Game game, int x, int y, Rectangle shape, boolean causesBounce) {
		super(game, x, y);
		this.shape = shape;
		this.causesBounce = causesBounce;
		
	}

	public void draw(Graphics2D g) {
		g.fillRect((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
	}
	
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

	public boolean getCausesBounce() {
		return causesBounce;
	}

	public void setCausesBounce(boolean causesBounce) {
		this.causesBounce = causesBounce;
	}
}
