//: Tanks/Tank.java

package tanks;

import java.awt.Color;

import static java.lang.Math.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Game object representing tank controlled by player. 
 * Extends GameObject.
 * Overrides: draw(Graphics2D g), move(long deltaTime)
 * Implements: inCollision(GameObject other)
 * @author Michal Czop
 */

public class Tank extends GameObject {

	/** Value of last used shot power. */
	private int power;

	/** Value of last used angle tangens. */
	private double angle;

	/** Amounts of weapons left. */
	private int bouncersLeft;
	private int bombsLeft;
	private int misslesLeft;
	
	/** Values for calculating turret length and angle. */
	private int dx, dy;
	
	/** Direction of tank movement (left/right). Determines angle of turret. */
	private String direction;
	
	/** Currently chosen weapon. */
	private Weapon weapon;
	
	/** 
	 * Constructor.
	 * @param game - game instance
	 * @param x - position of tank on x axis
	 * @param y - position of tank on y axis
	 * @param bouncers - initial number of bouncers available
	 * @param bombs - initial number of bombs available
	 * @param missiles - initial number of missiles available
	 * @param angle
	 * @param power
	 */
	public Tank(Game game, int x, int y, int bouncers, int bombs, int missiles, double angle, int power) {
		super(game, x, y);
		defaultSpeedX = 100;
		defaultSpeedY = 0;
		speedX = 0;
		speedY = 0;
		color = new Color(0, 0, 255);
		shape = new Rectangle(x, y, 30, 16);
		this.power = power;
		this.angle = angle;
		bouncersLeft = bouncers;
		bombsLeft = bombs;
		misslesLeft = missiles;
		isDestroyed=false;
		weapon = Weapon.MISSLE;
	
	}

	/** 
	 * Handles movement of tank. 
	 * @param deltaTime - time lapsed since last iteration
	 */
	@Override
	public void move(long deltaTime) {
		if (x<0 && speedX<0) {
			speedX = 0;
		}
		if (x>(1200 - shape.width) && speedX>0) {
			speedX = 0;
		}
		super.move(deltaTime);
	}
	
	/** Draws object to Graphics2D object. */
	@Override
	public void draw(Graphics2D g) {
		/** Draw tank. */
		g.fillRect((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
		
		/** Draw turret. */
		if(this.direction.equals("right")) {
			dx = (int) (power*cos(toRadians(360-angle)))/5;
			dy = (int) (power*sin(toRadians(360-angle)))/5;
			g.drawLine((int)x + 15, (int)y, (int)x + 15 + dx , (int)y - dy);
		} else {
			dx = (int) (power*cos(toRadians(angle-180)))/5;
			dy = (int) (power*sin(toRadians(angle-180)))/5;
			g.drawLine((int)x + 15, (int)y, (int)x + 15 - dx , (int)y - dy);
		}
	}

	/** 
	 * Determines actions performed after collision 
	 * with other objects.
	 * @param other - GameObject which collides with this
	*/
	@Override
	public void inCollision(GameObject other) {
		if(other.getClass().getSimpleName().equals("Missle")) {
			other.setIsDestroyed(true);
			this.setIsDestroyed(true);
		}
		if(other.getClass().getSimpleName().equals("Bomb")) {
			other.inCollision(this);
		}
		if(other.getClass().getSimpleName().equals("Bouncer")) {
			other.setIsDestroyed(true);
			this.setIsDestroyed(true);
		}
		
		if(other.getClass().getSimpleName().equals("Tank")) {
			other.setIsDestroyed(true);
			this.setIsDestroyed(true);
		}
		
		if(other.getClass().getSimpleName().equals("BackgroundObject")) {
			if(speedX > 0) {x = other.getX()-shape.width;}
			if(speedX < 0) {x = other.getX() + other.getShape().width;}
		}
	}
	
	/** Getters and setters. */
	public void setPower(int power) {
		this.power = power;
	}

	public int getPower() {
		return power;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

	public void setBouncers(int bouncers) {
		bouncersLeft = bouncers;
	}

	public int getBouncers() {
		return bouncersLeft;
	}

	public void setBombs(int bombs) {
		bombsLeft = bombs;
	}

	public int getBombs() {
		return bombsLeft;
	}

	public int getMisslesLeft() {
		return misslesLeft;
	}

	public void setMisslesLeft(int misslesLeft) {
		this.misslesLeft = misslesLeft;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void changeWeapon() {
		setWeapon(weapon.next());
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon () {
		return weapon;
	}
	
	public int getWeaponAmount(Weapon weapon) {
		switch(weapon) {
			case MISSLE: return misslesLeft; 
			case BOMB: return bombsLeft; 
			case BOUNCER: return bouncersLeft; 
			default: return 0;
		}
	}
	
	public void setWeaponAmount(Weapon weapon, int amount) {
		switch(weapon) {
			case MISSLE: misslesLeft = amount; break; 
			case BOMB: bombsLeft = amount; break; 
			case BOUNCER: bouncersLeft = amount; break; 
			default: break;
		}
	}

}