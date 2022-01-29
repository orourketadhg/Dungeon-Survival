package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.systems.BehaviourController;

public class Behaviour extends Component {

	public Behaviour() {

	}

	public void Execute() {

	}

	@Override
	public void enable() {
		super.enable();
		BehaviourController.getInstance().addBehaviourToSystem(this);
	}

	@Override
	public void disable() {
		super.disable();
		BehaviourController.getInstance().removeBehaviourToSystem(this);
	}

}
