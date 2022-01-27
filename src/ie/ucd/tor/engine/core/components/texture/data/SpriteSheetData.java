package ie.ucd.tor.engine.core.components.texture.data;

import ie.ucd.tor.engine.util.FileIO;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheetData {

	private final ArrayList<SpriteData> spriteSheetData;
	private final int spriteSheetColumns;
	private final int spriteSheetRows;

	public SpriteSheetData(String spriteSheet, int spriteSheetColumns, int spriteSheetRows, int numSprites, int spriteWidth, int spriteHeight) {

		spriteSheetData = new ArrayList<>();

		this.spriteSheetColumns = spriteSheetColumns;
		this.spriteSheetRows = spriteSheetRows;

		BufferedImage spriteSheetImage = FileIO.loadImageTexture(spriteSheet);
		constructSpriteSheet(spriteSheetImage, spriteWidth, spriteHeight);
	}

	private void constructSpriteSheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {
		for (int i = 0; i < spriteSheetColumns; i++) {
			for (int j = 0; j < spriteSheetRows; j++) {
				BufferedImage spriteImage = spriteSheet.getSubimage(i * spriteWidth, j, spriteWidth, spriteHeight);
				addSpriteToSpriteSheet(spriteImage, spriteWidth, spriteHeight);
			}
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
