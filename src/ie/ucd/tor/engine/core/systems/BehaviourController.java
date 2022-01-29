package ie.ucd.tor.engine.core.systems;

public class BehaviourController {

	private static BehaviourController instance;

	public static BehaviourController getInstance() {
		if (instance == null) {
			instance = new BehaviourController();
		}
		return instance;
	}

	public BehaviourController() {

	}

	public void UpdateBehaviours() {

	}
}
