package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.util.ImagesUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheetData {

	private final ArrayList<SpriteData> spriteSheetData;
	private final int numSprites;

	public SpriteSheetData(String spriteSheet, int numSprites, int spriteWidth, int spriteHeight) {

		spriteSheetData = new ArrayList<>();

		this.numSprites = numSprites;

		BufferedImage spriteSheetImage = ImagesUtil.loadImageTexture(spriteSheet);
		constructSpriteSheet(spriteSheetImage, spriteWidth, spriteHeight);
	}

	private void constructSpriteSheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {
		for (int i = 0; i < this.numSprites; i++) {
			BufferedImage spriteImage = spriteSheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
			addSpriteToSpriteSheet(spriteImage, spriteWidth, spriteHeight);
		}
	}

	public void addSpriteToSpriteSheet(BufferedImage sprite, int spriteWidth, int spriteHeight) {
		spriteSheetData.add(new SpriteData(sprite, spriteWidth, spriteHeight));
	}

	public ArrayList<SpriteData> getSpriteSheetData() {
		return spriteSheetData;
	}

	public int getNumSprites() {
		return spriteSheetData.size();
	}
}
