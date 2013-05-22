package stricken.board.critter;

import stricken.board.AbstractCritterTileInteraction;
import stricken.board.Tile;
import stricken.collector.IPredicate;
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
	private final ITileCollector targetableRange;
	private final IPredicate<Tile> predicate;
	private final ITileCollector areaOfEffect;
	private final AbstractCritterTileInteraction tileEffect;

	public CritterAction(String name, ITileCollector targetableRange,
			IPredicate<Tile> predicate, ITileCollector areaOfEffect,
			AbstractCritterTileInteraction tileEffect) {
		this.name = name;
		this.targetableRange = targetableRange;
		this.predicate = predicate;
		this.areaOfEffect = areaOfEffect;
		this.tileEffect = tileEffect;
	}

	public ITileCollector getAreaOfEffect() {
		return areaOfEffect;
	}

	public String getName() {
		return name;
	}

	public IPredicate<Tile> getPredicate() {
		return predicate;
	}

	public ITileCollector getTargetingRange() {
		return targetableRange;
	}

	public AbstractCritterTileInteraction getTileEffect() {
		return tileEffect;
	}
}
