package tanks;

import java.awt.Color;
import java.awt.Graphics2D;

class TimeBar {

	private int x;
	private int y;
	private double initLength;
	private double initHeight;
	private double length;
	private double height;
	private Color color;
	private long startTime;
	private long time;
	private long timeToCount;
	private double sizeFactor;

	private boolean countingStarted;
	private boolean endOfTime;
	
	public TimeBar(int x, int y, double length, double height, Color color, long timeToCount) {

		this.x = x;
		this.y = y;
		this.initLength = length;
		this.initHeight = height;
		this.length = initLength;
		this.height = initHeight;
		this.color = color;
		this.startTime = startTime;
		this.timeToCount = timeToCount;
		this.time = timeToCount;
		sizeFactor = 1;
		countingStarted = false;
		setEndOfTime(false);
		color = new Color(0, 255, 0);

	}

	// Setters and getters for cordinates

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

	// Setters and getters for size

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

	public void startCounting() {
		startTime = System.currentTimeMillis();
		countingStarted = true;
	}

	public void adjustSizeToTimeLeft (long currentTime, String axis) {

		if(countingStarted) {
			long timeLapsed = currentTime - startTime;
			double sizeFactor = 1.0 - (double)timeLapsed/timeToCount;
			if (sizeFactor <= 0) { 
				sizeFactor = 0; 
				setEndOfTime(true);
			}

			if (axis.equals("x")) {

				this.length = initLength * sizeFactor;
			
			} else if (axis.equals("y")) {

				this.height = height * sizeFactor;

			} else {}	
	
		} else { System.out.println("Counting not started."); }  //UWAGA DOPISAŒ OBLUSGE BLEDU
	}

	public void reset() {

		length = initLength;
		height = initHeight;
		sizeFactor = 1;
		countingStarted = false;
		setEndOfTime(false);		

	}

	public void draw(Graphics2D g) {
		g.fillRect(x, y, (int)length, (int)height);
	}

	public boolean isEndOfTime() {
		return endOfTime;
	}

	public void setEndOfTime(boolean endOfTime) {
		this.endOfTime = endOfTime;
	}
	
}