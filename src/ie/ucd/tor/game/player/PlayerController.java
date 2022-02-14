package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.events.InputEventHandler;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;

public class PlayerController extends Behaviour {

	private final InputEventHandler input;
	private static final float MOVEMENT_SPEED = 2;
	private static final float ATTACK_COOL_DOWN = 50;

	private boolean isAttacking;
	private boolean canMove;
	private boolean canAttack;

	private long nextAnimationTime;

	public PlayerController() {
		input = InputEventHandler.getInstance();

		canMove = true;
		canAttack = true;
	}

	@Override
	public void execute() {

		if (canAttack) {
			attemptAttack();
		}

		if (canMove) {
			attemptMove();
		}

	}

	private void attemptAttack() {

		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		if (isAttacking && currentAnimationTime > nextAnimationTime) {
			isAttacking = false;
		}

		if (input.isKeySpacePressed() && !isAttacking && currentAnimationTime > nextAnimationTime + ATTACK_COOL_DOWN) {
			nextAnimationTime = currentAnimationTime + 100;
			isAttacking = true;
		}
	}

	private void attemptMove() {

		if (input.isKeyWPressed()) {
			transform.getPosition().translate(new Vector2D(0, -1).Scale(MOVEMENT_SPEED));
		}
		else if (input.isKeySPressed()) {
			transform.getPosition().translate(new Vector2D(0, 1).Scale(MOVEMENT_SPEED));
		}

		if (input.isKeyDPressed()) {
			transform.getPosition().translate(new Vector2D(1, 0).Scale(MOVEMENT_SPEED));
		}
		else if (input.isKeyAPressed()) {
			transform.getPosition().translate(new Vector2D(-1, 0).Scale(MOVEMENT_SPEED));
		}

	}

	private void playerFreeze() {
		this.canAttack = false;
		this.canMove = false;
	}

	private void playerUnfreeze() {
		this.canAttack = true;
		this.canMove = true;
	}
}
