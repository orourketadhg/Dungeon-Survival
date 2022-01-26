package ie.ucd.tor.engine.core.components.texture.data;

import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.engine.util.FileIO;

import java.awt.*;

public class SpriteData {

	// texture data
	private final Image textureMap;
	private final int textureWidth;
	private final int textureHeight;

	// sprite data
	private final int spriteWidth;
	private final int spriteHeight;

	public SpriteData(Image img, int textureWidth, int textureHeight, int spriteWidth, int spriteHeight) {
		this.textureMap = img;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public Image getTextureMap() {
		return textureMap;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}
}
