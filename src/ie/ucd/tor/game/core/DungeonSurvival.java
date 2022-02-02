package ie.ucd.tor.game.core;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.game.player.PlayerController;

public class DungeonSurvival extends GameController {

	@Override
	protected void initialization() {

		GameObject player = new GameObject();
		player.addComponent(new Animation());
		player.addComponent(new PlayerController());
		player.getComponent(Animation.class).AddAnimation("Knight_Static", new SpriteSheetData("res/Knight/Knight_static.png", 4, 1, 4,14, 16));


		gameWindow.getSpriteRenderer().addElement(player);

	}

}
