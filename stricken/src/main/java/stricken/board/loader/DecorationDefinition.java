package stricken.board.loader;

public class DecorationDefinition {
	private String id;
	private boolean takingUpSpace;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTakingUpSpace(boolean takingUpSpace) {
		this.takingUpSpace = takingUpSpace;
	}

	public boolean isTakingUpSpace() {
		return takingUpSpace;
	}
}
