package ie.ucd.tor.game.room.data;

import ie.ucd.tor.engine.maths.Point2D;

public class BlockedAreaData {

	private Point2D position;
	private int width;
	private int height;

	public BlockedAreaData(Point2D position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}

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
