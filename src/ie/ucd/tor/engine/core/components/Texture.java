package ie.ucd.tor.engine.core.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Texture extends Component {

	private static final String blankTexture = "res/blankSprite.png";

	private final String textureLocation;
	private Image imageTexture;
	private boolean doesTextureAnimate;

	public Texture() {
		this(blankTexture, false);
	}

	public Texture(String textureLocation) {
		this(textureLocation, false);
	}

	public Texture(String textureLocation, boolean animated) {
		this.textureLocation = textureLocation;
		this.doesTextureAnimate = animated;
		loadImageTexture();
	}

	private void loadImageTexture() {
		File textureFile = new File(textureLocation);
		try {
			imageTexture = ImageIO.read(textureFile);
		}
		catch (IOException err) {
			err.printStackTrace();
		}
	}

	public boolean getTextureAnimationStatus() {
		return doesTextureAnimate;
	}

	public Image getTextureImage() {
		return imageTexture;
	}

	public String getTextureLocation() {
		return textureLocation;
	}

	public void setDoesTextureAnimate(boolean doesTextureAnimate) {
		this.doesTextureAnimate = doesTextureAnimate;
	}
}
