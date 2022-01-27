package ie.ucd.tor.engine.core.components.texture.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheetData {

	private final ArrayList<SpriteData> spriteSheetData;
	private final SpriteSheetDirection sheetSheetDirection;
	private final int spriteSheetColumns;
	private final int spriteSheetRows;
	private final int numSprites;

	public SpriteSheetData(BufferedImage spriteSheet, SpriteSheetDirection sheetSheetDirection, int spriteSheetColumns, int spriteSheetRows, int numSprites, int spriteWidth, int spriteHeight) {

		spriteSheetData = new ArrayList<>();

		this.sheetSheetDirection = sheetSheetDirection;
		this.spriteSheetColumns = spriteSheetColumns;
		this.spriteSheetRows = spriteSheetRows;
		this.numSprites = numSprites;

		constructSpriteSheet(spriteSheet, spriteWidth, spriteHeight);
	}

	private void constructSpriteSheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {



	}

	public void addSpriteToSpriteSheet(BufferedImage sprite, int spriteWidth, int spriteHeight) {
		spriteSheetData.add(new SpriteData(sprite, spriteWidth, spriteHeight));
	}

	public List<SpriteData> getSpriteSheetData() {
		return spriteSheetData;
	}

	public int getNumSprites() {
		return numSprites;
	}
}
