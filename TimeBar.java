//: tanks/TimeBar.java

package tanks;

import java.awt.Color;
import java.awt.Graphics2D;

/** Custom exception for reporting wrong choice of axis 
 * in adjustSizeToTimeLeft (long currentTime, String axis). 
 */

class axisException extends Exception {
	public axisException() {}
	public axisException(String msg) {
		super(msg);
	}
}

/**
 * Counts down the time and adjust size to milliseconds left. 
 * When counting is finished, X or Y size of object is equals 0.
 * 
 * @author Micha¸ Czop
 * 
 */

public class TimeBar {
	
	/** Fields */ 

	private int x;
	private int y;
	
	private double initLength;
	private double initHeight;
	private double length;
	private double height;
	private double sizeFactor;
	
	private long startTime;
	private long time;
	private long timeToCount;
	
	private boolean countingStarted;
	private boolean endOfTime;
	
	private Color color;

	/** Constructor */
	
	public TimeBar(int x, int y, double length, double height, Color color, long timeToCount) {
		this.x = x;
		this.y = y;
		this.initLength = length;
		this.initHeight = height;
		this.length = initLength;
		this.height = initHeight;
		this.color = color;
		this.timeToCount = timeToCount;
		this.time = timeToCount;
		sizeFactor = 1;
		countingStarted = false;
		endOfTime = false;
	}
	
	/** Methods */
	/** 
	 * Starts the counting process.
	 * Sets on the starting time and turns on the flag. 
	 */
	public void startCounting() {
		startTime = System.currentTimeMillis();
		countingStarted = true;
	}
	
	/**
	 * Adjusts size of time bar to time left.
	 * @param currentTime - current system time needed to calculate time lapsed since last iteration
	 * @param axis - axis defining which value (x or y size) will be reduced
	 */
	
	public void adjustSizeToTimeLeft (long currentTime, String axis) throws axisException {

		if(countingStarted) {
			long timeLapsed = currentTime - startTime;
			double sizeFactor = 1.0 - (double)timeLapsed/timeToCount;
			if (sizeFactor <= 0) { 
				sizeFactor = 0; 
				setEndOfTime(true);
			}
			try {
				if (axis.equals("x")) {
					this.length = initLength * sizeFactor;
				} else if (axis.equals("y")) {
					this.height = height * sizeFactor;	
				}
				else {
					throw new axisException("TimeBar.adjustSizeToTimeLeft(long currentTime, String axis): Wrong axis chosen.");
				}
			} catch(axisException e) {
				e.printStackTrace(System.out);
			}	
		} else { System.err.println("TimeBar.adjustSizeToTimeLeft(long currentTime, String axis): Counting not started."); } 
	}

	/**
	 * Resets size and flags to default values.
	 */
	
	public void reset() {

		length = initLength;
		height = initHeight;
		setSizeFactor(1);
		countingStarted = false;
		setEndOfTime(false);		

	}

	/**
	 * Draw time bar
	 * @param g
	 */
	
	public void draw(Graphics2D g) {
		g.fillRect(x, y, (int)length, (int)height);
	}

	/** 
	 * Setters and getters for variables 
	 */

	public void setX (int x) {
		this.x = x;
	}
	public void setY (int y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public void setLength (double length) {
		this.length = length;
	}
	public void setHeight (double height) {
		this.height = height;
	}
	public double getLength() {
		return length;
	}
	public double getHeight() {
		return height;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setTimeToCount(long timeToCount) {
		this.timeToCount = timeToCount;
	}
	public long getTimeToCount() {
		return timeToCount;
	}

	public boolean isEndOfTime() {
		return endOfTime;
	}

	public void setEndOfTime(boolean endOfTime) {
		this.endOfTime = endOfTime;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getSizeFactor() {
		return sizeFactor;
	}

	public void setSizeFactor(double sizeFactor) {
		this.sizeFactor = sizeFactor;
	}
}