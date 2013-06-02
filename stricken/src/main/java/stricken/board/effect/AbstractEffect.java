package stricken.board.effect;

import stricken.board.piece.Critter;
import stricken.board.piece.Tile;

public abstract class AbstractEffect {

	private Critter source;

	public Critter getSource() {
		return source;
	}

	public abstract void interact(Tile targetTile);

	public void setSource(Critter source) {
		this.source = source;
	}
}
