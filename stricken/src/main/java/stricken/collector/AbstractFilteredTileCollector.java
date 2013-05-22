package stricken.collector;

import java.util.ArrayList;
import java.util.List;

import stricken.board.Tile;

public abstract class AbstractFilteredTileCollector implements ITileCollector {

	public static final ITileFilter NO_FILTER = new ITileFilter() {

		@Override
		public boolean apply(Tile t) {
			return t != null;
		}
	};

	private final ITileFilter filter;

	public AbstractFilteredTileCollector(ITileFilter filter) {
		this.filter = filter;
	}

	@Override
	public List<Tile> collect(Tile targetTile) {
		List<Tile> ret = new ArrayList<Tile>();
		List<Tile> unfiltered = _collect(targetTile);
		if (unfiltered != null) {
			for (Tile tile : unfiltered) {
				if (filter.apply(tile)) {
					ret.add(tile);
				}
			}
		}
		return ret;
	}

	protected abstract List<Tile> _collect(Tile targetTile);

}