package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.events.InputEventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameWindow {

	public static final int WIDTH = 1028;
	public static final int HEIGHT = 1028;
	public static final int TARGET_FPS = 100;

	private final JFrame frame;
	private final JLayeredPane gamePanel;
	private final GamePanel spriteRenderer;
	private final GamePanel uiRenderer;
	private final GamePanel backgroundRenderer;
	private final KeyListener input;

	private static GameWindow instance;

	public static GameWindow getInstance() {
		if (instance == null) {
			instance = new GameWindow();
		}
		return instance;
	}

	public GameWindow() {

		input = InputEventHandler.getInstance();

		// Create application window frame
		frame = new JFrame("Dungeon Survival");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT + 24));
		frame.setLayout(null);

		// Game Panel to draw all layers on
		gamePanel = new JLayeredPane();
		gamePanel.setBounds(0, 0, WIDTH, HEIGHT);

		gamePanel.addKeyListener(input);
		gamePanel.requestFocusInWindow();
		gamePanel.setFocusable(true);

		// Create layer panes for different game layers
		uiRenderer = new GamePanel();
		spriteRenderer = new GamePanel();
		backgroundRenderer = new GamePanel();

		// add game canvases to LayeredPane
		gamePanel.add(backgroundRenderer, Integer.valueOf(0)); 		// Background
		gamePanel.add(spriteRenderer, Integer.valueOf(1));			// Middle ground
 		gamePanel.add(uiRenderer, Integer.valueOf(2));				// Foreground

		// add LayeredPane to frame
		frame.add(gamePanel);

		// start the application window
		frame.pack();
		frame.setVisible(true);

	}

	public void updateRendering() {
		backgroundRenderer.update();
		spriteRenderer.update();
		uiRenderer.update();
	}

	public static void checkFrameRate(long TargetTime, long DeliveredTime) {
		int TimeBetweenFrames = 1000 / TARGET_FPS;
		if ((TargetTime - DeliveredTime) > TimeBetweenFrames) {
			System.out.println("FPS failure by 10 m");
			System.out.println("Frame was late by  " + (TargetTime - DeliveredTime) + " ms");
		}
	}

	public GamePanel getSpriteRenderer() {
		return spriteRenderer;
	}

	public GamePanel getUiRenderer() {
		return uiRenderer;
	}

	public GamePanel getBackgroundRenderer() {
		return backgroundRenderer;
	}

}
