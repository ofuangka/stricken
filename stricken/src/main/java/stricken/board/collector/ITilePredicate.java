package stricken.board.collector;

import stricken.board.piece.Tile;

public interface ITilePredicate {
	boolean apply(Tile t);
}
