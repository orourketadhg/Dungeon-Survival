package ie.ucd.tor.game.room.data;

import java.util.ArrayList;
import java.util.List;

public class RoomData {

	private final String roomTexture;
	private final List<DoorLocation> doorLocations;

	private final int numDecorations;
	private final int numCollectables;
	private final int numEnemies;

	private final List<RoomObjectData> decorations;
	private final List<RoomObjectData> collectables;

	public RoomData(String roomTexture, List<DoorLocation> doorLocations, int numDecorations, int numCollectables, int numEnemies) {
		this.roomTexture = roomTexture;
		this.doorLocations = doorLocations;
		this.numDecorations = numDecorations;
		this.numCollectables = numCollectables;
		this.numEnemies = numEnemies;

		decorations = new ArrayList<>();
		collectables = new ArrayList<>();

	}

	public void addDecoration(RoomObjectData decoration) {
		decorations.add(decoration);
	}

	public void addInteractable(RoomObjectData collectible) {
		collectables.add(collectible);
	}

	public String getRoomTexture() {
		return roomTexture;
	}

	public List<DoorLocation> getDoorLocations() {
		return doorLocations;
	}

	public int getNumDecorations() {
		return numDecorations;
	}

	public int getNumCollectables() {
		return numCollectables;
	}

	public int getNumEnemies() {
		return numEnemies;
	}

	public List<RoomObjectData> getDecorations() {
		return decorations;
	}

	public List<RoomObjectData> getCollectables() {
		return collectables;
	}

}
