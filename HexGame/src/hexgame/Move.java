package hexgame;

public class Move implements MoveInterface {
	private int colour, x, y;

	public Move(int colour, int x, int y) {
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	public int getColour() {
		return this.colour;
	}

	@Override
	public boolean setPosition(int x, int y) throws InvalidPositionException {
		return false;
	}

	@Override
	public boolean hasConceded() {
		return false;
	}

	@Override
	public int getXPosition() {
		return this.x;
	}

	@Override
	public int getYPosition() {
		return this.y;
	}

	@Override
	public boolean setConceded() {
		return false;
	}
}
