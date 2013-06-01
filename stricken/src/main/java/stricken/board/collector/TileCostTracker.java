package stricken.board.collector;

import stricken.board.piece.Tile;

public class TileCostTracker {
	private Tile tile;
	private int cost;

	public TileCostTracker(Tile tile, int cost) {
		this.tile = tile;
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Tile getTile() {
		return tile;
	}
}
