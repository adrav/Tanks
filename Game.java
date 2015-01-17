package tanks;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {

	private BufferStrategy strategy;

	private GameObject player;
	private GameObject player1;
	private GameObject player2;
	private ObjectHolder objects;
	private TimeBar timeBar; 
	
	private ArrayList<GameObject> objectsToDelete = new ArrayList<GameObject>();
	
	// Fields for status of keys
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean tabPressed = false;
	private boolean firePressed = false;
	private boolean enterPressed = false;
	private boolean zPressed = false;
	private boolean xPressed = false;
	private boolean gameOver = false;
	private boolean logicNeeded = false;
	private boolean initLevel = true;
	private boolean newRound = true;
	private boolean timerCounting;
	private boolean missleFired = false;
	
	private long roundTime = 2000;
	private long lastLoopTime;
	
//	private double angle; 
	private double velocity; 
//	private double angle1 = 315; 
//	private double angle2 = 315; 
//	private double velocity1 = 250; 
//	private double velocity2 = 250; 
	int playerNumber = 2; 

	private String state;

	
	public Game() {
		
		setBounds(0,0,800,600);
		setIgnoreRepaint(true);
		requestFocus();

		JFrame mainWindow = new JFrame("Tanks (written by Micha¸ Czop)");
		
		JPanel panel = (JPanel) mainWindow.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		panel.add(this);
		
		mainWindow.pack();
		mainWindow.setResizable(false);
		mainWindow.setVisible(true);

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		mainWindow.addKeyListener(new KeyPressedHandler());
		
		mainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		objects = ObjectHolder.getInstance();
		player1 = new Tank(this, 180, 550, 2, 2, -1, -45, 250);
		player2 = new Tank(this, 580, 550, 2, 2, -1, -135, 250);
		timeBar = new TimeBar(20, 20, 300, 20, new Color(0, 255, 0), 20000);
		objects.addObject(player1);
		objects.addObject(player2);
		timeBar.setEndOfTime(true);
		
	}
	
	private class KeyPressedHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) { leftPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) { rightPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_UP) { upPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_DOWN) { downPressed = true; }
			if(e.getKeyCode() == KeyEvent.VK_TAB) { tabPressed = true; }
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
			if(e.getKeyCode() == KeyEvent.VK_TAB) { tabPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {firePressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_Z) { zPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_X) { xPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {enterPressed = false;}
		}
	}
	
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
		missleFired = false;
	}
		
	public void gameLoop() {

		while (!gameOver) {

			long deltaTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			
			//Change player
			if(timeBar.isEndOfTime()) {
				changePlayer();
			}
			
			//Addjust size of imeBar
			if(!missleFired) { timeBar.adjustSizeToTimeLeft(lastLoopTime, "x"); }
			
			//Handle angle
			if(upPressed && ((Tank) player).getAngle() > -180 && !missleFired) {
					((Tank) player).setAngle(((Tank) player).getAngle() - 0.5);
			}
			if(downPressed && ((Tank) player).getAngle() < 0 && !missleFired) {
					((Tank) player).setAngle(((Tank) player).getAngle() + 0.5);
			}
			
			//Handle power
			if(leftPressed && ((Tank) player).getPower() > 0 && !missleFired) {
				((Tank) player).setPower(((Tank) player).getPower()-1);
			}
			if(rightPressed && ((Tank) player).getPower() < 500 && !missleFired) {
				((Tank) player).setPower(((Tank) player).getPower()+1);
			}
			
			//Handle movement of player
			player.setSpeedX(0);
			if (zPressed && !xPressed) {
				player.setSpeedX(-(player.getDefaultSpeedX()));
			}
			if (!zPressed && xPressed) {
				player.setSpeedX(player.getDefaultSpeedX());
			}
			
			//	FIRE HANDLING

			if (firePressed && !missleFired) {
				objects.addObject(new Missle(this, (int)player.getX()+12, (int)player.getY()-50, ((Tank)player).getPower(), ((Tank) player).getAngle()));
				missleFired = true;
				timerCounting = false;
			}

			// Perform movement of GameObject's
			for (int i = 0; i<objects.getSize(); i++) {
				objects.getObject(i).move(deltaTime);
			}
			
			// Deleting of destroyed GameObject's
			for (int i = 0; i < objects.getSize(); i++) {
				if (objects.getObject(i).getIsDestroyed() == true) {
					objectsToDelete.add(objects.getObject(i));
					if (objects.getObject(i).getClass().getSimpleName().equals("Missle")) {
							changePlayer();
							
					}
				}
			}
			objects.deleteObject(objectsToDelete);
			
			//Draw GameObject's
			for (int i = 0; i<objects.getSize(); i++) {
				g.setColor(objects.getObject(i).getColor());
				objects.getObject(i).draw(g);
			}
			g.setColor(timeBar.getColor());
			timeBar.draw(g);
			
			// Draw informations
			g.setColor(new Color(255, 0, 0));
			g.drawString("angle = " + Math.abs(((Tank)player).getAngle()), 350, 35);
			g.drawString("power = " +((Tank)player).getPower(), 450, 35);
			if (playerNumber == 1) { g.drawString("Player 1" , 650, 35); }
			if (playerNumber == 2) { g.drawString("Player 2" , 650, 35); }
			
			g.dispose();
			strategy.show();
			
			try { 
				Thread.sleep(20); 
			} catch (Exception e) {}
		}
	}

	public static void main(String argv[]) {
		Game game = new Game();
		while(!game.gameOver) {
			game.gameLoop();
		}
	}
}