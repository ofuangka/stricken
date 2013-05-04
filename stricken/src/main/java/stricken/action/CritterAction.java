package stricken.action;

import stricken.collector.ITileCollector;

/**
 * CritterActions are all made up of a targeting range, and area of effect, and
 * the effect itself
 * 
 * @author ofuangka
 * 
 */
public class CritterAction {
	private final ITileCollector actualRange;
	private final ITileCollector targetableRange;
	private final ITileCollector areaOfEffect;
	private final ITileEffect tileEffect;

	public CritterAction(ITileCollector targetableRange,
			ITileCollector actualRange, ITileCollector areaOfEffect,
			ITileEffect tileEffect) {
		this.targetableRange = targetableRange;
		this.actualRange = actualRange;
		this.areaOfEffect = areaOfEffect;
		this.tileEffect = tileEffect;
	}

	public ITileCollector getAreaOfEffect() {
		return areaOfEffect;
	}

	public ITileCollector getTargetableRange() {
		return targetableRange;
	}

	public ITileCollector getActualRange() {
		return actualRange;
	}

	public ITileEffect getTileEffect() {
		return tileEffect;
	}
}
