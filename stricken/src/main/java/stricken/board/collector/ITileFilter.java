package stricken.board.collector;

import stricken.board.piece.Tile;

public interface ITileFilter {
	boolean apply(Tile t);
}
