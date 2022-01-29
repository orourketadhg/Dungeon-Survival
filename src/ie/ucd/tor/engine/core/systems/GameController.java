package ie.ucd.tor.engine.core.systems;

public class GameController {

	private final CollisionController collisionController;
	private final BehaviourController behaviourController;

	private static GameController instance;

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	public GameController() {
		this.collisionController = CollisionController.getInstance();
		this.behaviourController = BehaviourController.getInstance();

	}

	public void GameLoop() {
		UpdateCollisions();

		UpdateBehaviour();

	}

	private void UpdateCollisions() {
		this.collisionController.UpdateCollisions();
	}

	private void UpdateBehaviour() {
		this.behaviourController.UpdateBehaviours();
	}
}
