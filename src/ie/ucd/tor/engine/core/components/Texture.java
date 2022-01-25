package ie.ucd.tor.engine.core.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Texture extends Component {

	private static final String blankTexture = "res/blankSprite.png";

	private final String textureLocation;
	private Image imageTexture;
	private int renderPriority;
	private boolean doesTextureAnimate;

	public Texture() {
		this(blankTexture, 0, false);
	}

	public Texture(String textureLocation) {
		this(textureLocation, 0, false);
	}


	public Texture(String textureLocation, int renderPriority, boolean animated) {
		this.textureLocation = textureLocation;
		this.doesTextureAnimate = animated;
		this.renderPriority = renderPriority;
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

	public int getRenderPriority() {
		return renderPriority;
	}

	public void setRenderPriority(int renderPriority) {
		this.renderPriority = renderPriority;
	}

}
