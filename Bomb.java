package tanks;

import java.awt.Color;
import java.awt.Rectangle;

public class Bomb extends Missle {

	private boolean isExploding;
	private double explosionFactor; // How many times bomb size will be multiplied after explosion
	private long explosionTime;
	private long currentTime;
	private long deltaTime;
	private long explosionDurationMilis;
	private double tempFactor;
	
	public Bomb(Game game, int x, int y, double velocity, double angle) {
		super(game, x, y, velocity, angle);
		isExploding=false;
		explosionFactor = 10; // After explosion, size of bomb is multiplied by 5
		explosionDurationMilis = 2000;
		color = new Color(255, 0, 0);
	}
	
	@Override
	public void move (long deltaTime ) {
		if (!isExploding) {
			super.move(deltaTime);
		}
		else {
			animate();
		}
	}
	
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
	
	private void refactorSize(double factor) {
		double newWidth = defaultShape.width * factor;
		double newHeight = defaultShape.height * factor;
		double newX = (x - (newWidth - shape.width)/2+0.1);
		double newY = (y - (newHeight - shape.height)/2+0.1);
		x = newX;
		y = newY;
		shape = new Rectangle((int)x, (int)y, (int)newWidth, (int)newHeight);
	}
	
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
