package ie.ucd.tor.engine.core.components.texture.data;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteData {

	// texture data
	private final BufferedImage textureMap;
	private final int textureWidth;
	private final int textureHeight;

	// sprite data
	private final int spriteWidth;
	private final int spriteHeight;


	public SpriteData(BufferedImage img, int textureWidth, int textureHeight, int spriteWidth, int spriteHeight) {
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
