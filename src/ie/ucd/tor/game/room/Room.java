package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.maths.Point2D;

import java.util.List;

public class Room {

	private GameObject background;
	private final int[] doorLocations;

	private List<Point2D> decorationLocations;
	private final int numDecorations;

	private List<Point2D> destructibleLocations;
	private final int numDestructibles;

	private List<Point2D> enemySpawnLocations;
	private final int numEnemies;

	private List<GameObject> doors;
	private List<GameObject> destructibles;
	private List<GameObject> enemies;

	public Room(GameObject background, int[] doorLocations, List<Point2D> decorationLocations, int numDecorations, List<Point2D> destructibleLocations, int numDestructibles, List<Point2D> enemySpawnLocations, int numEnemies) {
		this.background = background;
		this.doorLocations = doorLocations;
		this.decorationLocations = decorationLocations;
		this.numDecorations = numDecorations;
		this.destructibleLocations = destructibleLocations;
		this.numDestructibles = numDestructibles;
		this.enemySpawnLocations = enemySpawnLocations;
		this.numEnemies = numEnemies;

	}

	public void initialiseNewRoom() {

	}


}
