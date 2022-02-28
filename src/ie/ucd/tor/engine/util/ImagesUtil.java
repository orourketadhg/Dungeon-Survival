package ie.ucd.tor.engine.util;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagesUtil {

	/**
	 * Load an image into the application
	 * @param textureLocation, location of the image
	 * @return the loaded image
	 */
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
