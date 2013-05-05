package stricken.action;

import stricken.board.Critter;
import stricken.board.Tile;
import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.ITileCollector;
import stricken.collector.SingleTileCollector;

public class CritterActionFactory {

	private ITileCollector SINGLE = new SingleTileCollector();
	private ITileCollector ADJACENT_INCLUSIVE_IGNORE_OCCUPANTS = new AbstractDecayingTileCollector() {

		@Override
		protected boolean isInclusive() {
			return true;
		}

		@Override
		protected int getCostThreshold() {
			return 1;
		}

		@Override
		protected int getTileCost(Tile tile) {
			return 1;
		}

		@Override
		protected boolean isTileValid(Tile tile) {
			return tile != null;
		}
	};
	private ITileCollector ADJACENT_NON_INCLUSIVE_IGNORE_OCCUPANTS = new AbstractDecayingTileCollector() {

		@Override
		protected boolean isInclusive() {
			return false;
		}

		@Override
		protected int getCostThreshold() {
			return 1;
		}

		@Override
		protected int getTileCost(Tile tile) {
			return 1;
		}

		@Override
		protected boolean isTileValid(Tile tile) {
			return tile != null;
		}

	};

	private ITileCollector ADJACENT_NON_INCLUSIVE_OCCUPANTS_ONLY = new AbstractDecayingTileCollector() {

		@Override
		protected boolean isInclusive() {
			return false;
		}

		@Override
		protected int getCostThreshold() {
			return 1;
		}

		@Override
		protected int getTileCost(Tile tile) {
			return 1;
		}

		@Override
		protected boolean isTileValid(Tile tile) {
			return tile != null && tile.isOccupied();
		}

	};

	public CritterAction get(String id, Critter critter) {
		ITileCollector targetableRange = ADJACENT_NON_INCLUSIVE_IGNORE_OCCUPANTS;
		ITileCollector actualRange = ADJACENT_NON_INCLUSIVE_OCCUPANTS_ONLY;
		ITileCollector aoe = SINGLE;

		if ("smnf".equals(id)) {
			aoe = ADJACENT_INCLUSIVE_IGNORE_OCCUPANTS;
		}

		ITileEffect effect = new AttackTileEffect();
		return new CritterAction(targetableRange, actualRange, aoe, effect);
	}
}
