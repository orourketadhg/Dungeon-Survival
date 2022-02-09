package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.rendering.GameWindow;

import static ie.ucd.tor.engine.rendering.GameWindow.TARGET_FPS;

public class GameController {

	protected final GameWindow gameWindow;
	protected final CollisionController collisionController;
	protected final BehaviourController behaviourController;

	public GameController() {
		this.gameWindow = GameWindow.getInstance();
		this.collisionController = CollisionController.getInstance();
		this.behaviourController = BehaviourController.getInstance();

		initialization();
	}

	protected void initialization() {
		// panelTest();
	}

	protected void gameLoop() {
		updateCollisions();

		updateBehaviours();

	}

	public void play() {
		while (true) {
			int timeBetweenFrames = 1000 / TARGET_FPS;
			long frameCheck = System.currentTimeMillis() + (long) timeBetweenFrames;

			while (frameCheck > System.currentTimeMillis()) {
				// wait until next frame time
			}

			gameLoop();

			gameWindow.updateRendering();

			GameWindow.checkFrameRate(System.currentTimeMillis(), frameCheck);

		}
	}

	private void panelTest() {
		GameObject backgroundTest = new GameObject();
		backgroundTest.addComponent(new Sprite("res/BackgroundPlaceholder.png", 1000, 1000, 0));

		GameObject uiTest = new GameObject();
		uiTest.addComponent(new Sprite("res/UIPlaceholder.png", 1000, 1000, 0));

		GameObject spriteTestA = new GameObject();
		spriteTestA.getTransform().setPosition(new Point2D(500 , 500));
		spriteTestA.addComponent(new Sprite("res/TexturePlaceholder.png", 32, 32, 0));
		spriteTestA.addComponent(new Collider(32, 32, Point2D.Zero));

		GameObject spriteTestB = new GameObject();
		spriteTestB.getTransform().setPosition(new Point2D(532 , 500));
		spriteTestB.addComponent(new Sprite("res/TexturePlaceholder.png", 32, 32, 1));
		spriteTestB.addComponent(new Collider(32, 32, Point2D.Zero));

		GameObject animationTestA = new GameObject();
		animationTestA.getTransform().setPosition(new Point2D(500 , 600));
		animationTestA.addComponent(new Animation());
		animationTestA.getComponent(Animation.class).AddAnimation("Test", new SpriteSheetData("res/AnimationPlaceholder.png", 4, 32, 32));

		gameWindow.getBackgroundRenderer().addElement(backgroundTest);
		gameWindow.getUiRenderer().addElement(uiTest);
		gameWindow.getSpriteRenderer().addElement(spriteTestA);
		gameWindow.getSpriteRenderer().addElement(spriteTestB);
		gameWindow.getSpriteRenderer().addElement(animationTestA);

	}

	private void updateCollisions() {
		this.collisionController.UpdateCollisions();
	}

	private void updateBehaviours() {
		this.behaviourController.updateBehaviours();
	}
}
