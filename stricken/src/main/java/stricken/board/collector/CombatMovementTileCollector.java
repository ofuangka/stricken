package stricken.board.collector;

import stricken.board.piece.Critter;
import stricken.board.piece.Critter.Stat;
import stricken.board.piece.Tile;

public class CombatMovementTileCollector extends AbstractDecayingTileCollector {

	private Critter critter;

	public CombatMovementTileCollector(TileListFilter filter, Critter critter) {
		super(filter);
		this.critter = critter;
	}

	@Override
	protected int getCostThreshold() {
		return critter.getStat(Stat.SPEED);
	}

	@Override
	protected int getTileCost(Tile tile) {
		return tile.getMovementCost();
	}

	protected boolean isInclusive() {
		return true;
	}

	@Override
	protected boolean isTileValid(Tile tile) {
		return tile != null && tile.isWalkable()
				&& (!tile.isOccupied() || tile.getOccupant() == critter);
	}

}
