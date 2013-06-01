package stricken.board.loader;

public class EntranceDefinition {
	private String id;
	private String dir;
	private int x;
	private int y;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDir() {
		return dir;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
