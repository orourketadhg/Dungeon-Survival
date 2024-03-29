package ie.ucd.tor.game.core;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.core.systems.BehaviourController;
import ie.ucd.tor.engine.core.systems.CollisionController;
import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.enemy.EnemyData;
import ie.ucd.tor.game.enemy.EnemyType;
import ie.ucd.tor.game.player.KeyLayout;
import ie.ucd.tor.game.player.PlayerController;
import ie.ucd.tor.game.room.data.BlockedAreaData;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;
import ie.ucd.tor.game.room.RoomManager;
import ie.ucd.tor.game.room.data.RoomObjectData;
import ie.ucd.tor.game.ui.UIController;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Controller for the Dungeon Survival game
 */
public class DungeonSurvival extends GameController {

	private GameObject playerOne;	// player 1 gameObject
	private GameObject playerTwo;	//
	private GameObject roomManager;

	private GameObject UI;

	private static DungeonSurvival instance;

	public static DungeonSurvival getInstance() {
		if (instance == null) {
			instance = new DungeonSurvival();
		}
		return instance;
	}

	/**
	 * Initialise the players, the UI, and the room manager
	 */
	@Override
	protected void initialization() {
		playerOne = initialisePlayer(1, new Point2D(512 - 64, 512), KeyLayout.WASD);
		playerTwo = initialisePlayer(2, new Point2D(512 + 64, 512), KeyLayout.ARROWKEYS);

		roomManager = initialiseRoomManager();
		roomManager.getComponent(RoomManager.class).generateStarterRoom();

		UI = initialiseUI();

	}

	/**
	 * Restart the game
	 */
	public void restartGame() {

		// clear all collision data and behaviours
		BehaviourController.getInstance().clearBehaviours();
		CollisionController.getInstance().clearColliders();

		// clear the Ui and game panels
		gameWindow.getUiRenderer().clearElements();
		gameWindow.getBackgroundRenderer().clearElements();
		gameWindow.getSpriteRenderer().clearElements();

		// reinitialise the game
		initialization();

	}

