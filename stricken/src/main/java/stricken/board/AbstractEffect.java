package stricken.board;

import stricken.board.critter.Critter;

public abstract class AbstractEffect {

	private Critter source;

	public void setSource(Critter source) {
		this.source = source;
	}

	public Critter getSource() {
		return source;
	}

	public abstract void interact(Tile targetTile);
}
