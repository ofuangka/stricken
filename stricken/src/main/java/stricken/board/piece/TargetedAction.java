package stricken.board.piece;

import stricken.board.collector.ITileCollector;
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
	private final ITileCollector actualRange;
	private final ITileCollector areaOfEffect;
	private final AbstractEffect tileEffect;

	public TargetedAction(String name, ITileCollector targetingRange,
			ITileCollector actualRange, ITileCollector areaOfEffect,
			AbstractEffect tileEffect) {
		this.name = name;
		this.targetingRange = targetingRange;
		this.actualRange = actualRange;
		this.areaOfEffect = areaOfEffect;
		this.tileEffect = tileEffect;
	}

	public ITileCollector getActualRange() {
		return actualRange;
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