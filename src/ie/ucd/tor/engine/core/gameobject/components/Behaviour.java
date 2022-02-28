package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.systems.BehaviourController;

/**
 * Component for gameObjects to inherit from to provide custom behaviour
 */
public class Behaviour extends Component {

	public Behaviour() {
	}

	/**
	 * Update the behaviour each frame
	 */
	public void execute() {

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
