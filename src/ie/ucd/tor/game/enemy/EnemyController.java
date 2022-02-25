package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.game.core.DungeonSurvival;

public class EnemyController extends Behaviour {

	private static final float HEALTH_COOL_DOWN = 100;

	private final int movementSpeed;

	private final int damage;
	private long nextAttackTime;

	private int health;
	private long nextDamageTime;

	public EnemyController(int damage, int health, int movementSpeed) {
		this.damage = damage;
		this.health = health;
		this.movementSpeed = movementSpeed;
	}

	public void move() {

	}

	public void attack() {

	}

	public void takeDamage(int damage) {
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentAnimationTime > nextDamageTime) {
			health = Math.max(0, health - damage);
			nextDamageTime = (long) (currentAnimationTime + HEALTH_COOL_DOWN);

			System.out.println("Enemy " + gameObject.getName() + " has taken " + damage + " Damage");
		}
	}

	public int getHealth() {
		return health;
	}

}
