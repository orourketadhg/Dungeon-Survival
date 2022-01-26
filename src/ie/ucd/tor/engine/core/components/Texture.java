package ie.ucd.tor.engine.core.components;

import ie.ucd.tor.engine.util.FileIO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Texture extends Component {

	private static final String blankTexture = "res/blankSprite.png";

	private final String textureLocation;
	private final Image imageTexture;
	private int renderPriority;

	public Texture() {
		this(blankTexture, 0, false);
	}

	public Texture(String textureLocation) {
		this(textureLocation, 0, false);
	}


	public Texture(String textureLocation, int renderPriority, boolean animated) {
		this.textureLocation = textureLocation;
		this.renderPriority = renderPriority;
		imageTexture = FileIO.loadImageTexture(textureLocation);
	}

	public Image getTextureImage() {
		return imageTexture;
	}

	public String getTextureLocation() {
		return textureLocation;
	}

	public int getRenderPriority() {
		return renderPriority;
	}

	public void setRenderPriority(int renderPriority) {
		this.renderPriority = renderPriority;
	}

}
