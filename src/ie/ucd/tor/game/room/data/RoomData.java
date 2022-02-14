package ie.ucd.tor.game.room.data;

import java.util.ArrayList;
import java.util.List;

public class RoomData {

	private final String roomTexture;
	private final List<DoorLocation> doorLocations;
	private final List<BlockedAreaData> blockedLocations;

	private final int numDecorations;
	private final int numIntractables;
	private final int numEnemies;

	private final List<RoomObjectData> decorations;
	private final List<RoomObjectData> collectables;

	public RoomData(String roomTexture, List<DoorLocation> doorLocations, int numDecorations, int numInteractibles, int numEnemies) {
		this.roomTexture = roomTexture;
		this.doorLocations = doorLocations;
		this.numDecorations = numDecorations;
		this.numIntractables = numInteractibles;
		this.numEnemies = numEnemies;

		blockedLocations = new ArrayList<>();

		decorations = new ArrayList<>();
		collectables = new ArrayList<>();

	}

	public void addDecoration(RoomObjectData decoration) {
		decorations.add(decoration);
	}

	public void addInteractable(RoomObjectData collectible) {
		collectables.add(collectible);
	}

	public void addBlockedArea(BlockedAreaData blockedArea) {
		blockedLocations.add(blockedArea);
	}

	public String getRoomTexture() {
		return roomTexture;
	}

	public List<DoorLocation> getDoorLocations() {
		return doorLocations;
	}

	public List<BlockedAreaData> getBlockedLocations() {
		return blockedLocations;
	}

	public int getNumDecorations() {
		return numDecorations;
	}

	public int getNumIntractables() {
		return numIntractables;
	}

	public int getNumEnemies() {
		return numEnemies;
	}

	public List<RoomObjectData> getDecorations() {
		return decorations;
	}

	public List<RoomObjectData> getIntractables() {
		return collectables;
	}

}
