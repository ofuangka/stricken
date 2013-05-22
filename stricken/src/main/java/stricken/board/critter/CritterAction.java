package stricken.board.critter;

import stricken.board.AbstractCritterTileInteraction;
import stricken.collector.ITileCollector;

/**
 * CritterActions are all made up of a targeting range, and area of effect, and
 * the effect itself
 * 
 * @author ofuangka
 * 
 */
public class CritterAction {
	private final String name;
	private final ITileCollector targetingRange;
	private final ITileCollector actualRange;
	private final ITileCollector areaOfEffect;
	private final AbstractCritterTileInteraction tileEffect;

	public CritterAction(String name, ITileCollector targetingRange,
			ITileCollector actualRange, ITileCollector areaOfEffect,
			AbstractCritterTileInteraction tileEffect) {
		this.name = name;
		this.targetingRange = targetingRange;
		this.actualRange = actualRange;
		this.areaOfEffect = areaOfEffect;
		this.tileEffect = tileEffect;
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

	public ITileCollector getActualRange() {
		return actualRange;
	}

	public AbstractCritterTileInteraction getTileEffect() {
		return tileEffect;
	}
}
