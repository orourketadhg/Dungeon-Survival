package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.GameObject;
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
import ie.ucd.tor.game.util.Util;

import java.util.List;

public class PlayerController extends Behaviour {

	private final InputEventHandler input;
	private final KeyLayout keyLayout;
	private static final float MOVEMENT_SPEED = 1;
	private static final int PLAYER_DAMAGE = 1;
	private static final int ATTACK_COOL_DOWN = 50;
	private static final int HEALTH_COOL_DOWN = 1000;

	private int playerHealth;
	private Vector2D playerMovement = Vector2D.Zero;
	private Vector2D playerAttackDirection = Vector2D.Zero;

	private GameObject playerDamageDealer;

	private boolean isAttacking;
	private boolean isDead;

	private boolean canMove;
	private boolean canAttack;

	private long nextAnimationTime;
	private long nextDamageTime;

	public PlayerController(KeyLayout keyLayout) {
		input = InputEventHandler.getInstance();
		this.keyLayout = keyLayout;

		playerHealth = 6;

		canMove = true;
		canAttack = true;
	}

	@Override
	public void execute() {

		if (playerHealth <= 0) {
			isDead = true;
			playerFreeze();
		}

		if (canAttack) {
			attemptAttack();
		}

		if (canMove) {
			attemptMove();
		}

		animatePlayer();

	}

	private void attemptAttack() {

		boolean attack = false;
		if (keyLayout == KeyLayout.WASD && input.isKeySpacePressed()) {
			attack = true;
		}
		else if (keyLayout == KeyLayout.ARROWKEYS && input.isKeyEnterPressed()) {
			attack = true;
		}

		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		if (isAttacking && currentAnimationTime > nextAnimationTime) {

			playerDamageDealer.disable();

			isAttacking = false;
			canMove = true;
		}

		if (attack && !isAttacking && currentAnimationTime > nextAnimationTime + ATTACK_COOL_DOWN) {
			playerAttackDirection = new Vector2D(playerMovement.getX(), playerMovement.getY());

			Point2D damageLocation = Point2D.Zero;

			if (playerAttackDirection.getX() == 1) {
				damageLocation = transform.getPosition().Add(new Vector2D((16 * RoomManager.ROOM_SCALE.getX()), 0));
			}
			else if (playerAttackDirection.getX() == -1) {
				damageLocation = transform.getPosition().Add(new Vector2D(-(16 * RoomManager.ROOM_SCALE.getX()), 0));
			}
			else if (playerAttackDirection.getY() == 1) {
				damageLocation = transform.getPosition().Add(new Vector2D(0, (16 * RoomManager.ROOM_SCALE.getX())));
			}
			else if (playerAttackDirection.getY() == -1) {
				damageLocation = transform.getPosition().Add(new Vector2D(0, -(16 * RoomManager.ROOM_SCALE.getX())));
			}

			playerDamageDealer = new GameObject();
			playerDamageDealer.getTransform().setPosition(damageLocation);
			playerDamageDealer.addComponent(new Collider((int) (16 * RoomManager.ROOM_SCALE.getX()), (int) (16 * RoomManager.ROOM_SCALE.getY()), Point2D.Zero));
			playerDamageDealer.addComponent(new Damage(PLAYER_DAMAGE));

			nextAnimationTime = currentAnimationTime + 600;
			isAttacking = true;
			canMove = false;
		}

	}

	private void attemptMove() {

		playerMovement = Vector2D.Zero;

		if (keyLayout == KeyLayout.WASD) {

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

		}
		else if (keyLayout == KeyLayout.ARROWKEYS) {
			if (input.isKeyArrowUpPressed()) {
				playerMovement = playerMovement.add(new Vector2D(0, -1));
			}

			if (input.isKeyArrowDownPressed()) {
				playerMovement = playerMovement.add(new Vector2D(0, 1));
			}

			if (input.isKeyArrowRightPressed()) {
				playerMovement = playerMovement.add(new Vector2D(1, 0));
			}

			if (input.isKeyArrowLeftPressed()) {
				playerMovement = playerMovement.add(new Vector2D(-1, 0));
			}
		}

		if (playerMovement != Vector2D.Zero) {

			RoomController controller = DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getActiveRoom();

			Point2D currentPos = transform.getPosition();
			Point2D nextPos = currentPos.Add(playerMovement.scale(MOVEMENT_SPEED));

			boolean isValid = Util.validateMove(gameObject, nextPos, controller.getBlockedAreas());

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
				animationName = "knightAttackDown";
			}
			else if (playerAttackDirection.getY() == -1) {
				animationName = "knightAttackUp";
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

	public void playerFreeze() {
		this.canAttack = false;
		this.canMove = false;
	}

	public void playerUnfreeze() {
		this.canAttack = true;
		this.canMove = true;
	}

	public void damagePlayer(int damage) {
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentAnimationTime > nextDamageTime) {
			playerHealth = Math.max(0, playerHealth - damage);
			nextDamageTime = (long) (currentAnimationTime + HEALTH_COOL_DOWN);

			System.out.println(gameObject.getName() + " has taken " + damage + " Damage");
		}
	}

	public void addHealth(int health) {
		this.playerHealth += health;
	}

	public boolean isDead() {
		return isDead;
	}

	public int getPlayerHealth() {
		return playerHealth;
	}
}
