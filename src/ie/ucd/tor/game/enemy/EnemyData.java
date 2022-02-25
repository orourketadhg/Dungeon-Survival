package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.maths.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnemyData {

	private EnemyType enemyType;
	private int enemyHealth;
	private int enemyDamage;
	private int enemyMovementSpeed;
	private Point2D enemySize;
	private Point2D enemyScale;

	private HashMap<String, SpriteSheetData> animations;
	private List<Point2D> spawnPositions;

	public EnemyData(EnemyType enemyType, int enemyHealth, int enemyDamage, int enemyMovementSpeed, Point2D enemySize, Point2D enemyScale) {
		this.enemyType = enemyType;
		this.enemyHealth = enemyHealth;
		this.enemyDamage = enemyDamage;
		this.enemyMovementSpeed = enemyMovementSpeed;
		this.enemySize = enemySize;
		this.enemyScale = enemyScale;

		animations = new HashMap<>();
		spawnPositions = new ArrayList<>();
	}

	public void addAnimation(String name, SpriteSheetData spriteSheet) {
		animations.put(name, spriteSheet);
	}

	public void addSpawnPosition(Point2D position) {
		spawnPositions.add(position);
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

	public Point2D getEnemyScale() {
		return enemyScale;
	}

	public HashMap<String, SpriteSheetData> getAnimations() {
		return animations;
	}

	public List<Point2D> getSpawnPositions() {
		return spawnPositions;
	}
}
