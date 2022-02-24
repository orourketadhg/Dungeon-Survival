package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.components.Animation;
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

	private Vector2D playerMovement = Vector2D.Zero;
	private Vector2D playerAttackDirection = Vector2D.Zero;

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

		animatePlayer();

	}

	private void attemptAttack() {

		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		if (isAttacking && currentAnimationTime > nextAnimationTime) {
			isAttacking = false;
			canMove = true;
		}

		if (input.isKeySpacePressed() && !isAttacking && currentAnimationTime > nextAnimationTime + ATTACK_COOL_DOWN) {
			playerAttackDirection = new Vector2D(playerMovement.getX(), playerMovement.getY());
			
			nextAnimationTime = currentAnimationTime + 100;
			isAttacking = true;
			canMove = false;
		}

	}

	private void attemptMove() {

		playerMovement = Vector2D.Zero;

		if (input.isKeyWPressed()) {
			playerMovement = playerMovement.add(new Vector2D(0, -1));
		}

		if (input.isKeySPressed()) {
			playerMovement = playerMovement.add(new Vector2D(0, 1));
		}

		if (input.isKeyDPressed()) {
			playerMovement = playerMovement.add(new Vector2D(1, 0));
		}

		if (input.isKeyAPressed()) {
			playerMovement = playerMovement.add(new Vector2D(-1, 0));
		}

		if (playerMovement != Vector2D.Zero) {

			RoomController controller = DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getActiveRoom();

			Point2D currentPos = transform.getPosition();
			Point2D nextPos = currentPos.Add(playerMovement.scale(MOVEMENT_SPEED));

			boolean isValid = validateMove(nextPos, controller.getBlockedAreas());

			if (isValid) {
				transform.getPosition().translate(playerMovement.scale(MOVEMENT_SPEED));
			}

		}

	}

	private void animatePlayer() {
		Animation playerAnimator = gameObject.getComponent(Animation.class);

		String animationName = "";

		if (isAttacking) {
			if (playerAttackDirection.getX() == 1) {
				animationName = "knightAttackRight";
			}
			else if (playerAttackDirection.getX() == -1) {
				animationName = "knightAttackLeft";
			}
			else if (playerAttackDirection.getY() == 1) {
				animationName = "knightAttackUp";
			}
			else if (playerAttackDirection.getY() == -1) {
				animationName = "knightAttackDown";
			}
			else {
				animationName = "knightAttackDown";
			}
		}
		else if (canMove) {
			if (playerMovement.getX() == 1) {
				animationName = "knightWalkRight";
			}
			else if (playerMovement.getX() == -1) {
				animationName = "knightWalkLeft";
			}
			else if (playerMovement.getY() == 1) {
				animationName = "knightWalkDown";
			}
			else if (playerMovement.getY() == -1) {
				animationName = "knightWalkUp";
			}
			else {
				animationName = "knightStatic";
			}
		}
		else {
			animationName = "knightStatic";
		}

		playerAnimator.setCurrentAnimation(animationName);

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
