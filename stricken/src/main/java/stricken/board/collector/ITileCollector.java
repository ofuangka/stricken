package stricken.board.collector;

import java.util.List;

import stricken.board.piece.Tile;

/**
 * A TileCollector is an object that abstracts collecting tiles given a target
 * tile. It produces a List of unique tiles in a deterministic order (so that
 * traversal can be anticipated by the user)
 * 
 * @author ofuangka
 * 
 */
public interface ITileCollector {
	List<Tile> collect(Tile targetTile);
}
