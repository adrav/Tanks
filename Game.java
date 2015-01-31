//: Tanks/Game.java

package tanks;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Gameplay class.
 * Extends Canvas.
 * @author Michal Czop
 */

public class Game extends Canvas {

	private BufferStrategy strategy;

	/** Objects taking part in gameplay. */
	private GameObject player;
	private GameObject player1;
	private GameObject player2;
	private GameObject ground;
	private GameObject wall;
	private GameObject leftBrick1;
	private GameObject leftBrick2;
	private GameObject rightBrick1;
	private GameObject rightBrick2;
	
	/** Object holding references to all objects taking part in gameplay. */
	private ObjectHolder objects;
	
	/** Object responsible for counting down time of round. */
	private TimeBar timeBar; 
	
	/** Holder of object to delete after current iteration. */
	private ArrayList<GameObject> objectsToDelete = new ArrayList<GameObject>();
	
	/** Boolean flags. */
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean shiftPressed = false;
	private boolean firePressed = false;
	private boolean enterPressed = false;
	private boolean zPressed = false;
	private boolean xPressed = false;
	private boolean gameOver = false;
	private boolean initLevel = true;
	private boolean newRound = true;
	private boolean timerCounting;
	private boolean missleFired = false;
	private boolean suicide = false;
	
	private Weapon playerWeapon;
	
	int playerNumber; 
	int windPower;
	
	/** Values for handling round time. */
	private long roundTime = 15000;
	private long lastLoopTime;

	/** String object representing direction and vlocity of wind. */
	String windImage;
	
	private Font bigFont = new Font("SANS_SERIF", Font.BOLD, 36);
	private Font smallFont = new Font("SANS_SERIF", Font.ITALIC, 16);

	/** State machine variable. */
	private String state;
	
	/** Variable for randomizing wind velocity. */
	private Random rand = new Random();

	/** Constructor. */
	public Game() {
		
		/** Setting graphics for game. */
		setBounds(0,0,1200,600);
		setIgnoreRepaint(true);
		requestFocus();

		JFrame mainWindow = new JFrame("Tanks (written by Micha¸ Czop)");
		
		JPanel panel = (JPanel) mainWindow.getContentPane();
		panel.setPreferredSize(new Dimension(1200,600));
		panel.setLayout(null);
		panel.add(this);
		
		mainWindow.pack();
		mainWindow.setResizable(false);
		mainWindow.setVisible(true);

		/** Buffer strategy for displaying graphics. */
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		mainWindow.addKeyListener(new KeyPressedHandler());
		
		mainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		/** Creating game objects. */ 
		objects = ObjectHolder.getInstance();
		player1 = new Tank(this, 200, 564, 2, 2, 99, 315, 250);
		player2 = new Tank(this, 980, 564, 2, 2, 99, 225, 250);
		timeBar = new TimeBar(20, 20, 300, 20, new Color(0, 255, 0), roundTime);
		ground = new BackgroundObject(this, 0, 580, new Rectangle (1200, 30), true);
		ground.setColor(new Color(0, 255, 0));
		wall = new BackgroundObject(this, 585, 330, new Rectangle (30, 250), false);
		wall.setColor(new Color(155, 0, 0));
		leftBrick1 = new BackgroundObject(this, 0, 570, new Rectangle (10, 10), false);
		leftBrick1.setColor(new Color(255, 0, 0));
		leftBrick2 = new BackgroundObject(this, 450, 570, new Rectangle (10, 10), false);
		leftBrick2.setColor(new Color(255, 0, 0));
		rightBrick1 = new BackgroundObject(this, 730, 570, new Rectangle (10, 10), false);
		rightBrick1.setColor(new Color(255, 0, 0));
		rightBrick2 = new BackgroundObject(this, 1190, 570, new Rectangle (10, 10), false);
		rightBrick2.setColor(new Color(255, 0, 0));
		objects.addObject(player1);
		objects.addObject(player2);
		objects.addObject(ground);
		objects.addObject(wall);
		objects.addObject(leftBrick1);
		objects.addObject(leftBrick2);
		objects.addObject(rightBrick1);
		objects.addObject(rightBrick2);
		timeBar.setEndOfTime(true);
		state = "game intro";
		((Tank) player1).setDirection("right");
		((Tank) player2).setDirection("left");
		randomizeWindPower();
		
	}
	
