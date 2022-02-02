package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.events.InputEventHandler;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.player.animation.PlayerAnimationStateMachine;

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
	public void Execute() {

		Move();

	}

	private void Move() {
		if (input.isKeyWPressed()) {
			transform.getPosition().Translate(new Vector2D(0, -1).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeySPressed()) {
			transform.getPosition().Translate(new Vector2D(0, 1).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeyDPressed()) {
			transform.getPosition().Translate(new Vector2D(1, 0).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeyAPressed()) {
			transform.getPosition().Translate(new Vector2D(-1, 0).Scale(MOVEMENT_SPEED));
		}
	}
}
