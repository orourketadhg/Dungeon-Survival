package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.game.core.DungeonSurvival;

public class EnemyController extends Behaviour {

	private static final float HEALTH_COOL_DOWN = 100;

	private int health;
	private long nextDamageTime;

	public int getHealth() {
		return health;
	}

	public void doDamage(int damage) {
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentAnimationTime > nextDamageTime) {
			health = Math.max(0, health - damage);
			nextDamageTime = (long) (currentAnimationTime + HEALTH_COOL_DOWN);

			System.out.println("Enemy " + gameObject.getName() + " has taken " + damage + " Damage");
		}
	}
}
