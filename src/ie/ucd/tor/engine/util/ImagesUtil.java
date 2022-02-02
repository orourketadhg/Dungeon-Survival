package ie.ucd.tor.engine.util;

import ie.ucd.tor.engine.maths.Point2D;

import javax.imageio.ImageIO;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagesUtil {

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
