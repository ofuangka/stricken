package stricken.board.loader;

public class EntranceDefinition {
	private String id;
	private String dir;
	private int x;
	private int y;

	public String getDir() {
		return dir;
	}

	public String getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
