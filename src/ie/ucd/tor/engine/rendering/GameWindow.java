package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.core.components.Texture;
import ie.ucd.tor.engine.maths.Point2D;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public static final int TARGET_FPS = 100;

	private final JFrame frame;
	private final JLayeredPane gamePanel;
	private final GamePanel spriteRenderer;
	private final GamePanel uiRenderer;
	private final GamePanel backgroundRenderer;

	public GameWindow() {

		// Create application window frame
		frame = new JFrame("Dungeon Survival");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setLayout(null);

		// Game Panel to draw all layers on
		gamePanel = new JLayeredPane();
		gamePanel.setBounds(0, 0, WIDTH, HEIGHT);

		// Create layer panes for different game layers
		uiRenderer = new GamePanel();
		spriteRenderer = new GamePanel();
		backgroundRenderer = new GamePanel();

		panelTest();

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

	public void play() {
		while (true) {
			int timeBetweenFrames = 1000 / TARGET_FPS;
			long frameCheck = System.currentTimeMillis() + (long) timeBetweenFrames;

			while (frameCheck > System.currentTimeMillis()) {
				// wait until next frame time
			}

			updateGamePanel();

			CheckFrameRate(System.currentTimeMillis(), frameCheck, TARGET_FPS);

		}
	}

	private void updateGamePanel() {
		spriteRenderer.Update();
		uiRenderer.Update();
	}

	private static void CheckFrameRate(long TargetTime, long DeliveredTime, int TargetFPS) {
		int TimeBetweenFrames = 1000 / TargetFPS;
		if ((TargetTime - DeliveredTime) > TimeBetweenFrames) {
			System.out.println("FPS failure by 10 m");
			System.out.println("Frame was late by  " + (TargetTime - DeliveredTime) + " ms");
		}
	}

	private void panelTest() {
		// testing game rendering panels
		GameObject backgroundTest = new GameObject();
		backgroundTest.getTransform().setScale(new Point2D(1000, 1000));
		backgroundTest.addComponent(new Texture("res/BackgroundPlaceholder.png"));

		GameObject uiTest = new GameObject();
		uiTest.getTransform().setScale(new Point2D(1000, 1000));
		uiTest.addComponent(new Texture("res/UIPlaceholder.png"));

		GameObject spriteTest = new GameObject();
		spriteTest.getTransform().setPosition(new Point2D(500 , 500));
		spriteTest.getTransform().setScale(new Point2D(32, 32));
		spriteTest.addComponent(new Texture("res/TexturePlaceholder.png", 0, false));

		GameObject spriteTest2 = new GameObject();
		spriteTest2.getTransform().setPosition(new Point2D(516 , 500));
		spriteTest2.getTransform().setScale(new Point2D(32, 32));
		spriteTest2.addComponent(new Texture("res/TexturePlaceholder.png", 1, false));

		GameObject spriteTest3 = new GameObject();
		spriteTest3.getTransform().setPosition(new Point2D(532 , 500));
		spriteTest3.getTransform().setScale(new Point2D(32, 32));
		spriteTest3.addComponent(new Texture("res/TexturePlaceholder.png", 2, false));

		backgroundRenderer.addElement(backgroundTest);
		uiRenderer.addElement(uiTest);
		spriteRenderer.addElement(spriteTest);
		spriteRenderer.addElement(spriteTest2);
		spriteRenderer.addElement(spriteTest3);


	}

}
