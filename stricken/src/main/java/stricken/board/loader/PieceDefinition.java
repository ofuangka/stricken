package stricken.board.loader;

public class PieceDefinition {
	private String id;
	private String type;
	private int seedX;
	private int seedY;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setSeedX(int seedX) {
		this.seedX = seedX;
	}

	public void setSeedY(int seedY) {
		this.seedY = seedY;
	}

	public int getSeedX() {
		return seedX;
	}

	public int getSeedY() {
		return seedY;
	}
}
