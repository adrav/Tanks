package tanks;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BackgroundObject extends GameObject{
	
	public BackgroundObject(Game game, int x, int y, Rectangle shape) {
		super(game, x, y);
		this.shape = shape;
	}

	public void draw(Graphics2D g) {
		g.fillRect((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
	}
	
	public void inCollision(GameObject other) {
		if(other.getClass().getSimpleName().equals("Missle")) {
			other.setIsDestroyed(true);
		}
		
	}

}
