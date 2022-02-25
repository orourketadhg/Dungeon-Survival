package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;

import java.util.ArrayList;
import java.util.List;

public class BehaviourController {

	private final List<Behaviour> behaviours;

	private static BehaviourController instance;

	public static BehaviourController getInstance() {
		if (instance == null) {
			instance = new BehaviourController();
		}
		return instance;
	}

	public BehaviourController() {
		behaviours = new ArrayList<>();
	}

	public void updateBehaviours() {
		// System.out.println(getBehaviours());

		int i = 0;
		while(i < behaviours.size()) {
			behaviours.get(i).execute();
			i++;
		}

	}

	public List<Behaviour> getBehaviours() {
		return behaviours;
	}

	public void addBehaviourToSystem(Behaviour behaviour) {
		behaviours.add(behaviour);
	}

	public void removeBehaviourToSystem(Behaviour behaviour) {
		behaviours.remove(behaviour);
	}
}
