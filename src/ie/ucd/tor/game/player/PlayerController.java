package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.events.InputEventHandler;
import ie.ucd.tor.engine.maths.Vector2D;

public class PlayerController extends Behaviour {

	private final InputEventHandler input;
	// private final Animation animation;
	// private final PlayerAnimationStateMachine animationStateMachine;

	private static final float MOVEMENT_SPEED = 2;

	public PlayerController() {
		input = InputEventHandler.getInstance();

		// animation = gameObject.getComponent(Animation.class);
	}

	@Override
	public void execute() {

		Move();

	}

	private void Move() {
		if (input.isKeyWPressed()) {
			transform.getPosition().translate(new Vector2D(0, -1).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeySPressed()) {
			transform.getPosition().translate(new Vector2D(0, 1).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeyDPressed()) {
			transform.getPosition().translate(new Vector2D(1, 0).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeyAPressed()) {
			transform.getPosition().translate(new Vector2D(-1, 0).Scale(MOVEMENT_SPEED));
		}
	}
}
