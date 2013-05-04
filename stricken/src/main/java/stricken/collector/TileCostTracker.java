package stricken.collector;

import stricken.board.Tile;

public class TileCostTracker {
	public Tile tile;
	public int cost;

	public TileCostTracker(Tile tile, int cost) {
		this.tile = tile;
		this.cost = cost;
	}
}
