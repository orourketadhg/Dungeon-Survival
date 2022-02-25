package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.events.InputEventHandler;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.engine.rendering.GameWindow;
import ie.ucd.tor.game.enemy.EnemyData;
import ie.ucd.tor.game.enemy.EnemyType;
import ie.ucd.tor.game.enemy.SkullController;
import ie.ucd.tor.game.enemy.SlimeController;
import ie.ucd.tor.game.room.data.BlockedAreaData;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;
import ie.ucd.tor.game.room.data.RoomObjectData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomController extends Behaviour {

	private final RoomData roomData;
	private final GameWindow window;
	private boolean isRoomComplete;
	private boolean isRoomLocked = true;
	private DoorLocation exitedDoor;

	private final List<BlockedAreaData> blockedAreas;

	private final List<GameObject> doors;
	private final List<GameObject> decorations;
	private final List<GameObject> intractables;
	private final List<GameObject> enemies;

	private static final String VERTICAL_DOOR = "res/rooms/decorations/Door_Vertical.png";
	private static final String HORIZONTAL_DOOR = "res/rooms/decorations/Door_horizontal.png";

	public RoomController(RoomData roomData, GameWindow window, boolean doGenerateEnemies) {
		this.roomData = roomData;
		this.window = window;
		this.isRoomComplete = false;

		doors = new ArrayList<>();
		decorations = new ArrayList<>();
		intractables = new ArrayList<>();
		enemies = new ArrayList<>();
		blockedAreas = new ArrayList<>();

		generateBlockedAreas();

		generateDoors();

		generateDecorations();

		generateIntractables();

		if (doGenerateEnemies) {
			generateEnemies();
		}

	}

	@Override
	public void execute() {

		if (InputEventHandler.getInstance().isKeyFPressed() && isRoomLocked) {
			clearEnemies();
		}

		if (!enemies.isEmpty()) {
			return;
		}

		if (isRoomLocked) {
			unlockRoom();
			return;
		}

		for (GameObject door : doors) {
			DoorController doorController = door.getComponent(DoorController.class);
			if (doorController.getExitAttempt()) {
				exitedDoor = doorController.getDoor();
				isRoomComplete = true;
			}
		}

	}

	private void unlockRoom() {
		for (GameObject door: doors) {
			door.getComponent(DoorController.class).unlockDoor();

			if (door.hasComponent(Sprite.class)) {
				door.getComponent(Sprite.class).disable();
			}
			else if (door.hasComponent(Animation.class)) {
				door.getComponent(Animation.class).disable();
			}
		}

		for (int i = 0; i < roomData.getDoorLocations().size(); i++) {
			blockedAreas.remove(blockedAreas.size() - 1);
		}

		isRoomLocked = false;

	}

	private void clearEnemies() {
		if (!enemies.isEmpty()) {
			for (GameObject enemy : enemies) {
				window.getSpriteRenderer().removeElement(enemy);
				enemy.disable();
			}

			enemies.clear();
		}
	}

	public void destroyRoom() {
		for (GameObject door: doors) {
			door.disable();
		}

		for (GameObject decoration : decorations) {
			window.getBackgroundRenderer().removeElement(decoration);
			decoration.disable();
		}

		decorations.clear();

		for (GameObject collectible : intractables) {
			window.getSpriteRenderer().removeElement(collectible);
			collectible.disable();
		}

		intractables.clear();

		if (!enemies.isEmpty()) {
			for (GameObject enemy : enemies) {
				window.getSpriteRenderer().removeElement(enemy);
				enemy.disable();
			}

			enemies.clear();
		}
	}

	private void generateBlockedAreas() {
		List<BlockedAreaData> roomAreas = roomData.getBlockedLocations();

		for (BlockedAreaData area: roomAreas) {
			// scale the position an area based on room scale
			int areaX = (int) (24 + area.getPosition().getX() * RoomManager.ROOM_SCALE.getX());
			int areaY = (int) (24 + area.getPosition().getY() * RoomManager.ROOM_SCALE.getY());
			int areaWidth = (int) (area.getWidth() * RoomManager.ROOM_SCALE.getX());
			int areaHeight= (int) (area.getHeight() * RoomManager.ROOM_SCALE.getY());

			blockedAreas.add(new BlockedAreaData(new Point2D(areaX, areaY), areaWidth, areaHeight));
		}

	}

	private void generateDoors() {

		for (DoorLocation doorLocation: roomData.getDoorLocations()) {

			Point2D doorPosition;
			Sprite doorSprite;
			Collider collider;

			switch (doorLocation) {
				case NORTH -> doorPosition = new Point2D(18 + (64 * RoomManager.ROOM_SCALE.getX()), 24 + (0 * RoomManager.ROOM_SCALE.getY()));
				case SOUTH -> doorPosition = new Point2D(18 + (64 * RoomManager.ROOM_SCALE.getX()), 24 + (144 * RoomManager.ROOM_SCALE.getY()));
				case EAST -> doorPosition = new Point2D(24 + (144 * RoomManager.ROOM_SCALE.getX()), 18 + (64 * RoomManager.ROOM_SCALE.getY()));
				case WEST -> doorPosition = new Point2D(24 + (0 * RoomManager.ROOM_SCALE.getX()), 18 + (64 * RoomManager.ROOM_SCALE.getY()));
				default -> throw new IllegalStateException("Unexpected value: " + doorLocation);
			}

			switch (doorLocation) {
				case NORTH, SOUTH -> {
					doorSprite = new Sprite(HORIZONTAL_DOOR, 32, 16, 1);
					collider = new Collider((int) (32 * RoomManager.ROOM_SCALE.getX()), (int) (16 * RoomManager.ROOM_SCALE.getY()), Point2D.Zero);

					blockedAreas.add(new BlockedAreaData(doorPosition, (int) (32 * RoomManager.ROOM_SCALE.getX()), (int) (16 * RoomManager.ROOM_SCALE.getY())));
				}
				case EAST, WEST -> {
					doorSprite = new Sprite(VERTICAL_DOOR, 16, 32, 1);
					collider = new Collider((int) (16 * RoomManager.ROOM_SCALE.getX()), (int) (32 * RoomManager.ROOM_SCALE.getY()), Point2D.Zero);

					blockedAreas.add(new BlockedAreaData(doorPosition, (int) (16 * RoomManager.ROOM_SCALE.getX()), (int) (32 * RoomManager.ROOM_SCALE.getY())));
				}
				default -> throw new IllegalStateException("Unexpected value: " + doorLocation);
			}

			GameObject door = new GameObject();
			door.getTransform().setPosition(doorPosition);
			door.getTransform().setScale(RoomManager.ROOM_SCALE);
			door.addComponent(doorSprite);
			door.addComponent(collider);
			door.addComponent(new DoorController(doorLocation));

			doors.add(door);
			window.getBackgroundRenderer().addElement(door);

		}

	}

	private void generateDecorations() {

		List<RoomObjectData> decorations = roomData.getDecorations();
		int numDecorations = (int) (Math.random() * roomData.getNumDecorations());

		for (int i = 0; i < numDecorations; i++) {
			if (decorations.isEmpty()) {
				break;
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
				break;
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

		intractables.add(interactable);
		window.getSpriteRenderer().addElement(interactable);
	}

	private void generateEnemies() {
		List<EnemyData> enemyDataList = roomData.getEnemyData();
		int numEnemies = (int) (Math.random() * roomData.getNumEnemies());

		for (int i = 0; i < numEnemies; i++) {
			if (enemyDataList.isEmpty()) {
				break;
			}

			int enemyIndex = (int) (Math.random() * enemyDataList.size());
			EnemyData data = enemyDataList.get(enemyIndex);

			if (data.getSpawnPositions().isEmpty()) {
				continue;
			}

			int randomPositionIndex = (int) (Math.random() * data.getSpawnPositions().size());
			Point2D spawnPosition = data.getSpawnPositions().get(randomPositionIndex);

			generateEnemy(data, spawnPosition);

		}

	}

	private void generateEnemy(EnemyData data, Point2D spawnPosition) {
		GameObject enemy = new GameObject();
		enemy.getTransform().setScale(new Point2D(4, 4));

		int randomX = (int) (Math.random() * 8);
		int randomY = (int) (Math.random() * 8);
		int randomDirectionX = (int) ((Math.random() * 2) - 1);
		int randomDirectionY = (int) ((Math.random() * 2) - 1);
		Point2D position = spawnPosition.Add(new Vector2D(randomX * randomDirectionX, randomY * randomDirectionY));
		enemy.getTransform().setPosition(new Point2D(18 + (position.getX() * RoomManager.ROOM_SCALE.getX()), 18 + (position.getY() * RoomManager.ROOM_SCALE.getY())));

		enemy.addComponent(new Collider((int) data.getEnemySize().getX(), (int) data.getEnemySize().getY(), Point2D.Zero));

		enemy.addComponent(new Animation(5));
		for (String animation: data.getAnimations().keySet()) {
			enemy.getComponent(Animation.class).AddAnimation(animation, data.getAnimations().get(animation));
		}

		if (data.getEnemyType() == EnemyType.Skull) {
			enemy.addComponent(new SkullController(data.getEnemyDamage(), data.getEnemyHealth(), data.getEnemyMovementSpeed()));
		}
		else if (data.getEnemyType() == EnemyType.Slime) {
			enemy.addComponent(new SlimeController(data.getEnemyDamage(), data.getEnemyHealth(), data.getEnemyMovementSpeed()));
		}

		enemies.add(enemy);
		window.getSpriteRenderer().addElement(enemy);

	}

	public boolean isRoomComplete() {
		return isRoomComplete;
	}

	public DoorLocation getExitedDoor() {
		return exitedDoor;
	}

	public List<GameObject> getEnemies() {
		return enemies;
	}

	public List<BlockedAreaData> getBlockedAreas() {
		return blockedAreas;
	}
}
