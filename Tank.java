package tanks;

import java.awt.Color;

import static java.lang.Math.*;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.HashMap;


public class Tank extends GameObject {

	// Value of last used power of shot
	private int power;

	// Value of tangens of last used angle of turret
	private double angle;

	// Amounts of veapons left
	private int bouncersLeft;
	private int bombsLeft;

	// Infinite missles
	private int misslesLeft;
	
	// Common for all tanks shaps
	private Polygon leftTankShape;
	private Polygon rightTankShape;
	
	private String direction;
	
	//public Polygon shape;
	
	private int dx, dy;

	// Information of weapons left. List of pairs (name, amount).
	
	private HashMap<String, Integer> weapons = new HashMap<String, Integer>();
	
	public Tank(Game game, int x, int y, int bouncers, int bombs, int missles, double angle, int power) {
		super(game, x, y);
		defaultSpeedX = 100;
		defaultSpeedY = 0;
		speedX = 0;
		speedY = 0;
		color = new Color(0, 0, 255);
		int[] xPoints = {21, 42, 13};
		int[] yPoints = {10, 11, 12};
		//shape = new Polygon(xPoints, yPoints, 3);
		shape = new Rectangle(x, y, 30, 30);
		this.power = power;
		this.angle = angle;
		bouncersLeft = bouncers;
		bombsLeft = bombs;
		setMisslesLeft(missles); // -1 = Infinite
		isDestroyed=false;

		this.weapons.put("Bouncers", bouncers);
		this.weapons.put("Bombs", bombs);
		this.weapons.put("Missles", missles);
	
	}
	
	public HashMap<String, Integer> getWeapons() {
		return weapons;
	}
	
	public void deleteWeapon(String weapon) {
			weapons.remove(weapon);
	}

	public void move(long deltaTime) {
		if (x<30 && speedX<0) {
			speedX = 0;
		}
		if (x>1140 && speedX>0) {
			speedX = 0;
		}
		super.move(deltaTime);
	}

	public void inCollision(GameObject other) {
		if(other.getClass().getSimpleName().equals("Missle")) {
			other.setIsDestroyed(true);
			this.setIsDestroyed(true);
		}
		
		if(other.getClass().getSimpleName().equals("Tank")) {
			other.setIsDestroyed(true);
			this.setIsDestroyed(true);
		}
		
		if(other.getClass().getSimpleName().equals("BackgroundObject")) {
			other.setIsDestroyed(true);
			this.setIsDestroyed(true);
		}
	}
	
	public void draw(Graphics2D g) {
		g.fillRect((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
		// Draw line
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

	public Polygon getLeftTankShape() {
		return leftTankShape;
	}

	public void setLeftTankShape(Polygon leftTankShape) {
		this.leftTankShape = leftTankShape;
	}

	public Polygon getRightTankShape() {
		return rightTankShape;
	}

	public void setRightTankShape(Polygon rightTankShape) {
		this.rightTankShape = rightTankShape;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}