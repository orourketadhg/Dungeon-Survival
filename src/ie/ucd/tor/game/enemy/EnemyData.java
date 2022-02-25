package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.maths.Point2D;

import java.util.HashMap;

public class EnemyData {

	private EnemyType enemyType;
	private int enemyHealth;
	private int enemyDamage;
	private int enemyMovementSpeed;
	private Point2D enemySize;

	private HashMap<String, SpriteSheetData> animations;

	public EnemyData(EnemyType enemyType, int enemyHealth, int enemyDamage, int enemyMovementSpeed, Point2D enemySize) {
		this.enemyType = enemyType;
		this.enemyHealth = enemyHealth;
		this.enemyDamage = enemyDamage;
		this.enemyMovementSpeed = enemyMovementSpeed;
		this.enemySize = enemySize;

		animations = new HashMap<>();
	}

	public void addAnimation(String name, SpriteSheetData spriteSheet) {
		animations.put(name, spriteSheet);
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

	public HashMap<String, SpriteSheetData> getAnimations() {
		return animations;
	}

}
