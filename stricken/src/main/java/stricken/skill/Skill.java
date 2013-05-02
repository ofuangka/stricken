package stricken.skill;

import stricken.collector.ITileCollector;
import stricken.effect.ITileEffect;

/**
 * Skills are all made up of a targeting range, and area of effect, and the
 * effect itself
 * 
 * @author ofuangka
 * 
 */
public class Skill {
	private final ITileCollector range;
	private final ITileCollector areaOfEffect;
	private final ITileEffect tileEffect;

	public Skill(ITileCollector range, ITileCollector areaOfEffect,
			ITileEffect tileEffect) {
		this.range = range;
		this.areaOfEffect = areaOfEffect;
		this.tileEffect = tileEffect;
	}

	public ITileCollector getAreaOfEffect() {
		return areaOfEffect;
	}

	public ITileCollector getRange() {
		return range;
	}

	public ITileEffect getTileEffect() {
		return tileEffect;
	}
}
