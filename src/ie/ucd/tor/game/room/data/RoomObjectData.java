package ie.ucd.tor.game.room.data;

import ie.ucd.tor.engine.maths.Point2D;

import java.util.ArrayList;
import java.util.List;

public class RoomObjectData {

	private final String spriteLocation;
	private final boolean isAnimated;
	private final List<Point2D> positions;

	public RoomObjectData(String decorationTexture, boolean animated) {
		this.spriteLocation = decorationTexture;
		this.isAnimated = animated;
		this.positions = new ArrayList<>();
	}

	public void addPosition(Point2D pos) {
		positions.add(pos);
	}

	public List<Point2D> getPositions() {
		return positions;
	}

	public String getSpriteLocation() {
		return spriteLocation;
	}

	public boolean isAnimated() {
		return isAnimated;
	}

}
