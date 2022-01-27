package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.core.components.texture.Animator;
import ie.ucd.tor.engine.core.components.texture.Sprite;
import ie.ucd.tor.engine.core.components.texture.data.AnimationData;
import ie.ucd.tor.engine.core.components.texture.data.AnimationDirection;
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

			CheckFrameRate(System.currentTimeMillis(), frameCheck);

		}
	}

	private void updateGamePanel() {
		spriteRenderer.update();
		uiRenderer.update();
	}

	private static void CheckFrameRate(long TargetTime, long DeliveredTime) {
		int TimeBetweenFrames = 1000 / TARGET_FPS;
		if ((TargetTime - DeliveredTime) > TimeBetweenFrames) {
			System.out.println("FPS failure by 10 m");
			System.out.println("Frame was late by  " + (TargetTime - DeliveredTime) + " ms");
		}
	}

	private void panelTest() {
		// testing game rendering panels
		GameObject backgroundTest = new GameObject();
		backgroundTest.addComponent(new Sprite("res/BackgroundPlaceholder.png", 1000, 1000, 0));

		GameObject uiTest = new GameObject();
		uiTest.addComponent(new Sprite("res/UIPlaceholder.png", 1000, 1000, 0));

		GameObject spriteTestA = new GameObject();
		spriteTestA.getTransform().setPosition(new Point2D(500 , 500));
		spriteTestA.addComponent(new Sprite("res/TexturePlaceholder.png", 32, 32, 0));

		GameObject spriteTestB = new GameObject();
		spriteTestB.getTransform().setPosition(new Point2D(516 , 500));
		spriteTestB.addComponent(new Sprite("res/TexturePlaceholder.png", 32, 32, 1));

		GameObject animationTest = new GameObject();
		animationTest.getTransform().setPosition(new Point2D(500 , 600));
		animationTest.addComponent(new Animator());
		animationTest.getComponent(Animator.class).AddAnimation("Test", new AnimationData("res/AnimationPlaceholder.png", 128, 32, 32, 32, AnimationDirection.Horizontal, 4));

		backgroundRenderer.addElement(backgroundTest);
		uiRenderer.addElement(uiTest);
		spriteRenderer.addElement(spriteTestA);
		spriteRenderer.addElement(spriteTestB);
	 	spriteRenderer.addElement(animationTest);

	}

}
