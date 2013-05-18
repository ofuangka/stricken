package stricken.board.critter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.core.io.Resource;

import stricken.board.ITileEffect;
import stricken.board.StatDrivenAttackTileEffect;
import stricken.board.Tile;
import stricken.collector.AbstractDecayingTileCollector;
import stricken.collector.IPredicate;
import stricken.collector.ITileCollector;
import stricken.event.IEventContext;
import stricken.util.AbstractXmlConsumer;

/**
 * This class produces a CritterAction for a String
 * 
 * @author ofuangka
 * 
 */
public class CritterActionFactory extends AbstractXmlConsumer {

	public enum PredicateType {
		OCCUPIED_BY_CRITTER, ALL_TILES
	}

	private static final Logger log = Logger
			.getLogger(CritterActionFactory.class);

	private IEventContext eventContext;

	public CritterActionFactory(IEventContext eventContext, Resource resource)
			throws DocumentException, IOException {
		super(resource);
		this.eventContext = eventContext;
	}

	public CritterAction get(String id, Critter critter) {

		// get the main element representing the ID provided
		String elXpath = "/critterActions/critterAction[@id='" + id + "']";
		log.debug("Requesting critter using XPath: '" + elXpath + "'...");
		Element el = (Element) getDocument().selectSingleNode(elXpath);

		// get the metadata inside the element
		Element targetableMetadata = (Element) el
				.selectSingleNode("collector[@type='targetable']");
		Element aoeMetadata = (Element) el
				.selectSingleNode("collector[@type='aoe']");
		Element attackMetadata = (Element) el.selectSingleNode("attack");

		// create the critterAction
		String statName = attackMetadata.valueOf("@stat");
		ITileCollector targetableRange = getAbstractDecayingTileCollector(
				Integer.valueOf(targetableMetadata.valueOf("@costThreshold")),
				Integer.valueOf(targetableMetadata.valueOf("@tileCost")),
				StringUtils.isNotBlank(targetableMetadata
						.valueOf("@isInclusive")));

		ITileCollector aoe = getAbstractDecayingTileCollector(
				Integer.valueOf(aoeMetadata.valueOf("@costThreshold")),
				Integer.valueOf(aoeMetadata.valueOf("@tileCost")),
				StringUtils.isNotBlank(aoeMetadata.valueOf("@isInclusive")));

		int lookupDamageRange = critter.getStat(Critter.Stat.DAMAGE_RANGE);
		int lookupModifier = critter.getStat(Critter.Stat.DAMAGE_MODIFIER);

		ITileEffect effect = new StatDrivenAttackTileEffect(
				Critter.Stat.valueOf(statName.toUpperCase()),
				lookupDamageRange, lookupModifier, eventContext);

		IPredicate<Tile> predicate = getPredicate(targetableMetadata
				.valueOf("@predicateType"));

		return new CritterAction(el.valueOf("@name"), targetableRange,
				predicate, aoe, effect);
	}

	protected AbstractDecayingTileCollector getAbstractDecayingTileCollector(
			final int costThreshold, final int tileCost,
			final boolean isInclusive) {
		return new AbstractDecayingTileCollector() {

			@Override
			protected int getCostThreshold() {
				return costThreshold;
			}

			@Override
			protected int getTileCost(Tile tile) {
				return tileCost;
			}

			@Override
			protected boolean isInclusive() {
				return isInclusive;
			}

			@Override
			protected boolean isTileValid(Tile tile) {
				return tile != null;
			}

		};
	}

	/**
	 * Produces a Predicate depending on which PredicateType was passed in
	 * 
	 * @param predicateType
	 * @return
	 */
	protected IPredicate<Tile> getPredicate(String predicateTypeName) {
		IPredicate<Tile> ret = new IPredicate<Tile>() {

			@Override
			public boolean apply(Tile t) {
				return t != null
						&& t.isOccupied()
						&& Critter.class.isAssignableFrom(t.getOccupant()
								.getClass());
			}

		};
		return ret;
	}
}
