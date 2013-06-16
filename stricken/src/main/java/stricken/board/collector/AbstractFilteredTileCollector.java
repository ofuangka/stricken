package stricken.board.collector;

import java.util.List;

import stricken.board.piece.Tile;

public abstract class AbstractFilteredTileCollector implements ITileCollector {

	private final TileListFilter filter;

	public AbstractFilteredTileCollector(TileListFilter filter) {
		this.filter = filter;
	}

	@Override
	public List<Tile> collect(Tile targetTile) {
		return filter.filter(doCollect(targetTile));
	}

	protected abstract List<Tile> doCollect(Tile targetTile);

}
