package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.maths.Point2D;

import java.util.ArrayList;
import java.util.List;

public class RoomData {

	private final String roomTexture;
	private final int[] doorLocations;

	private final int numDecorations;
	private final List<Point2D> decorationPositions;

	private final int numCollectables;
	private final List<Point2D> collectablePositions;

	private final int numEnemies;

	public RoomData(String roomTexture, int[] doorLocations, int numDecorations, int numCollectables, int numEnemies) {
		this.roomTexture = roomTexture;
		this.doorLocations = doorLocations;
		this.numDecorations = numDecorations;
		this.numCollectables = numCollectables;
		this.numEnemies = numEnemies;

		decorationPositions = new ArrayList<>();
		collectablePositions = new ArrayList<>();

	}

	public void addDecorationPosition(Point2D pos) {
		decorationPositions.add(pos);
	}

	public void addCollectiblePosition(Point2D pos) {
		collectablePositions.add(pos);
	}

	public String getRoomTexture() {
		return roomTexture;
	}

	public int[] getDoorLocations() {
		return doorLocations;
	}

	public int getNumDecorations() {
		return numDecorations;
	}

	public List<Point2D> getDecorationPositions() {
		return decorationPositions;
	}

	public int getNumCollectables() {
		return numCollectables;
	}

	public List<Point2D> getCollectablePositions() {
		return collectablePositions;
	}

	public int getNumEnemies() {
		return numEnemies;
	}

}
