package stricken.collector;

import stricken.board.Tile;

public class TileCostTracker {
	private Tile tile;
	private int cost;

	public TileCostTracker(Tile tile, int cost) {
		this.tile = tile;
		this.cost = cost;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public int getCost() {
		return cost;
	}
}
