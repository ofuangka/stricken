package stricken.action;

import stricken.board.Critter;
import stricken.board.Tile;

public interface ITileEffect {

	public void execute(Critter source, Tile targetTile);
}