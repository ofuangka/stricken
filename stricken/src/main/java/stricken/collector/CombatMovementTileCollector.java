package stricken.collector;

import stricken.board.Critter;
import stricken.board.Critter.Stat;
import stricken.board.Tile;

public class CombatMovementTileCollector extends AbstractDecayingTileCollector {

	private Critter critter;

	@Override
	protected int getCostThreshold() {
		return critter.getStat(Stat.SPEED);
	}

	@Override
	protected int getTileCost(Tile tile) {
		return tile.getMovementCost();
	}

	@Override
	protected boolean isTileValid(Tile tile) {
		return tile != null
				&& (!tile.isOccupied() || tile.getOccupant() == critter);
	}

	public void setCritter(Critter critter) {
		this.critter = critter;
	}

	protected boolean isInclusive() {
		return true;
	}

}
