package ie.ucd.tor.game.ui;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.events.InputEventHandler;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.rendering.GameWindow;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.player.PlayerController;
import ie.ucd.tor.game.room.RoomManager;

public class UIController extends Behaviour {

	private final GameObject tutorial;
	private GameObject gameover;

	private final GameObject playerOneHealth;
	private final GameObject playerTwoHealth;

	private boolean isPaused;
	private boolean isGameOver = false;

	private long delay;

	public UIController() {

		// tutorial
		tutorial = new GameObject();
		tutorial.getTransform().setPosition(new Point2D(24, 24));
		tutorial.addComponent(new Sprite("res/UI/Tutorial.png", 960, 960, 10));

		// player health
		playerOneHealth = new GameObject();
		playerOneHealth.getTransform().setPosition(new Point2D(24, 24));
		playerOneHealth.getTransform().setScale(new Point2D(5, 5));
		playerOneHealth.addComponent(new Animation(1));
		playerOneHealth.getComponent(Animation.class).AddAnimation("6", new SpriteSheetData("res/UI/Health/hearts_6.png", 1, 51, 17));
		playerOneHealth.getComponent(Animation.class).AddAnimation("5", new SpriteSheetData("res/UI/Health/hearts_5.png", 1, 51, 17));
		playerOneHealth.getComponent(Animation.class).AddAnimation("4", new SpriteSheetData("res/UI/Health/hearts_4.png", 1, 51, 17));
		playerOneHealth.getComponent(Animation.class).AddAnimation("3", new SpriteSheetData("res/UI/Health/hearts_3.png", 1, 51, 17));
		playerOneHealth.getComponent(Animation.class).AddAnimation("2", new SpriteSheetData("res/UI/Health/hearts_2.png", 1, 51, 17));
		playerOneHealth.getComponent(Animation.class).AddAnimation("1", new SpriteSheetData("res/UI/Health/hearts_1.png", 1, 51, 17));
		playerOneHealth.getComponent(Animation.class).AddAnimation("0", new SpriteSheetData("res/UI/Health/hearts_0.png", 1, 51, 17));

		playerTwoHealth = new GameObject();
		playerTwoHealth.getTransform().setPosition(new Point2D(120 * RoomManager.ROOM_SCALE.getX(), 24));
		playerTwoHealth.getTransform().setScale(new Point2D(5, 5));
		playerTwoHealth.addComponent(new Animation(1));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("6", new SpriteSheetData("res/UI/Health/hearts_6.png", 1, 51, 17));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("5", new SpriteSheetData("res/UI/Health/hearts_5.png", 1, 51, 17));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("4", new SpriteSheetData("res/UI/Health/hearts_4.png", 1, 51, 17));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("3", new SpriteSheetData("res/UI/Health/hearts_3.png", 1, 51, 17));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("2", new SpriteSheetData("res/UI/Health/hearts_2.png", 1, 51, 17));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("1", new SpriteSheetData("res/UI/Health/hearts_1.png", 1, 51, 17));
		playerTwoHealth.getComponent(Animation.class).AddAnimation("0", new SpriteSheetData("res/UI/Health/hearts_0.png", 1, 51, 17));

		GameWindow.getInstance().getUiRenderer().addElement(playerOneHealth);
		GameWindow.getInstance().getUiRenderer().addElement(playerTwoHealth);

		gameover = new GameObject();
		gameover.getTransform().setPosition(new Point2D(24, 24));
		gameover.addComponent(new Sprite("res/UI/GAMEOVER.png", 960, 960, 20));

	}

	@Override
	public void execute() {

		if (isGameOver) {
			long time = DungeonSurvival.getInstance().getSpriteAnimationTime();
			if (time > delay) {
				DungeonSurvival.getInstance().restartGame();
			}
			else {
				return;
			}
		}
		else {
			checkGameOver();
		}

		if (InputEventHandler.getInstance().isKeyEscPressed() && !isGameOver) {
			toggleTutorial();
		}

		updatePlayerOneHealthUI();
		updatePlayerTwoHealthUI();

	}

	private void checkGameOver() {
		int playerOneHealth = DungeonSurvival.getInstance().getPlayerOne().getComponent(PlayerController.class).getPlayerHealth();
		int playerTwoHealth = DungeonSurvival.getInstance().getPlayerTwo().getComponent(PlayerController.class).getPlayerHealth();

		if (playerOneHealth <= 0 || playerTwoHealth <= 0) {
			isGameOver = true;
			DungeonSurvival.getInstance().pauseGame();
			int numClearedRooms = DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getClearedRooms();

			GameWindow.getInstance().getUiRenderer().addElement(gameover);
			GameWindow.getInstance().getUiRenderer().addText(String.valueOf(numClearedRooms), 512, 748);

			delay = DungeonSurvival.getInstance().getSpriteAnimationTime() + 5000;
		}
	}

	public void toggleTutorial() {

		long currentTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentTime < delay) {
			return;
		}

		isPaused = !isPaused;

		if (!isPaused) {
			DungeonSurvival.getInstance().unpauseGame();

			GameWindow.getInstance().getUiRenderer().removeElement(tutorial);
		}
		else {
			DungeonSurvival.getInstance().pauseGame();

			GameWindow.getInstance().getUiRenderer().addElement(tutorial);
		}

		delay = currentTime + 100;
	}

	private void updatePlayerOneHealthUI() {
		int health = DungeonSurvival.getInstance().getPlayerOne().getComponent(PlayerController.class).getPlayerHealth();
		playerOneHealth.getComponent(Animation.class).setCurrentAnimation(String.valueOf(health));
	}

	private void updatePlayerTwoHealthUI() {
		int health = DungeonSurvival.getInstance().getPlayerTwo().getComponent(PlayerController.class).getPlayerHealth();
		playerTwoHealth.getComponent(Animation.class).setCurrentAnimation(String.valueOf(health));
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean gameOver) {
		isGameOver = gameOver;
	}
}
