package tanks;

public class Bouncer extends Missle {
	
	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	private boolean isBouncing;	
	private double bounceFactor; // Multiplier for velocity after bouncing;
	private int bounceNumber; // how many times the missle will bounce
	
	public Bouncer(Game game, int x, int y, double velocity, double angle) {
		super(game, x, y, velocity, angle);
		isBouncing = false;
		bounceFactor = 0.6; // velocity after bounce = 0.3* vel before bounce
		bounceNumber = 2; // After one bounce, the missle will be destroyed
	}
	
	private void bounce(Direction d, GameObject colidedObject) {
		switch(d) {
			case LEFT: break; // No such situation in the game
			case RIGHT: break; // No such situation in the game
			case UP: angle = 360-angle; velocity = velocity*bounceFactor; y=colidedObject.getY()-this.shape.height; break;
			case DOWN: break; // No such situation in the game
		}
	}
	
	@Override
	void inCollision(GameObject other) {
		if (other.getClass().getSimpleName().equals("BackgroundObject")) {
			if(((BackgroundObject)other).getCausesBounce() && bounceNumber>0) {
				bounce(Direction.UP, other);
				bounceNumber--;
				System.out.println("bounce");
			} else {
				isDestroyed = true;
			}
		}

		if (other.getClass().getSimpleName().equals("Tank")) {
			isDestroyed = true;
			other.setIsDestroyed(true);
		}
	}
}
