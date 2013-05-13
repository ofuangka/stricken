package stricken.board.critter;

import stricken.board.ITileEffect;
import stricken.board.StatDrivenAttackTileEffect;
import stricken.board.Tile;
import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.ITileCollector;
import stricken.collector.SingleTileCollector;
import stricken.event.IEventContext;

public class CritterActionFactory {

	private static final ITileCollector SINGLE = new SingleTileCollector();
	private static final ITileCollector ADJACENT_INCLUSIVE_IGNORE_OCCUPANTS = new AbstractDecayingTileCollector() {

		@Override
		protected int getCostThreshold() {
			return 1;
		}

		@Override
		protected int getTileCost(Tile tile) {
			return 1;
		}

		@Override
		protected boolean isInclusive() {
			return true;
		}

		@Override
		protected boolean isTileValid(Tile tile) {
			return tile != null;
		}
	};
	private static final ITileCollector ADJACENT_NON_INCLUSIVE_IGNORE_OCCUPANTS = new AbstractDecayingTileCollector() {

		@Override
		protected int getCostThreshold() {
			return 1;
		}

		@Override
		protected int getTileCost(Tile tile) {
			return 1;
		}

		@Override
		protected boolean isInclusive() {
			return false;
		}

		@Override
		protected boolean isTileValid(Tile tile) {
			return tile != null;
		}

	};
	private static final ITileCollector ADJACENT_NON_INCLUSIVE_OCCUPANTS_ONLY = new AbstractDecayingTileCollector() {

		@Override
		protected int getCostThreshold() {
			return 1;
		}

		@Override
		protected int getTileCost(Tile tile) {
			return 1;
		}

		@Override
		protected boolean isInclusive() {
			return false;
		}

		@Override
		protected boolean isTileValid(Tile tile) {
			return tile != null && tile.isOccupied();
		}

	};

	private IEventContext eventContext;

	public CritterActionFactory(IEventContext eventContext) {
		this.eventContext = eventContext;
	}

	public CritterAction get(String id, Critter critter) {
		ITileCollector targetableRange = ADJACENT_NON_INCLUSIVE_IGNORE_OCCUPANTS;
		ITileCollector actualRange = ADJACENT_NON_INCLUSIVE_OCCUPANTS_ONLY;
		ITileCollector aoe = SINGLE;

		int lookupDamageRange = 1;
		int lookupModifier = 0;

		ITileEffect effect = new StatDrivenAttackTileEffect(
				Critter.Stat.STRENGTH, lookupDamageRange, lookupModifier,
				eventContext);
		return new CritterAction(targetableRange, actualRange, aoe, effect);
	}

	public String getLabel(String id) {
		return "Label";
	}
}