	/**
	 * Initialise a player gameObject and its components
	 * @param playerIndex, the players number
	 * @param startingPosition, the spawn position of the player
	 * @param playerKeyLayout, the controls the player will use
	 * @return a constructed player gameObject
	 */
	private GameObject initialisePlayer(int playerIndex, Point2D startingPosition, KeyLayout playerKeyLayout) {
		/* Player Initialisation */
		GameObject player = new GameObject();
		player.setName("Player " + playerIndex);
		player.getTransform().setPosition(startingPosition);
		player.getTransform().setScale(new Point2D(4, 4));
		// Player Behaviours
		player.addComponent(new PlayerController(playerKeyLayout));
		// Player Animations
		player.addComponent(new Animation(10));
		player.getComponent(Animation.class).AddAnimation("knightStatic", new SpriteSheetData("res/Knight/Knight_static.png", 4,14, 16));
		player.getComponent(Animation.class).AddAnimation("knightWalkUp", new SpriteSheetData("res/Knight/Knight_walk_up.png", 8,16, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkDown", new SpriteSheetData("res/Knight/Knight_walk_down.png", 8,15, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkRight", new SpriteSheetData("res/Knight/Knight_walk_right.png", 8,17, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkLeft", new SpriteSheetData("res/Knight/Knight_walk_left.png", 8,22, 15));
		player.getComponent(Animation.class).AddAnimation("knightAttackUp", new SpriteSheetData("res/Knight/Knight_attack_up.png", 6,20, 20));
		player.getComponent(Animation.class).AddAnimation("knightAttackDown", new SpriteSheetData("res/Knight/Knight_attack_down.png", 6,20, 22));
		player.getComponent(Animation.class).AddAnimation("knightAttackRight", new SpriteSheetData("res/Knight/Knight_attack_right.png", 6,24, 18));
		player.getComponent(Animation.class).AddAnimation("knightAttackLeft", new SpriteSheetData("res/Knight/Knight_attack_left.png", 6,24, 18));
		// Add colliders
		player.addComponent(new Collider(16 * 4, 16 * 4, Point2D.Zero));

		// Add player to renderer
		this.gameWindow.getSpriteRenderer().addElement(player);

		return player;

	}

	/**
	 * Setup the UI for the game
	 * @return the UI controller gameObject
	 */
	private GameObject initialiseUI() {

		GameObject UI = new GameObject();
		UI.addComponent(new UIController());

		return UI;
	}

	/**
	 * Initialize the room manager gameObject and populate the room data
	 * @return
	 */
	private GameObject initialiseRoomManager() {
		GameObject roomManager = new GameObject();
		roomManager.setName("Room Manager");

		RoomManager manager = new RoomManager(gameWindow);

		/* setup the rooms and their respective data */

		{
			/* Room - plus */
			RoomData room = new RoomData("res/rooms/Room_plus.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH, DoorLocation.EAST, DoorLocation.WEST)), 6, 2, 3);

			// blocked areas
			// top left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 16, 64));

			// top right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 0), 16, 64));

			// bottom left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 96), 16, 64));

			// bottom right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 96), 16, 64));

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(16, 16));
			candle.addPosition(new Point2D(128, 16));
			candle.addPosition(new Point2D(16, 128));
			candle.addPosition(new Point2D(128, 128));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);
			flag.addPosition(new Point2D(48, 0));
			flag.addPosition(new Point2D(96, 0));

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData plusRoomBox = new RoomObjectData("res/rooms/decorations/Box.png", true);
			plusRoomBox.addPosition(new Point2D(32, 32));
			plusRoomBox.addPosition(new Point2D(32, 112));
			plusRoomBox.addPosition(new Point2D(112, 32));
			plusRoomBox.addPosition(new Point2D(112, 112));

			room.addInteractable(plusRoomBox);

			// Enemies
			EnemyData skullData = new EnemyData(EnemyType.Skull, 2, 1, 1, new Point2D(16, 16), Point2D.One);
			skullData.addAnimation("static", new SpriteSheetData("res/enemies/skull/skull.png", 4, 16, 16));
			skullData.addSpawnPosition(new Point2D(96, 48));
			skullData.addSpawnPosition(new Point2D(48, 96));

			EnemyData slimeData = new EnemyData(EnemyType.Slime, 4, 1, 1, new Point2D(16, 16), new Point2D(0.5, 0.5));
			slimeData.addAnimation("slimeStatic", new SpriteSheetData("res/enemies/slime/Slime_static.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkUp", new SpriteSheetData("res/enemies/slime/Slime_walk_up.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkDown", new SpriteSheetData("res/enemies/slime/Slime_walk_down.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkLeft", new SpriteSheetData("res/enemies/slime/Slime_walk_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkRight", new SpriteSheetData("res/enemies/slime/Slime_walk_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackLeft", new SpriteSheetData("res/enemies/slime/Slime_attack_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackRight", new SpriteSheetData("res/enemies/slime/Slime_attack_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeDamage", new SpriteSheetData("res/enemies/slime/Slime_damage.png", 4, 32, 32));
			slimeData.addAnimation("slimeDeath", new SpriteSheetData("res/enemies/slime/SlimeDeathSheet.png", 11, 32, 32));
			slimeData.addSpawnPosition(new Point2D(48, 48));
			slimeData.addSpawnPosition(new Point2D(96, 96));


			room.addEnemyData(skullData);
			room.addEnemyData(slimeData);

			manager.addRoomData(room);

		}

		{
			/* Room - plus hall */
			RoomData room = new RoomData("res/rooms/Room_plus_hall.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH, DoorLocation.EAST, DoorLocation.WEST)), 4, 2, 4);

			// blocked areas
			// top left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(16, 16), 32, 32));

			// top right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 0), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(112, 16), 32, 32));

			// bottom left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 96), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(16, 112), 32, 32));

			// bottom right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 96), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(112, 112), 32, 32));

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(72, 72));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);
			box.addPosition(new Point2D(32, 72));
			box.addPosition(new Point2D(72, 32));
			box.addPosition(new Point2D(72, 112));
			box.addPosition(new Point2D(112, 72));

			room.addInteractable(box);

			// Enemies
			EnemyData skullData = new EnemyData(EnemyType.Skull, 2, 1, 1, new Point2D(16, 16), Point2D.One);
			skullData.addAnimation("static", new SpriteSheetData("res/enemies/skull/skull.png", 4, 16, 16));
			skullData.addSpawnPosition(new Point2D(72, 112));
			skullData.addSpawnPosition(new Point2D(112, 72));

			EnemyData slimeData = new EnemyData(EnemyType.Slime, 4, 1, 1, new Point2D(16, 16), new Point2D(0.5, 0.5));
			slimeData.addAnimation("slimeStatic", new SpriteSheetData("res/enemies/slime/Slime_static.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkUp", new SpriteSheetData("res/enemies/slime/Slime_walk_up.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkDown", new SpriteSheetData("res/enemies/slime/Slime_walk_down.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkLeft", new SpriteSheetData("res/enemies/slime/Slime_walk_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkRight", new SpriteSheetData("res/enemies/slime/Slime_walk_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackLeft", new SpriteSheetData("res/enemies/slime/Slime_attack_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackRight", new SpriteSheetData("res/enemies/slime/Slime_attack_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeDamage", new SpriteSheetData("res/enemies/slime/Slime_damage.png", 4, 32, 32));
			slimeData.addAnimation("slimeDeath", new SpriteSheetData("res/enemies/slime/SlimeDeathSheet.png", 11, 32, 32));
			slimeData.addSpawnPosition(new Point2D(32, 72));
			slimeData.addSpawnPosition(new Point2D(72, 32));

			room.addEnemyData(skullData);
			room.addEnemyData(slimeData);

			manager.addRoomData(room);
		}

		{
			/* Room - horizontal */
			RoomData room = new RoomData("res/rooms/Room_horizontal.png", new ArrayList<>(List.of(DoorLocation.EAST, DoorLocation.WEST)), 6, 0, 6);

			// blocked areas
			// top left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(64, 0), 32, 16));

			// top right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 0), 16, 64));

			// bottom left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 96), 16, 64));

			// bottom right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 96), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(64, 144), 32, 16));

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(16, 16));
			candle.addPosition(new Point2D(128, 16));
			candle.addPosition(new Point2D(16, 128));
			candle.addPosition(new Point2D(128, 128));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);
			flag.addPosition(new Point2D(64, 0));
			flag.addPosition(new Point2D(72, 0));
			flag.addPosition(new Point2D(32, 0));
			flag.addPosition(new Point2D(112, 0));

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);

			room.addInteractable(box);

			// Enemies
			EnemyData skullData = new EnemyData(EnemyType.Skull, 2, 1, 1, new Point2D(16, 16), Point2D.One);
			skullData.addAnimation("static", new SpriteSheetData("res/enemies/skull/skull.png", 4, 16, 16));
			skullData.addSpawnPosition(new Point2D(96, 48));
			skullData.addSpawnPosition(new Point2D(48, 96));

			EnemyData slimeData = new EnemyData(EnemyType.Slime, 4, 1, 1, new Point2D(16, 16), new Point2D(0.5, 0.5));
			slimeData.addAnimation("slimeStatic", new SpriteSheetData("res/enemies/slime/Slime_static.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkUp", new SpriteSheetData("res/enemies/slime/Slime_walk_up.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkDown", new SpriteSheetData("res/enemies/slime/Slime_walk_down.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkLeft", new SpriteSheetData("res/enemies/slime/Slime_walk_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkRight", new SpriteSheetData("res/enemies/slime/Slime_walk_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackLeft", new SpriteSheetData("res/enemies/slime/Slime_attack_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackRight", new SpriteSheetData("res/enemies/slime/Slime_attack_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeDamage", new SpriteSheetData("res/enemies/slime/Slime_damage.png", 4, 32, 32));
			slimeData.addAnimation("slimeDeath", new SpriteSheetData("res/enemies/slime/SlimeDeathSheet.png", 11, 32, 32));
			slimeData.addSpawnPosition(new Point2D(96, 96));
			slimeData.addSpawnPosition(new Point2D(48, 48));

			room.addEnemyData(skullData);
			room.addEnemyData(slimeData);

			manager.addRoomData(room);
		}

		{
			/* Room - horizontal hall */
			RoomData room = new RoomData("res/rooms/Room_horizontal_hall.png", new ArrayList<>(List.of(DoorLocation.EAST, DoorLocation.WEST)), 3, 0, 2);

			// blocked areas
			// large walls
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 112), 160, 48));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 160, 48));

			// top left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 16, 64));

			// top right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 0), 16, 64));

			// bottom left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 96), 16, 64));

			// bottom right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 96), 16, 64));

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(32, 72));
			candle.addPosition(new Point2D(72, 72));
			candle.addPosition(new Point2D(104, 72));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);

			room.addInteractable(box);

			// Enemies
			EnemyData skullData = new EnemyData(EnemyType.Skull, 2, 1, 1, new Point2D(16, 16), Point2D.One);
			skullData.addAnimation("static", new SpriteSheetData("res/enemies/skull/skull.png", 4, 16, 16));
			skullData.addSpawnPosition(new Point2D(32, 72));
			skullData.addSpawnPosition(new Point2D(104, 72));

			EnemyData slimeData = new EnemyData(EnemyType.Slime, 4, 1, 1, new Point2D(16, 16), new Point2D(0.5, 0.5));
			slimeData.addAnimation("slimeStatic", new SpriteSheetData("res/enemies/slime/Slime_static.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkUp", new SpriteSheetData("res/enemies/slime/Slime_walk_up.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkDown", new SpriteSheetData("res/enemies/slime/Slime_walk_down.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkLeft", new SpriteSheetData("res/enemies/slime/Slime_walk_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkRight", new SpriteSheetData("res/enemies/slime/Slime_walk_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackLeft", new SpriteSheetData("res/enemies/slime/Slime_attack_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackRight", new SpriteSheetData("res/enemies/slime/Slime_attack_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeDamage", new SpriteSheetData("res/enemies/slime/Slime_damage.png", 4, 32, 32));
			slimeData.addAnimation("slimeDeath", new SpriteSheetData("res/enemies/slime/SlimeDeathSheet.png", 11, 32, 32));
			slimeData.addSpawnPosition(new Point2D(72, 72));

			room.addEnemyData(skullData);
			room.addEnemyData(slimeData);

			manager.addRoomData(room);
		}

		{
			/* Room - vertical */
			RoomData room = new RoomData("res/rooms/Room_vertical.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH)), 8, 4, 0);

			// blocked areas
			// top left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 16, 64));

			// top right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 0), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 0), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(64, 0), 16, 32));

			// bottom left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 96), 16, 64));

			// bottom right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 144), 64, 16));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 96), 16, 64));
			room.addBlockedArea(new BlockedAreaData(new Point2D(144, 64), 16, 32));

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(16, 16));
			candle.addPosition(new Point2D(128, 16));
			candle.addPosition(new Point2D(16, 128));
			candle.addPosition(new Point2D(128, 128));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);
			box.addPosition(new Point2D(48, 48));
			box.addPosition(new Point2D(48, 96));
			box.addPosition(new Point2D(96, 48));
			box.addPosition(new Point2D(96, 96));

			room.addInteractable(box);

			// Enemies
			EnemyData skullData = new EnemyData(EnemyType.Skull, 2, 1, 1, new Point2D(16, 16), Point2D.One);
			skullData.addAnimation("static", new SpriteSheetData("res/enemies/skull/skull.png", 4, 16, 16));
			skullData.addSpawnPosition(new Point2D(48, 48));

			EnemyData slimeData = new EnemyData(EnemyType.Slime, 4, 1, 1, new Point2D(16, 16), new Point2D(0.5, 0.5));
			slimeData.addAnimation("slimeStatic", new SpriteSheetData("res/enemies/slime/Slime_static.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkUp", new SpriteSheetData("res/enemies/slime/Slime_walk_up.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkDown", new SpriteSheetData("res/enemies/slime/Slime_walk_down.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkLeft", new SpriteSheetData("res/enemies/slime/Slime_walk_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkRight", new SpriteSheetData("res/enemies/slime/Slime_walk_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackLeft", new SpriteSheetData("res/enemies/slime/Slime_attack_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackRight", new SpriteSheetData("res/enemies/slime/Slime_attack_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeDamage", new SpriteSheetData("res/enemies/slime/Slime_damage.png", 4, 32, 32));
			slimeData.addAnimation("slimeDeath", new SpriteSheetData("res/enemies/slime/SlimeDeathSheet.png", 11, 32, 32));
			slimeData.addSpawnPosition(new Point2D(96, 48));
			slimeData.addSpawnPosition(new Point2D(48, 96));
			slimeData.addSpawnPosition(new Point2D(96, 96));

			room.addEnemyData(skullData);
			room.addEnemyData(slimeData);

			manager.addRoomData(room);
		}

		{
			/* Room - vertical hall */
			RoomData room = new RoomData("res/rooms/Room_vertical_hall.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH)), 3, 0, 4);

			// blocked areas
			// large walls
			room.addBlockedArea(new BlockedAreaData(new Point2D(112, 0), 48, 160));
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 48, 160));

			// top left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 64, 16));

			// top right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 0), 64, 16));

			// bottom left wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 144), 64, 16));

			// bottom right wall
			room.addBlockedArea(new BlockedAreaData(new Point2D(96, 144), 64, 16));

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(72, 72));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);
			flag.addPosition(new Point2D(48, 0));
			flag.addPosition(new Point2D(96, 0));

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);

			room.addInteractable(box);

			// Enemies
			EnemyData skullData = new EnemyData(EnemyType.Skull, 2, 1, 1, new Point2D(16, 16), Point2D.One);
			skullData.addAnimation("static", new SpriteSheetData("res/enemies/skull/skull.png", 4, 16, 16));
			skullData.addSpawnPosition(new Point2D(72, 72));
			skullData.addSpawnPosition(new Point2D(72, 104));

			EnemyData slimeData = new EnemyData(EnemyType.Slime, 4, 1, 1, new Point2D(16, 16), new Point2D(0.5, 0.5));
			slimeData.addAnimation("slimeStatic", new SpriteSheetData("res/enemies/slime/Slime_static.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkUp", new SpriteSheetData("res/enemies/slime/Slime_walk_up.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkDown", new SpriteSheetData("res/enemies/slime/Slime_walk_down.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkLeft", new SpriteSheetData("res/enemies/slime/Slime_walk_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeWalkRight", new SpriteSheetData("res/enemies/slime/Slime_walk_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackLeft", new SpriteSheetData("res/enemies/slime/Slime_attack_left.png", 6, 32, 32));
			slimeData.addAnimation("slimeAttackRight", new SpriteSheetData("res/enemies/slime/Slime_attack_right.png", 6, 32, 32));
			slimeData.addAnimation("slimeDamage", new SpriteSheetData("res/enemies/slime/Slime_damage.png", 4, 32, 32));
			slimeData.addAnimation("slimeDeath", new SpriteSheetData("res/enemies/slime/SlimeDeathSheet.png", 11, 32, 32));
			slimeData.addSpawnPosition(new Point2D(72, 32));

			room.addEnemyData(skullData);
			room.addEnemyData(slimeData);

			manager.addRoomData(room);
		}

		roomManager.addComponent(manager);

		return roomManager;
	}


	/**
	 * Pause the game
	 */
	public void pauseGame() {
		// freeze the players and all enemies
		DungeonSurvival.getInstance().getPlayerOne().getComponent(PlayerController.class).playerFreeze();
		DungeonSurvival.getInstance().getPlayerTwo().getComponent(PlayerController.class).playerFreeze();
		DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getActiveRoom().freezeEnemies();
	}

	/**
	 * Unpause the game
	 */
	public void unpauseGame() {
		// unfreeze the players and all enemies
		DungeonSurvival.getInstance().getPlayerOne().getComponent(PlayerController.class).playerUnfreeze();
		DungeonSurvival.getInstance().getPlayerTwo().getComponent(PlayerController.class).playerUnfreeze();
		DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getActiveRoom().unfreezeEnemies();
	}

	// ACCESSORS

	public GameObject getPlayerOne() {
		return playerOne;
	}

	public GameObject getPlayerTwo() {
		return playerTwo;
	}

	public GameObject getRoomManager() {
		return roomManager;
	}

	public GameObject getUI() {
		return UI;
	}

	public long getSpriteAnimationTime() {
		return this.gameWindow.getSpriteRenderer().getAnimationTime();
	}

}
