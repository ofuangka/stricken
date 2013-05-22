package stricken.collector;

import stricken.board.critter.Critter;
import stricken.board.critter.Critter.Stat;
import stricken.board.Tile;

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