	/** Internal class for handling key events. */
	private class KeyPressedHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) { leftPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) { rightPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_UP) { upPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_DOWN) { downPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_SHIFT) { shiftPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_SPACE) { firePressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_ENTER) { enterPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_Z) { zPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_X) { xPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { System.exit(0); }
		}

		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {leftPressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {rightPressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_UP) { upPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_DOWN) { downPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_SHIFT) { shiftPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {firePressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_Z) { zPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_X) { xPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {enterPressed = false;}
		}
	}
	
	/** Introducing screen of game. */
	public void gameIntro() {
		while(!enterPressed) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setFont(bigFont);
			g.setColor(Color.black);
			g.fillRect(0,0,1200,600);
			g.setColor(Color.white);
			g.drawString("Press Enter to start", 200, 250);
			g.setFont(smallFont);
			g.drawString("Z / X - movement", 200, 300);
			g.drawString("UP / DOWN - angle", 200, 320);
			g.drawString("LEFT / RIGHT - power", 200, 340);
			g.drawString("SHIFT - change weapon", 200, 360);
			g.drawString("SPACE - shot", 200, 380);
			g.dispose();
			strategy.show();
		}
		state = "game";
	}
	
	/** Switching between plaers. */
	public void changePlayer() {
		if (playerNumber == 1) {
			player1.setSpeedX(0);
			playerNumber = 2;
			player = player2;
		} else {
			player2.setSpeedX(0);
			playerNumber = 1;
			player = player1;
		}
		timeBar.reset();
		timeBar.startCounting();
		playerWeapon = (((Tank) player).getWeapon());
		missleFired = false;
	}
	
	/** When tank changes its direction, mirrorPlayerAngle(Tank p) adapts parameters of turret.*/
	public void mirrorPlayerAngle(Tank p) {
		p.setAngle(540 - p.getAngle());
	}
	
	/** Random wind velocity. */
	public void randomizeWindPower() {
		windPower = rand.nextInt(12) * 500 - 3000;
		switch (windPower) {
			case -3000 : windImage = "------>"; break;
			case -2500 : windImage = "----->"; break;
			case -2000 : windImage = "---->"; break;
			case -1500 : windImage = "--->"; break;
			case -1000 : windImage = "-->"; break;
			case -500 : windImage = "->"; break;
			case 0 : windImage = ""; break;
			case 500 : windImage = "<-"; break;
			case 1000 : windImage = "<--"; break;
			case 1500 : windImage = "<---"; break;
			case 2000 : windImage = "<----"; break;
			case 2500 : windImage = "<-----"; break;
			case 3000 : windImage = "<------"; break;
		}
	}
		
	/** Main loop of game. */
	public void gameLoop() throws axisException {

		while (!gameOver) {
			/** Calculations for loop timing. */
			long deltaTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			/** Preparing main window appearance. */
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,1200,600);
			
			/** Switching player. */
			if(timeBar.isEndOfTime()) {
				changePlayer();
			}
			
			/** Changing weapon.*/
			if(shiftPressed) {
				((Tank) player).changeWeapon();
				playerWeapon = (((Tank) player).getWeapon());
				shiftPressed = false;
				
			}
			
			/** Adjust size of timebar. */
			if(!missleFired) { timeBar.adjustSizeToTimeLeft(lastLoopTime, "x"); }
			
			/** Calculate angle of turret. */
			if(((Tank) player).getDirection().equals("right")) {
				if(upPressed && ((Tank) player).getAngle() > 270 && !missleFired) {
					((Tank) player).setAngle(((Tank) player).getAngle() - 0.5);
				}
				if(downPressed && ((Tank) player).getAngle() < 360 && !missleFired) {
					((Tank) player).setAngle(((Tank) player).getAngle() + 0.5);
				}
			}
			if(((Tank) player).getDirection().equals("left")) {
				if(upPressed && ((Tank) player).getAngle() < 270 && !missleFired) {
					((Tank) player).setAngle(((Tank) player).getAngle() + 0.5);
				}
				if(downPressed && ((Tank) player).getAngle() > 180 && !missleFired) {
					((Tank) player).setAngle(((Tank) player).getAngle() - 0.5);
				}
			}
			
			/** Calculate power of shot. */
			if(leftPressed && ((Tank) player).getPower() > 0 && !missleFired) {
				((Tank) player).setPower(((Tank) player).getPower()-1);
			}
			if(rightPressed && ((Tank) player).getPower() < 500 && !missleFired) {
				((Tank) player).setPower(((Tank) player).getPower()+1);
			}
			
			/** Handle movement of player.*/
			player.setSpeedX(0);
			if (zPressed && !xPressed) {
				if(((Tank) player).getDirection().equals("right") && !missleFired) {
					mirrorPlayerAngle((Tank) player);
				}
				player.setSpeedX(-(player.getDefaultSpeedX()));
				((Tank) player).setDirection("left");
			}
			if (!zPressed && xPressed) {
				if(((Tank) player).getDirection().equals("left") && !missleFired) {
					mirrorPlayerAngle((Tank) player);
				}
				player.setSpeedX(player.getDefaultSpeedX());
				((Tank) player).setDirection("right");
			}
			
			/** Handle fire. */
			if (firePressed && !missleFired) {
				if (((Tank) player).getWeaponAmount(((Tank) player).getWeapon()) > 0) {
					if (((Tank) player).getWeapon().equals(Weapon.MISSLE)) {
						objects.addObject(new Missle(this, (int)player.getX()+15, (int)player.getY()-10, ((Tank)player).getPower(), ((Tank) player).getAngle()));
					} else if ((((Tank) player).getWeapon().equals(Weapon.BOMB))) {
						objects.addObject(new Bomb(this, (int)player.getX()+15, (int)player.getY()-10, ((Tank)player).getPower(), ((Tank) player).getAngle()));
					} else if (((Tank) player).getWeapon().equals(Weapon.BOUNCER)) {
						objects.addObject(new Bouncer(this, (int)player.getX()+15, (int)player.getY()-10, ((Tank)player).getPower(), ((Tank) player).getAngle()));
					}
					missleFired = true;
					timerCounting = false;
					((Tank) player).setWeaponAmount(playerWeapon, ((Tank) player).getWeaponAmount(playerWeapon)-1);
				}
			}

			/** Perform movement of game objects. */
			for (int i = 0; i<objects.getSize(); i++) {
				if (!objects.getObject(i).getClass().getSimpleName().equals("Tank") || !missleFired) {
					objects.getObject(i).move(deltaTime); 
				}
			}
			
			/** Draw game objects. */
			for (int i = 0; i<objects.getSize(); i++) {
				g.setColor(objects.getObject(i).getColor());
				objects.getObject(i).draw(g);
			}
			g.setColor(timeBar.getColor());
			timeBar.draw(g);
			
			/** Collision check. */
			for (int i1 = 0; i1 < objects.getSize(); i1++) {
				for(int i2 = i1+1; i2 < objects.getSize(); i2++) {
					if(objects.getObject(i1).collisionCheck(objects.getObject(i2))) {
						objects.getObject(i1).inCollision(objects.getObject(i2));
					}
				}	
			}
			
			/** Deleting of destroyed game objects. */
			for (int i = 0; i < objects.getSize(); i++) {
				if (objects.getObject(i).getIsDestroyed()) {
					objectsToDelete.add(objects.getObject(i));
					if (objects.getObject(i).getClass().getSimpleName().equals("Missle") || objects.getObject(i).getClass().getSuperclass().getSimpleName().equals("Missle")){
							changePlayer();
					}
					if (objects.getObject(i).getClass().getSimpleName().equals("Tank")) {
						state = "gameOver";
						gameOver = true;
						if (objects.getObject(i)==player) {
							suicide = true;
							System.out.println("suicide");
						}
					}
				}
			}
			objects.deleteObject(objectsToDelete);
			
			/** Draw information in status bar. */
			g.setColor(new Color(255, 0, 0));
			g.drawString("angle = " + ((Tank)player).getAngle(), 350, 35);
			g.drawString("power = " +((Tank)player).getPower(), 450, 35);
			if (playerNumber == 1) { g.drawString("Player 1" , 550, 35); }
			if (playerNumber == 2) { g.drawString("Player 2" , 550, 35); }
			g.drawString("wind: " + windImage, 620, 35);
			g.drawString("weapon: " + playerWeapon + " - " + ((Tank) player).getWeaponAmount(playerWeapon) + " left.", 720, 35);
			
			/** Check end condition. */
			if (gameOver) {
				playerNumber = (suicide)? playerNumber : (playerNumber == 1)? 2:1;
				g.setColor(Color.black);
				g.fillRect(0,0,1200,600);
				g.setColor(Color.white);
				g.setFont(bigFont);
				g.drawString("Player " + playerNumber + " won!", 280, 250);
				g.setFont(smallFont);
				g.drawString("Press Escape to exit", 320, 300);
				g.dispose();
				strategy.show();
			}
			
			/** Draw graphics. */
			g.dispose();
			strategy.show();
			
			/** Wait 20ms. */
			try { 
				Thread.sleep(20); 
			} catch (Exception e) {}
		}
	}

	public static void main(String args[]) throws axisException {
		Game game = new Game();
		while(!game.gameOver) {
			if (game.state.equals("game")) { game.gameLoop(); }
			if (game.state.equals("game intro")) { game.gameIntro(); }
		}
	}
}