package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.events.InputEventHandler;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.RoomController;
import ie.ucd.tor.game.room.RoomManager;
import ie.ucd.tor.game.room.data.BlockedAreaData;

import java.util.List;

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

		Vector2D movement = Vector2D.Zero;

		if (input.isKeyWPressed()) {
			movement = movement.add(new Vector2D(0, -1));
		}

		if (input.isKeySPressed()) {
			movement =movement.add(new Vector2D(0, 1));
		}

		if (input.isKeyDPressed()) {
			movement =movement.add(new Vector2D(1, 0));
		}

		if (input.isKeyAPressed()) {
			movement = movement.add(new Vector2D(-1, 0));
		}

		if (movement != Vector2D.Zero) {

			RoomController controller = DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getActiveRoom();

			Point2D currentPos = transform.getPosition();
			Point2D nextPos = currentPos.Add(movement.scale(MOVEMENT_SPEED));

			boolean isValid = validateMove(nextPos, controller.getBlockedAreas());

			if (isValid) {
				transform.getPosition().translate(movement.scale(MOVEMENT_SPEED));
			}

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

	private boolean validateMove(Point2D position, List<BlockedAreaData> blockedAreas) {

		// get players collision area
		Collider playerCollider = gameObject.getComponent(Collider.class);
		int playerWidth = playerCollider.getColliderWidth();
		int playerHeight = playerCollider.getColliderHeight();

		boolean isIntersecting = false;

		for (BlockedAreaData area: blockedAreas) {

			if (!isIntersecting) {
				isIntersecting =
							position.getX() < area.getPosition().getX() + area.getWidth() &&
							position.getX() + playerWidth > area.getPosition().getX() &&
							position.getY() < area.getPosition().getY() + area.getHeight() &&
							position.getY() + playerHeight > area.getPosition().getY();
			}

		}

		return !isIntersecting;
	}

}
