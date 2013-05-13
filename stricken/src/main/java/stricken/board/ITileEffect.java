package stricken.board;

import stricken.board.critter.Critter;

public interface ITileEffect {

	public void execute(Critter source, Tile targetTile);
}
