package stricken.board.piece;

import stricken.board.collector.ITileCollector;
import stricken.board.collector.TileListFilter;
import stricken.board.effect.AbstractEffect;

/**
 * CritterActions are all made up of a targeting range, and area of effect, and
 * the effect itself
 * 
 * @author ofuangka
 * 
 */
public class TargetedAction {
	private final String name;
	private final ITileCollector targetingRange;
	private final TileListFilter actualRangeFilter;
	private final ITileCollector areaOfEffect;
	private final AbstractEffect tileEffect;

	public TargetedAction(String name, ITileCollector targetingRange,
			TileListFilter actualRangeFilter, ITileCollector areaOfEffect,
			AbstractEffect tileEffect) {
		this.name = name;
		this.targetingRange = targetingRange;
		this.actualRangeFilter = actualRangeFilter;
		this.areaOfEffect = areaOfEffect;
		this.tileEffect = tileEffect;
	}

	public TileListFilter getActualRangeFilter() {
		return actualRangeFilter;
	}

	public ITileCollector getAreaOfEffect() {
		return areaOfEffect;
	}

	public String getName() {
		return name;
	}

	public ITileCollector getTargetingRange() {
		return targetingRange;
	}

	public AbstractEffect getTileEffect() {
		return tileEffect;
	}
}
