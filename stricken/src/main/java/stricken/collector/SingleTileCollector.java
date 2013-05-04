package stricken.collector;

import java.util.ArrayList;
import java.util.List;

import stricken.board.Tile;

/**
 * This is the simplest TileCollector that just produces a List containing the
 * target tile
 * 
 * @author ofuangka
 * 
 */
public class SingleTileCollector implements ITileCollector {

	@Override
	public List<Tile> collect(Tile targetTile) {
		List<Tile> ret = new ArrayList<Tile>(1);
		ret.add(targetTile);
		return ret;
	}

}
