package ie.ucd.tor.engine.util;

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

	public static BufferedImage calculateImageFlip(BufferedImage image) {
		AffineTransform flippedTransform = new AffineTransform();
		AffineTransform transform = AffineTransform.getTranslateInstance(image.getWidth(), 0);
		AffineTransform scale = AffineTransform.getScaleInstance(-1, 1);
		flippedTransform.concatenate(transform);
		flippedTransform.concatenate(scale);

		AffineTransformOp operation = new AffineTransformOp(flippedTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return operation.filter(image, null);
	}
}
