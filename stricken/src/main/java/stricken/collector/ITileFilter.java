package stricken.collector;

import stricken.board.Tile;

public interface ITileFilter {
	boolean apply(Tile t);
}
