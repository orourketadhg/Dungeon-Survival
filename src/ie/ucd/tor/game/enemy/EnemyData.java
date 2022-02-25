package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.maths.Point2D;

import java.util.HashMap;

public class EnemyData {

	private EnemyType enemyType;
	private int enemyHealth;
	private int enemyDamage;
	private int enemyMovementSpeed;
	private Point2D enemySize;

	private HashMap<String, String> animations;

	public EnemyData(EnemyType enemyType, int enemyHealth, int enemyDamage, int enemyMovementSpeed, Point2D enemySize) {
		this.enemyType = enemyType;
		this.enemyHealth = enemyHealth;
		this.enemyDamage = enemyDamage;
		this.enemyMovementSpeed = enemyMovementSpeed;
		this.enemySize = enemySize;

		animations = new HashMap<>();
	}

	public void addAnimation(String name, String location) {
		animations.put(name, location);
	}

	public EnemyType getEnemyType() {
		return enemyType;
	}

	public int getEnemyHealth() {
		return enemyHealth;
	}

	public int getEnemyDamage() {
		return enemyDamage;
	}

	public int getEnemyMovementSpeed() {
		return enemyMovementSpeed;
	}

	public Point2D getEnemySize() {
		return enemySize;
	}

	public HashMap<String, String> getAnimations() {
		return animations;
	}

}
