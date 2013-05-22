package stricken.collector;

import stricken.board.Tile;

public interface ITileFilter {
	public boolean apply(Tile t);
}
