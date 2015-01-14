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
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {

	private BufferStrategy strategy;

	private GameObject player;
	private ObjectHolder objects;
	
	// Fields for status of keys
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean tabPressed = false;
	private boolean firePressed = false;
	private boolean enterPressed = false;
	private boolean gameOver = false;
	private boolean logicNeeded = false;
	private boolean initLevel = true;
	private boolean newRound = true;
	
	private long roundTime = 2000;
	private long lastLoopTime;

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
		player = new Tank(this, 380, 550, 2, 2, -1);
		objects.addObject(player);
		
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
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { System.exit(0); }
		}

		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {leftPressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {rightPressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_UP) { upPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_DOWN) { downPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_TAB) { tabPressed = false; }
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {firePressed = false;}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {enterPressed = false;}
		}
	}
		
	public void gameLoop() {

		while (!gameOver) {

			long deltaTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			
			//Handle movement of player
			player.setSpeedX(0);
			if (leftPressed && !rightPressed) {
				player.setSpeedX(-(player.getDefaultSpeedX()));
			}
			if (!leftPressed && rightPressed) {
				player.setSpeedX(player.getDefaultSpeedX());
			}

			// Perform movement of GameObject's
			for (int i = 0; i<objects.getSize(); i++) {
				objects.getObject(i).move(deltaTime);
			}
			
			//Draw GameObject's
			for (int i = 0; i<objects.getSize(); i++) {
				g.setColor(objects.getObject(i).getColor());
				objects.getObject(i).draw(g);
			}
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