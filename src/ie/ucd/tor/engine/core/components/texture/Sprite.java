package ie.ucd.tor.engine.core.components.texture;

import ie.ucd.tor.engine.core.components.Component;
import ie.ucd.tor.engine.util.FileIO;

import java.awt.*;

public class Sprite extends Component {

	private static final String blankTexture = "res/blankSprite.png";

	private final String textureLocation;
	private final Image imageTexture;
	private int renderPriority;

	public Sprite() {
		this(blankTexture, 0, false);
	}

	public Sprite(String textureLocation) {
		this(textureLocation, 0, false);
	}


	public Sprite(String textureLocation, int renderPriority, boolean animated) {
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
