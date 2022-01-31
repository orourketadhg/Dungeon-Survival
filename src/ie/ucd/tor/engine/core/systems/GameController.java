package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.rendering.GameWindow;

import static ie.ucd.tor.engine.rendering.GameWindow.TARGET_FPS;

public class GameController {

	private final CollisionController collisionController;
	private final BehaviourController behaviourController;
	private final GameWindow gameWindow;

	public GameController() {
		this.gameWindow = GameWindow.getInstance();
		this.collisionController = CollisionController.getInstance();
		this.behaviourController = BehaviourController.getInstance();


		initialization();
	}

	protected void initialization() {

	}

	public void gameLoop() {
		updateCollisions();

		updateBehaviour();

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

	private void updateCollisions() {
		this.collisionController.UpdateCollisions();
	}

	private void updateBehaviour() {
		this.behaviourController.updateBehaviours();
	}
}
