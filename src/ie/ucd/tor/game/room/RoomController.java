package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collision;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.rendering.GameWindow;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;
import ie.ucd.tor.game.room.data.RoomObjectData;

import java.util.ArrayList;
import java.util.List;

public class RoomController extends Behaviour {

	private final RoomData roomData;
	private final GameWindow window;
	private boolean roomComplete = false;
	private DoorLocation exitedDoor;

	private final List<GameObject> doors;
	private final List<GameObject> decorations;
	private final List<GameObject> intractables;
	private final List<GameObject> enemies;

	private static final String VERTICAL_DOOR = "res/rooms/decorations/Door_Vertical.png";
	private static final String HORIZONTAL_DOOR = "res/rooms/decorations/Door_horizontal.png";

	public RoomController(RoomData roomData, GameWindow window) {
		this.roomData = roomData;
		this.window = window;
		this.roomComplete = false;

		doors = new ArrayList<>();
		decorations = new ArrayList<>();
		intractables = new ArrayList<>();
		enemies = new ArrayList<>();

		generateDoors();

		generateDecorations();

		generateIntractables();

		generateEnemies();

	}

	public void destroyRoom() {
		for (GameObject decoration : decorations) {
			window.getBackgroundRenderer().removeElement(decoration);
		}

		for (GameObject collectible : intractables) {
			window.getSpriteRenderer().removeElement(collectible);
		}

		for (GameObject enemy : enemies) {
			window.getSpriteRenderer().removeElement(enemy);
		}
	}

	public void setRoomComplete() {
		this.roomComplete = true;
	}

	public boolean isRoomComplete() {
		return roomComplete;
	}

	private void generateDoors() {

		for (DoorLocation doorLocation: roomData.getDoorLocations()) {

			Point2D doorPosition;
			Sprite doorSprite;
			Collision collider;

			switch (doorLocation) {
				case NORTH -> doorPosition = new Point2D(18 + (64 * RoomManager.ROOM_SCALE.getX()), 24 + (0 * RoomManager.ROOM_SCALE.getY()));
				case SOUTH -> doorPosition = new Point2D(18 + (64 * RoomManager.ROOM_SCALE.getX()), 24 + (144 * RoomManager.ROOM_SCALE.getY()));
				case EAST -> doorPosition = new Point2D(24 + (144 * RoomManager.ROOM_SCALE.getX()), 18 + (64 * RoomManager.ROOM_SCALE.getY()));
				case WEST -> doorPosition = new Point2D(24 + (0 * RoomManager.ROOM_SCALE.getX()), 18 + (64 * RoomManager.ROOM_SCALE.getY()));
				default -> throw new IllegalStateException("Unexpected value: " + doorLocation);
			}

			// TODO fix collider scales

			switch (doorLocation) {
				case NORTH, SOUTH -> {
					doorSprite = new Sprite(HORIZONTAL_DOOR, 32, 16, 1);
					collider = new Collision(32, 16, Point2D.Zero);
				}
				case EAST, WEST -> {
					doorSprite = new Sprite(VERTICAL_DOOR, 16, 32, 1);
					collider = new Collision(16, 32, Point2D.Zero);
				}
				default -> throw new IllegalStateException("Unexpected value: " + doorLocation);
			}

			GameObject door = new GameObject();
			door.getTransform().setPosition(doorPosition);
			door.getTransform().setScale(RoomManager.ROOM_SCALE);
			door.addComponent(doorSprite);
			door.addComponent(collider);
			door.addComponent(new DoorController(this, doorLocation));

			doors.add(door);
			window.getBackgroundRenderer().addElement(door);
		}

	}

	private void generateDecorations() {

		List<RoomObjectData> decorations = roomData.getDecorations();
		int numDecorations = (int) (Math.random() * roomData.getNumDecorations());

		for (int i = 0; i < numDecorations; i++) {
			if (decorations.isEmpty()) {
				continue;
			}

			int randomDecorationIndex = (int) (Math.random() * decorations.size());
			RoomObjectData decoration = decorations.get(randomDecorationIndex);

			if (decoration.getPositions().isEmpty()) {
				continue;
			}

			int randomPositionIndex = (int) (Math.random() * decoration.getPositions().size());
			Point2D decorationPosition = decoration.getPositions().get(randomPositionIndex);

			generateDecoration(decoration.getSpriteLocation(), decorationPosition, decoration.isAnimated());

		}

	}

	private void generateDecoration(String decorationSpriteLocation, Point2D spawnPosition, boolean isAnimatedDecoration) {
		GameObject decoration = new GameObject();

		Point2D position = new Point2D(18 + (spawnPosition.getX() * RoomManager.ROOM_SCALE.getX()), 18 + (spawnPosition.getY() * RoomManager.ROOM_SCALE.getY()));
		decoration.getTransform().setPosition(position);
		decoration.getTransform().setScale(RoomManager.ROOM_SCALE);

		if (isAnimatedDecoration) {
			decoration.addComponent(new Animation(1));
			decoration.getComponent(Animation.class).AddAnimation("Static", new SpriteSheetData(decorationSpriteLocation, 4, 16, 16));
		}
		else {
			decoration.addComponent(new Sprite(decorationSpriteLocation, 16, 16, 1));
		}

		decorations.add(decoration);
		window.getBackgroundRenderer().addElement(decoration);
	}

	private void generateIntractables() {
		List<RoomObjectData> intractables = roomData.getIntractables();
		int numIntractables = (int) (Math.random() * roomData.getNumIntractables());

		for (int i = 0; i < numIntractables; i++) {
			if (intractables.isEmpty()) {
				continue;
			}

			int randomIntractableIndex = (int) (Math.random() * intractables.size());
			RoomObjectData intractable = intractables.get(randomIntractableIndex);

			if (intractable.getPositions().isEmpty()) {
				continue;
			}

			int randomPositionIndex = (int) (Math.random() * intractable.getPositions().size());
			Point2D intractablePosition = intractable.getPositions().get(randomPositionIndex);

			generateInteractable(intractable.getSpriteLocation(), intractablePosition, intractable.isAnimated());

		}
	}

	private void generateInteractable(String interactableSpriteLocation, Point2D spawnPosition, boolean isAnimatedDecoration) {
		GameObject interactable = new GameObject();

		Point2D position = new Point2D(18 + (spawnPosition.getX() * RoomManager.ROOM_SCALE.getX()), 18 + (spawnPosition.getY() * RoomManager.ROOM_SCALE.getY()));
		interactable.getTransform().setPosition(position);
		interactable.getTransform().setScale(RoomManager.ROOM_SCALE);

		if (isAnimatedDecoration) {
			interactable.addComponent(new Animation(1));
			interactable.getComponent(Animation.class).AddAnimation("Static", new SpriteSheetData(interactableSpriteLocation, 4, 16, 16));
		}
		else {
			interactable.addComponent(new Sprite(interactableSpriteLocation, 16, 16, 1));
		}

		System.out.println("Generated Interactable");

		intractables.add(interactable);
		window.getSpriteRenderer().addElement(interactable);
	}

	private void generateEnemies() {

	}

	public DoorLocation getExitedDoor() {
		return exitedDoor;
	}

	public List<GameObject> getEnemies() {
		return enemies;
	}

	public void setExitedDoor(DoorLocation exitedDoor) {
		this.exitedDoor = exitedDoor;
	}
}