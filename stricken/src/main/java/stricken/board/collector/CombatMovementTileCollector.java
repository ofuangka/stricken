package stricken.board.collector;

import stricken.board.piece.Tile;
import stricken.board.piece.critter.Critter;
import stricken.board.piece.critter.Critter.Stat;

public class CombatMovementTileCollector extends AbstractDecayingTileCollector {

	private Critter critter;

	public CombatMovementTileCollector(ITileFilter filter, Critter critter) {
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
		return tile != null
				&& (!tile.isOccupied() || tile.getOccupant() == critter);
	}

}