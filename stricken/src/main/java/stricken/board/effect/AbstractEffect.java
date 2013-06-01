package stricken.board.effect;

import stricken.board.piece.Tile;
import stricken.board.piece.critter.Critter;

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
