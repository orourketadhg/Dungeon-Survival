package ie.ucd.tor.game.room.data;

import ie.ucd.tor.engine.maths.Point2D;

/**
 * Data about a blocked area of the map
 */
public class BlockedAreaData {

	private Point2D position;
	private int width;
	private int height;

	public BlockedAreaData(Point2D position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}

	// ACCESSORS

	public Point2D getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
