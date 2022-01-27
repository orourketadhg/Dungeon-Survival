package ie.ucd.tor.engine.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileIO {

	public static BufferedImage loadImageTexture(String textureLocation) {
		File textureFile = new File(textureLocation);
		try {
			return ImageIO.read(textureFile);
		}
		catch (IOException err) {
			err.printStackTrace();
		}

		return null;
	}
}
