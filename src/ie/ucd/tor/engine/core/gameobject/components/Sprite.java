package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.SpriteData;
import ie.ucd.tor.engine.util.ImagesUtil;

import java.awt.image.BufferedImage;

public class Sprite extends Component {

	private static final String blankTexture = "res/blankSprite.png";

	private final SpriteData spriteData;
	private final int renderPriority;

	public Sprite() {
		this(blankTexture, 32, 32, 0);
	}

	public Sprite(String textureLocation, int spriteWidth, int spriteHeight, int renderPriority) {
		this.renderPriority = renderPriority;
		BufferedImage spriteImage = ImagesUtil.loadImageTexture(textureLocation);
		assert spriteImage != null;
		this.spriteData = new SpriteData(spriteImage, spriteWidth, spriteHeight);
	}

	public SpriteData getSpriteData() {
		return spriteData;
	}

	public int getRenderPriority() {
		return renderPriority;
	}

}
