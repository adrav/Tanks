package tanks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

abstract public class GameObject {
	
	// Instance of current Game used for communication
	protected Game gameInstance;

	// Creation of id number for each object
	protected static int idCounter;
	protected final int id = idCounter++;
	
	protected int timesHit;
	
	// Coordinates of object. Double type protects good resolution of motion.
	protected double x;
	protected double y;
	
	// Default speed of Game Objects
	protected int defaultSpeedX;
	protected int defaultSpeedY;
	
	// Speed in pixels/second
	protected int speedX;
	protected int speedY;
	
	// Color and shape for object
	protected Color defaultColor;
	protected Color color;
	protected Rectangle defaultShape;
	protected Rectangle shape;
	
	// Data used for collision check
	protected Rectangle myShape = new Rectangle();
	protected Rectangle otherShape = new Rectangle();
	
	// Status of object. Set by collision check. If destroyed, object should be 
	// removed from ObjectHolder
	protected boolean isDestroyed = false;
	
	// Constructor
	public GameObject(Game game, int x, int y) {
		this.gameInstance = game;
		this.x = x;
		this.y = y;
	}
	
	public void performLogic() {}
	
	// Setters and getters for cordinates
	public void setX (double x) {
		this.x = x;
	}
	public void setY (double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	// Setters and getters for speed
	public void setDefaultSpeedX (int defaultSpeedX) {
		this.defaultSpeedX = defaultSpeedX;
	}
	public void setDefaultSpeedY (int defaultSpeedY) {
		this.defaultSpeedY = defaultSpeedY;
	}
	public int getDefaultSpeedX() {
		return defaultSpeedX;
	}
	public int getDefaultSpeedY() {
		return defaultSpeedY;
	}
	public void setSpeedX (int speedX) {
		this.speedX = speedX;
	}
	public void setSpeedY (int speedY) {
		this.speedY = speedY;
	}
	public int getSpeedX() {
		return speedX;
	}
	public int getSpeedY() {
		return speedY;
	}
	
	public void setShape(Rectangle shape) {
		this.shape = shape;
	}
	public Rectangle getShape() {
		return shape;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	
	// Setter and getter for boolean variables
	public void setIsDestroyed (boolean status) {
		this.isDestroyed = status;
	}
	public boolean getIsDestroyed () {
		return isDestroyed;
	}
	
	public void setTimesHit(int timesHit) {
		this.timesHit = timesHit;
	}
	
	public int getTimesHit() {
		return timesHit;
	}
	
	// Change coordinates dependent on time passed
	public void move(long deltaTime) {
		x += (speedX*deltaTime)/1000;
		y += (speedY*deltaTime)/1000;
	}
	
	public boolean collisionCheck (GameObject other) {
		myShape.setBounds((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
		otherShape.setBounds((int)other.getX(), (int)other.getY(), (int)other.getShape().getWidth(), (int)other.getShape().getHeight());
		return myShape.intersects(otherShape);
	}
	
	public void draw(Graphics2D g) {
		g.fillRect((int)x, (int)y, (int)shape.getWidth(), (int)shape.getHeight());
	}
	
	abstract void inCollision(GameObject other);
}
